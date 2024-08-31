package com.company.charging.api.service;

import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.mapper.UserMapper;
import com.company.charging.api.model.User;
import com.company.charging.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:10:52 PM
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserDTO> listOfUsers(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = BuildPageRequest.build(pageNumber,pageSize,"username");
        return userRepository.findAll(pageRequest).map(userMapper ::mapToDTO);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return Optional.ofNullable(userMapper.mapToDTO(userRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, UserDTO userDto) {
        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();
        userRepository.findById(id).ifPresentOrElse(founduser -> {
            founduser.setEmail(userDto.getEmail());
            founduser.setPassword(userDto.getPassword());
            founduser.setUpdateDate(LocalDateTime.now());
            atomicReference.set(Optional.of(userMapper.mapToDTO(userRepository.save(founduser))));
        }, () -> atomicReference.set(Optional.empty())
        );
        return atomicReference.get();
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            user.get().softDelete();
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return userMapper.mapToDTO(userRepository.save(userMapper.mapToModel(userDTO)));
    }
}
