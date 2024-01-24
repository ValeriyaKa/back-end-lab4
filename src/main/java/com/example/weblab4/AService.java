package com.example.weblab4;

import com.example.weblab4.Database.User;
import com.example.weblab4.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class AService {
    private final UserRepository userRepository;

    public AService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String login, String password) {
        Optional<User> existingUser = userRepository.findByLogin(login);
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login has been taken");
        }

        User user = new User(login, getHash(password));
        userRepository.save(user);
    }

    public String check(String authorization) {
        if (!authorization.startsWith("Basic")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Authorization header");
        }

        String login, password;

        try {
            String base64 = authorization.substring(6);
            String decodedCredentials = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
            String[] credentials = decodedCredentials.split(":", 2);
            if (credentials.length < 2) {
                throw new IllegalArgumentException();
            }
            login = credentials[0];
            password = credentials[1];
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Authorization header");
        }

        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login");
        }

        if (!getHash(password).equals(user.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return user.get().getLogin();
    }

    private String getHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexBuilder = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexBuilder.append('0');
                }
                hexBuilder.append(hex);
            }
            return hexBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
