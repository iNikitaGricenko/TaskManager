package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.controller.TaskEndpoints;
import com.wolfhack.todo.mapper.WebCommentMapper;
import com.wolfhack.todo.mapper.WebTaskMapper;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import com.wolfhack.todo.service.ICommentService;
import com.wolfhack.todo.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController implements TaskEndpoints {

	private final ITaskService taskService;
	private final ICommentService commentService;
	private final WebTaskMapper taskMapper;
	private final WebCommentMapper commentMapper;

	@Override
	public long create(TaskCreateDTO taskCreateDTO) {
		Task task = taskMapper.toModel(taskCreateDTO);
		return taskService.create(task);
	}

	@Override
	public long comment(Long id, CommentCreateDTO commentCreateDTO) {
		Comment comment = commentMapper.toModel(commentCreateDTO);
		return commentService.addCommentToTask(comment, id);
	}

	@Override
	public void assign(Long taskId, Long userId) {
		taskService.assignUser(taskId, userId);
	}

	@Override
	public void start(Long id) {
		taskService.start(id);
	}

	@Override
	public void finish(Long id) {
		taskService.finish(id);
	}
}
