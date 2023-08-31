package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.model.DomainPage;
import com.wolfhack.todo.model.domain.Comment;
import org.springframework.data.domain.Pageable;

public interface CommentDatabaseAdapter extends DatabaseGateway<Comment> {

	DomainPage<Comment> getByTask(Long id, Pageable pageable);

}
