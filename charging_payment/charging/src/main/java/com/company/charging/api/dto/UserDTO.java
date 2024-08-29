package com.company.charging.api.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:10:49 PM
 */
@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private BigDecimal balance;

}
