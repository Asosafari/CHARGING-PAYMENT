package com.company.charging.api.controller;

import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;



@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private UserDTO userDTO;

    @MockBean
    UserService userService;

    @Captor
    ArgumentCaptor<Long> idArgumentCaptor;

    @Captor
    ArgumentCaptor<UserDTO> userDTOArgumentCaptor;

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

    @Test
    void createUser() throws Exception {
        given(userService.saveUser(any())).willReturn(userDTO);

        mockMvc.perform(post("/api/v1/users/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Gorg_Ali"));
    }

    @Test
    void updateUser() throws Exception {
        given(userService.updateUser(any(),any())).willReturn(Optional.of(userDTO));

        mockMvc.perform(put("/api/v1/users/{userId}",userDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNoContent());

        verify(userService).updateUser(idArgumentCaptor.capture(),any(UserDTO.class));
        assertThat(userDTO.getId()).isEqualTo(idArgumentCaptor.getValue());
        verify(userService).updateUser(idArgumentCaptor.capture(),userDTOArgumentCaptor.capture());
        assertThat(userDTO.getId()).isEqualTo(idArgumentCaptor.getValue());
        assertThat(userDTO.getEmail()).isEqualTo(userDTOArgumentCaptor.getValue().getEmail());
    }

    @Test
    void deleteUser() throws Exception {
        given(userService.deleteUser(any())).willReturn(true);
        mockMvc.perform(delete("/api/v1/users/{userId}",userDTO.getId())
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(idArgumentCaptor.capture());
        assertThat(userDTO.getId()).isEqualTo(idArgumentCaptor.getValue());

    }
}