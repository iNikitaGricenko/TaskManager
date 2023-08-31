package com.wolfhack.todo.adapter.database;

import com.wolfhack.todo.model.User;

public interface UserDatabaseAdapter extends DatabaseGateway<User> {

	User getByUsername(String username);

	User getByEmail(String email);

}
