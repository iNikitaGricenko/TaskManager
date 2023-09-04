package com.wolfhack.todo.controller.implement;

import com.wolfhack.todo.controller.TagEndpoints;
import com.wolfhack.todo.mapper.WebTagMapper;
import com.wolfhack.todo.mapper.WebTaskMapper;
import com.wolfhack.todo.model.Tag;
import com.wolfhack.todo.model.Task;
import com.wolfhack.todo.model.create.TagCreateDTO;
import com.wolfhack.todo.model.response.TaskResponseDTO;
import com.wolfhack.todo.service.ITagService;
import com.wolfhack.todo.service.ITaskTagService;
import com.wolfhack.todo.wrapper.DomainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController implements TagEndpoints {

	private final ITagService tagService;
	private final ITaskTagService taskTagService;
	private final WebTagMapper tagMapper;
	private final WebTaskMapper taskMapper;

	@Override
	public long create(TagCreateDTO tagCreateDTO) {
		Tag tag = tagMapper.toModel(tagCreateDTO);
		return tagService.create(tag);
	}

	@Override
	public DomainPage<TaskResponseDTO> getTasksByTag(Long tagId, Pageable pageable) {
		DomainPage<Task> page = taskTagService.getTasksByTag(tagId, pageable);
		List<TaskResponseDTO> responseDTOS = page.getContent().stream().map(taskMapper::toResponse).toList();
		return new DomainPage<>(page, responseDTOS);
	}
}
