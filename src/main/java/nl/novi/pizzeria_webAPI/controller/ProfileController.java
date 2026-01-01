package nl.novi.pizzeria_webAPI.controller;

import nl.novi.pizzeria_webAPI.dto.ProfileDto;
import nl.novi.pizzeria_webAPI.exception.RecordAlreadyExistsException;
import nl.novi.pizzeria_webAPI.exception.ResourceNotFoundException;
import nl.novi.pizzeria_webAPI.model.Profile;
import nl.novi.pizzeria_webAPI.repository.ProfileRepository;
import nl.novi.pizzeria_webAPI.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileRepository profileRepos;
    private final UserRepository userRepos;

    public ProfileController(ProfileRepository profileRepos, UserRepository userRepository) {

        this.profileRepos = profileRepos;
        this.userRepos = userRepository;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileDto profileDto) {

        if (!userRepos.existsById(profileDto.username)) {
            throw new ResourceNotFoundException("The user does not exist with username: " + profileDto.username);
        }

        //check of de combinatie name en lastname al bestaat
        if(profileRepos.existsByNameAndLastname(profileDto.name, profileDto.lastname)) {
            throw new RecordAlreadyExistsException("A profile with this name and lastname already exists");
        }

        Profile profile = new Profile();

        profile.setUsername(profileDto.username);
        profile.setName(profileDto.name);
        profile.setLastname(profileDto.lastname);
        profile.setAddress(profileDto.address);
        profile.setBankAccount(profileDto.bankAccount);

        this.profileRepos.save(profile);

        return ResponseEntity.created(null).body(profile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable String username, @AuthenticationPrincipal UserDetails userdetails) {

        Profile profile = this.profileRepos.findById(username).orElse(null);

        if(profile == null){
            return ResponseEntity.notFound().build();
        }

        if(!userdetails.getUsername().equals(username)){
            return ResponseEntity.status(403).build();
        }

        ProfileDto profileDto = new ProfileDto();
        profileDto.username = profile.getUsername();
        profileDto.name = profile.getName();
        profileDto.lastname = profile.getLastname();
        profileDto.address = profile.getAddress();
        profileDto.bankAccount = profile.getBankAccount();

        //succesvolle response
        return ResponseEntity.ok(profileDto);
    }
}

