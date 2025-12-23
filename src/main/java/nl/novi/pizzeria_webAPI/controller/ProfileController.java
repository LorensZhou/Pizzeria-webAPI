package nl.novi.pizzeria_webAPI.controller;

import nl.novi.pizzeria_webAPI.dto.ProfileDto;
import nl.novi.pizzeria_webAPI.model.Profile;
import nl.novi.pizzeria_webAPI.repository.ProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    // No ProfileService used in demo code!

    private final ProfileRepository profileRepos;

    public ProfileController(ProfileRepository profileRepos) {

        this.profileRepos = profileRepos;
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileDto profileDto) {
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

