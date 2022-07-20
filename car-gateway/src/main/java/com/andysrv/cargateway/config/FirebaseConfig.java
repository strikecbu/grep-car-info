package com.andysrv.cargateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp init(FirebaseInitConfig config, ObjectMapper objectMapper) throws IOException {

        Config build = Config.builder()
                .type(config.getType())
                .project_id(config.getProject_id())
                .private_key_id(config.getPrivate_key_id())
                .private_key(config.getPrivate_key())
                .client_email(config.getClient_email())
                .client_id(config.getClient_id())
                .auth_uri(config.getAuth_uri())
                .token_uri(config.getToken_uri())
                .auth_provider_x509_cert_url(config.getAuth_provider_x509_cert_url())
                .client_x509_cert_url(config.getClient_x509_cert_url())
                .build();

        String json = objectMapper.writeValueAsString(build);

        ByteArrayInputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(stream))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth auth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }


    @Data
    @Builder
    public static class Config {
        private String type;
        private String project_id;
        private String private_key_id;
        private String private_key;
        private String client_email;
        private String client_id;
        private String auth_uri;
        private String token_uri;
        private String auth_provider_x509_cert_url;
        private String client_x509_cert_url;
    }
}
