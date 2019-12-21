package com.kidd.shopping.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${google-service.path}")
    private String googleServicePath;
    @Value("${firebase.database-url}")
    private String databaseUrl;
    @Value("${firebase.storage-bucket}")
    private String storageBucket;

    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = new ClassPathResource(googleServicePath).getInputStream();
            FirebaseOptions.Builder optionsBuilder = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl(databaseUrl)
                    .setStorageBucket(storageBucket);
            FirebaseOptions options = optionsBuilder.build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
