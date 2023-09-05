package com.wolfhack.todo.model;

public record MailMessagePOJO(
		String to,
		String subject,
		String body
) {
}
