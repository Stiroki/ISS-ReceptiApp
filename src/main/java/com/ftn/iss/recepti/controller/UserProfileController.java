package com.ftn.iss.recepti.controller;

import com.ftn.iss.recepti.dto.ProfileDTOs.*;
import com.ftn.iss.recepti.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService profileService;

    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    // Javni pregled profila bilo kog korisnika (vidi se slika, region, biografija i broj recepata)
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getProfileByUsername(username));
    }

    // Izmena sopstvenog profila (region, biografija, URL slike)
    @PutMapping("/{username}")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @PathVariable String username,
            @Valid @RequestBody UpdateProfileRequest req,
            Authentication auth) {
        profileService.verifyOwnership(auth.getName(), username);
        return ResponseEntity.ok(profileService.updateOwnProfile(auth.getName(), req));
    }

    // Upload profilne slike na backend (multipart/form-data)
    @PostMapping(value = "/{username}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResponse> uploadAvatar(
            @PathVariable String username,
            @RequestParam("file") MultipartFile file,
            Authentication auth) {
        profileService.verifyOwnership(auth.getName(), username);
        return ResponseEntity.ok(profileService.uploadOwnAvatar(auth.getName(), file));
    }
}