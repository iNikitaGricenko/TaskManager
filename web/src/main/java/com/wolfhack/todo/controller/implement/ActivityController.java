package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.annotation.Endpoint;
import com.wolfhack.todo.controller.ActivityEndpoints;
import com.wolfhack.todo.mapper.WebActivityMapper;
import com.wolfhack.todo.mapper.WebCommentMapper;
import com.wolfhack.todo.model.Activity;
import com.wolfhack.todo.model.Comment;
import com.wolfhack.todo.model.create.ActivityCreateDTO;
import com.wolfhack.todo.model.create.CommentCreateDTO;
import com.wolfhack.todo.model.response.ActivityResponseDTO;
import com.wolfhack.todo.model.update.ActivityUpdateDTO;
import com.wolfhack.todo.service.IActivityService;
import com.wolfhack.todo.service.ICommentService;
import com.wolfhack.todo.wrapper.DomainPage;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
@RolesAllowed({ "USER", "ADMIN" })
public class ActivityController implements ActivityEndpoints {

	private final IActivityService activityService;
	private final ICommentService commentService;
	private final WebActivityMapper activityMapper;
	private final WebCommentMapper commentMapper;

	@Override
	@Endpoint
	public long create(Long taskId, ActivityCreateDTO activityCreateDTO) {
		Activity activity = activityMapper.toModel(activityCreateDTO);
		return activityService.create(taskId, activity);
	}

	@Override
	@Endpoint
	public long comment(Long id, CommentCreateDTO commentCreateDTO) {
		Comment comment = commentMapper.toModel(commentCreateDTO);
		return commentService.addCommentToActivity(comment, id);
	}

	@Override
	@Endpoint
	public void start(Long id) {
		activityService.start(id);
	}

	@Override
	@Endpoint
	public void finish(Long id) {
		activityService.finish(id);
	}

	@Override
	public void delete(Long id) {
		activityService.delete(id);
	}

	@Override
	@Endpoint
	public void partialUpdate(Long activityId, ActivityUpdateDTO activityUpdateDTO) {
		Activity activity = activityMapper.toModel(activityUpdateDTO);
		activityService.partialUpdate(activityId, activity);
	}

	@Override
	@Endpoint
	public void update(Long activityId, ActivityCreateDTO activityUpdateDTO) {
		Activity activity = activityMapper.toModel(activityUpdateDTO);
		activityService.update(activityId, activity);
	}

	@Override
	@Endpoint
	public DomainPage<ActivityResponseDTO> getPage(Long taskId, Pageable pageable) {
		DomainPage<Activity> page = activityService.getPage(taskId, pageable);
		List<ActivityResponseDTO> responseDTOS = page.getContent().stream().map(activityMapper::toResponse).toList();
		return new DomainPage<>(page, responseDTOS);
	}

	@Override
	public ActivityResponseDTO getOne(Long id) {
		Activity activity = activityService.get(id);
		return activityMapper.toResponse(activity);
	}
}
