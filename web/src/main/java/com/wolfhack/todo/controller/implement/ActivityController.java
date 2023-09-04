package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.controller.ActivityEndpoints;
import com.wolfhack.todo.mapper.WebActivityMapper;
import com.wolfhack.todo.mapper.WebCommentMapper;
import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.create.ActivityCreateDTO;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.service.IActivityService;
import com.wolfhack.todo.service.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController implements ActivityEndpoints {

	private final IActivityService activityService;
	private final ICommentService commentService;
	private final WebActivityMapper activityMapper;
	private final WebCommentMapper commentMapper;

	@Override
	public long create(Long taskId, ActivityCreateDTO activityCreateDTO) {
		Activity activity = activityMapper.toModel(activityCreateDTO);
		return activityService.create(taskId, activity);
	}

	@Override
	public long comment(Long id, CommentCreateDTO commentCreateDTO) {
		Comment comment = commentMapper.toModel(commentCreateDTO);
		return commentService.addCommentToActivity(comment, id);
	}

	@Override
	public void start(Long id) {
		activityService.start(id);
	}

	@Override
	public void finish(Long id) {
		activityService.finish(id);
	}
}
