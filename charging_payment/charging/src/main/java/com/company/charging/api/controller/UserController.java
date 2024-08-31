package com.company.charging.api.controller;

import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.exception.NotFoundException;
import com.company.charging.api.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:5:59 PM
 */
@RestController()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public Page<UserDTO> listOfUsers(@RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer pageSize){

        return userService.listOfUsers(pageNumber,pageSize);
    }

    @GetMapping("/api/v1/users/{userId}")
    public UserDTO getUserDTO(@PathVariable("userId") Long userId){
        return userService.getUserById(userId).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/api/v1/users/create")
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO){
        UserDTO saveUserDto = userService.saveUser(userDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/user/" + saveUserDto.getId().toString());
        return new ResponseEntity<>(saveUserDto, headers, HttpStatus.CREATED);
    }

}
