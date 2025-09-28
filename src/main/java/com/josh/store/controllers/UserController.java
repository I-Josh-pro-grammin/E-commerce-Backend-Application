package com.josh.store.controllers;

import com.josh.store.Mappers.UserMapper;
import com.josh.store.dtos.ChangePasswordRequest;
import com.josh.store.dtos.RegisterUserRequest;
import com.josh.store.dtos.UpdateUserRequest;
import com.josh.store.dtos.UserDto;
import com.josh.store.entities.User;
import com.josh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        var userData = userMapper.toEntity(request);

        userRepository.save(userData);

        var userDto = userMapper.toUser(userData);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto)  ;
    }

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestHeader(required = false, name = "x-auth-token") String authToken,
            @RequestParam(required = false, defaultValue = "", name = "sort")  String sortParam
    ) {

        System.out.println(authToken);
        if(!Set.of("id", "name", "email").contains(sortParam)){
            sortParam = "name";
        }
        return userRepository.findAll(Sort.by(sortParam).ascending())
                .stream()
                .map(userMapper::toUser)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
    ) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.updateUser(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(
            @PathVariable(name = "id") Long id
    ) {
        var user = userRepository.findById(id).orElse(null);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest request
            ) {
        var user = userRepository.findById(id).orElse(null);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();

    }
}
