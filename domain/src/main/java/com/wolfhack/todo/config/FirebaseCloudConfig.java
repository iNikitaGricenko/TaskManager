package com.wolfhack.todo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseCloudConfig {

	@Bean
	public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
		return FirebaseMessaging.getInstance(firebaseApp);
	}

	@Bean
	public FirebaseApp firebaseApp(GoogleCredentials credentials) {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(credentials)
				.build();

		return FirebaseApp.initializeApp(options);
	}

}
