package com.company.charging.api.controller;

import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private UserDTO userDTO;

    @MockBean
    UserService userService;

    @BeforeEach
    void setUp() {
         userDTO = UserDTO.builder()
                 .password("1230")
                 .username("Gorg_Ali")
                 .id(1L)
                 .balance(new BigDecimal(6798))
                 .email("www.example@gmail.com")
                 .build();
    }

    @Test
    void listOfUsers() throws Exception {
        Page<UserDTO>  userDTOPage = new PageImpl<>(Collections.singletonList(userDTO));
         given(userService.listOfUsers(any(),any())).willReturn(userDTOPage);
         mockMvc.perform(get("/api/v1/users")
                         .queryParam("pageNumber", String.valueOf(0))
                         .queryParam("pageSize", String.valueOf(10))
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.content[0].username").value("Gorg_Ali"));

    }
}