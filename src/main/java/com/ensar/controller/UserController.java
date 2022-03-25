package com.ensar.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ensar.entity.User;
import com.ensar.request.dto.CreateUpdateUserDto;
import com.ensar.security.CurrentUser;
import com.ensar.security.EnsarUserDetails;
import com.ensar.service.UserService;

import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Api(tags = "User Mgmt")
@RestController
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getLoggedInUser() {
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<User> getUserByEmail(@NotBlank @PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null)
            throw new ResourceNotFoundException();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllAccessibleUsers());
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<User> createUser(
            @ApiIgnore @CurrentUser final EnsarUserDetails currentUser,
            @Valid @RequestBody CreateUpdateUserDto createUpdateUserDto
    ) {
        /*if (createUpdateUserDto.getOrganizationId() == null) {
            createUpdateUserDto.setOrganizationId(currentUser.getOrganization().getId());
        }*/
        if (createUpdateUserDto.getRole() == null) {
            createUpdateUserDto.setRole(User.Role.ROLE_USER);
        }
        return ResponseEntity.ok(userService.createOrUpdateUser(Optional.empty(), createUpdateUserDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody CreateUpdateUserDto createUpdateUserDto) {
        return ResponseEntity.ok(userService.createOrUpdateUser(Optional.of(id), createUpdateUserDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/enable")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<Void> enableUsers(@RequestBody List<Long> idList) {
        userService.enableOrDisableUsers(idList, false);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/disable")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<Void> disableUsers(@RequestBody List<Long> idList) {
        userService.enableOrDisableUsers(idList, true);
        return ResponseEntity.ok().build();
    }

}
