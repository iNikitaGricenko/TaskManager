package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.wrapper.DomainPage;
import com.wolfhack.todo.model.Comment;
import org.springframework.data.domain.Pageable;

public interface CommentDatabaseAdapter extends DatabaseGateway<Comment> {

	DomainPage<Comment> getByTask(Long id, Pageable pageable);

}
