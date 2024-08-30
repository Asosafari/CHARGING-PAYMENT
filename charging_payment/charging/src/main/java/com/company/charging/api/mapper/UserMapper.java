package com.company.charging.api.mapper;

import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.model.User;
import org.mapstruct.Mapper;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:11:04 PM
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO mapToDTO(User user);
}

