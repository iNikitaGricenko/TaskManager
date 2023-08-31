package com.wolfhack.todo.service;

import com.wolfhack.todo.model.domain.Comment;

public interface ICommentService {

	Long addCommentToTask(Comment comment, Long taskId);

	Long addCommentToActivity(Comment comment, Long activityId);

	void updateComment(Long id, Comment comment);

}
