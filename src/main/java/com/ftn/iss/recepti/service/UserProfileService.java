package com.ftn.iss.recepti.service;

import com.ftn.iss.recepti.dto.AuthDTOs.*;
import com.ftn.iss.recepti.exception.BadRequestException;
import com.ftn.iss.recepti.exception.ConflictException;
import com.ftn.iss.recepti.model.Role;
import com.ftn.iss.recepti.model.User;
import com.ftn.iss.recepti.repository.UserRepository;
import com.ftn.iss.recepti.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username())) {
            throw new ConflictException("Korisničko ime je već zauzeto.");
        }
        if (userRepo.existsByEmail(req.email())) {
            throw new ConflictException("Mejl adresa je već registrovana.");
        }

        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPassword(encoder.encode(req.password()));
        user.setRegion(req.region());
        user.setRole(Role.USER);
        user.setProfileImageUrl("https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=300");
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByUsername(req.username())
                .orElseThrow(() -> new BadRequestException("Pogrešno korisničko ime ili lozinka."));

        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new BadRequestException("Pogrešno korisničko ime ili lozinka.");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}