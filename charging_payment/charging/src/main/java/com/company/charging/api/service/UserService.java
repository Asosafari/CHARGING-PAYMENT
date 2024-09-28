package com.company.charging.api.service;

import com.company.charging.api.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:10:39 PM
 */
@Service
public interface UserService {
    Page<UserDTO> listOfUsers(Integer pageNumber, Integer pageSize);
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> updateUser(Long id, UserDTO userDto);
    Optional<UserDTO> patchUser(Long id, UserDTO userDto);
    boolean deleteUser(Long id);
    UserDTO saveUser(UserDTO userDTO);


}
