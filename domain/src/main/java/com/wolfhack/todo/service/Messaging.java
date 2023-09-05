package com.wolfhack.todo.service;

import com.wolfhack.todo.model.TopicMessagePOJO;

public interface Messaging {

	String send(TopicMessagePOJO message);

}
