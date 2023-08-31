package com.wolfhack.todo.service.implement;

import com.wolfhack.todo.adapter.database.ActivityDatabaseAdapter;
import com.wolfhack.todo.adapter.database.CommentDatabaseAdapter;
import com.wolfhack.todo.adapter.database.TaskDatabaseAdapter;
import com.wolfhack.todo.model.domain.Activity;
import com.wolfhack.todo.model.domain.Comment;
import com.wolfhack.todo.model.domain.Task;
import com.wolfhack.todo.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

	private final CommentDatabaseAdapter commentDatabaseAdapter;
	private final ActivityDatabaseAdapter activityDatabaseAdapter;
	private final TaskDatabaseAdapter taskDatabaseAdapter;

	@Override
	public Long addCommentToTask(Comment comment, Long taskId) {
		if (!taskDatabaseAdapter.exists(taskId)) {
			throw new RuntimeException();
		}
		comment.setCreatedAt(LocalDateTime.now());
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
		Activity activity = activityDatabaseAdapter.getById(activityId);
		comment.setActivity(activity);

		return commentDatabaseAdapter.save(comment);
	}

	@Override
	public void updateComment(Long id, Comment comment) {
		comment.setUpdatedAt(LocalDateTime.now());
		commentDatabaseAdapter.update(id, comment);
	}
}
