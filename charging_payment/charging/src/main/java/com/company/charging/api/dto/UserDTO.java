package com.company.charging.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:10:49 PM
 */
@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private Boolean isAuthorized;
    private String password;
    private String email;
    private BigDecimal balance;
    private BigDecimal accountBalance;
    private Set<String> rolesName;
    private BigDecimal amount;


}
