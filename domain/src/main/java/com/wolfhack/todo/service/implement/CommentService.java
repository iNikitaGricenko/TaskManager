package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.ActivityDatabaseAdapter;
import com.wolfhack.todo.adapter.database.CommentDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.adapter.database.UserDatabaseAdapter;
import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.User;
import com.wolfhack.todo.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

	private final CommentDatabaseAdapter commentDatabaseAdapter;
	private final ActivityDatabaseAdapter activityDatabaseAdapter;
	private final UserDatabaseAdapter userDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;

	@Override
	public Long addCommentToTask(Comment comment, Long taskId) {
		if (!taskDatabaseAdapter.exists(taskId)) {
			throw new RuntimeException();
		}
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUser(getCurrentUser());
		Task task = taskDatabaseAdapter.getById(taskId);
		comment.setTask(task);

		return commentDatabaseAdapter.save(comment);
	}

	@Override
	public Long addCommentToActivity(Comment comment, Long activityId) {
		if (!activityDatabaseAdapter.exists(activityId)) {
			throw new RuntimeException();
		}
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUser(getCurrentUser());
		Activity activity = activityDatabaseAdapter.getById(activityId);
		comment.setActivity(activity);

		return commentDatabaseAdapter.save(comment);
	}

	@Override
	public void updateComment(Long id, Comment comment) {
		comment.setUpdatedAt(LocalDateTime.now());
		commentDatabaseAdapter.update(id, comment);
	}

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			throw new RuntimeException();
		}
		Long principalId = (Long) authentication.getCredentials();
		return userDatabaseAdapter.getById(principalId);
	}
}
