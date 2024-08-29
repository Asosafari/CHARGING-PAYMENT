package com.company.charging.api.service;

import com.company.charging.api.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:10:52 PM
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public Page<UserDTO> listOfUsers(Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public Optional<UserDTO> getUserById(Long Id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> updateUser(Long Id, UserDTO userDto) {
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public Optional<UserDTO> patchUser(Long Id, UserDTO userDto) {
        return Optional.empty();
    }
}
