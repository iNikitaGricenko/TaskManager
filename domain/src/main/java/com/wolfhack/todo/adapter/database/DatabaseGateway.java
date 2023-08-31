package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.adapter.DomainModel;
import com.wolfhack.todo.wrapper.DomainPage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface DatabaseGateway<T extends DomainModel> {

	Long save(T model);

	Long update(Long id, T model);

	T getById(Long id);

	boolean exists(Long id);

	Collection<T> getById(Collection<Long> ids);

	List<T> getAll();

	DomainPage<T> getPage(Pageable pageable);

	void delete(Long id);
}
