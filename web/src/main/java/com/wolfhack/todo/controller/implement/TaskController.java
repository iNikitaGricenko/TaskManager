package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.controller.TaskEndpoints;
import com.wolfhack.todo.mapper.WebCommentMapper;
import com.wolfhack.todo.mapper.WebTaskMapper;
import com.wolfhack.todo.mapper.WebTaskMetaMapper;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.create.TaskMetaCreateDTO;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.TaskMeta;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.create.TaskCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.service.ICommentService;
import com.wolfhack.todo.service.ITaskService;
import com.wolfhack.todo.wrapper.DomainPage;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@RolesAllowed({ "USER", "ADMIN" })
public class TaskController implements TaskEndpoints {

	private final ITaskService taskService;
	private final ICommentService commentService;
	private final WebTaskMapper taskMapper;
	private final WebCommentMapper commentMapper;
	private final WebTaskMetaMapper taskMetaMapper;

	@Override
	public long create(TaskCreateDTO taskCreateDTO) {
		Task task = taskMapper.toModel(taskCreateDTO);
		Long taskId = taskService.create(task);

		taskService.addTag(taskId, taskCreateDTO.tagId());

		return taskId;
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
	public void unassign(Long taskId) {
		taskService.unassign(taskId);
	}

	@Override
	public void start(Long id) {
		taskService.start(id);
	}

	@Override
	public long addMeta(Long id, TaskMetaCreateDTO taskMetaDTO) {
		TaskMeta taskMeta = taskMetaMapper.toModel(taskMetaDTO);
		return taskService.addMeta(id, taskMeta);
	}

	@Override
	public void finish(Long id) {
		taskService.finish(id);
	}

	@Override
	public void delete(Long id) {
		taskService.delete(id);
	}

	@Override
	public DomainPage<TaskResponseDTO> getPage(Pageable pageable) {
		DomainPage<Task> page = taskService.getPage(pageable);
		List<TaskResponseDTO> responseDTOS = page.getContent().stream().map(taskMapper::toResponse).toList();
		return new DomainPage<>(page, responseDTOS);
	}
}
