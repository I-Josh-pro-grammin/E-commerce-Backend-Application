package com.josh.store.controllers;

import com.josh.store.Mappers.UserMapper;
import com.josh.store.dtos.UserDto;
import com.josh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort")  String sortParam
    ) {
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
}
