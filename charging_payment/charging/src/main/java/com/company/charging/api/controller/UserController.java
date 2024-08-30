package com.company.charging.api.controller;

import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
