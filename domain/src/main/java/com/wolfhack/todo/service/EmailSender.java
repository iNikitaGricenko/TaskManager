package com.wolfhack.todo.service;

import com.wolfhack.todo.model.MailMessagePOJO;

public interface EmailSender {

	void send(MailMessagePOJO mailMessagePOJO);

}
