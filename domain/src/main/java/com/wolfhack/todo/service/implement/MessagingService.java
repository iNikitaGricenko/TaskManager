package com.wolfhack.todo.service.implement;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.wolfhack.todo.model.TopicMessagePOJO;
import com.wolfhack.todo.service.Messaging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingService implements Messaging {

	private final FirebaseMessaging firebaseMessaging;

	@Override
	public String send(TopicMessagePOJO message) {
		Message firebaseMessage = Message.builder()
				.setTopic(message.topic())
				.putData("body", message.message())
				.build();

		try {
			return firebaseMessaging.send(firebaseMessage);
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
