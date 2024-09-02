package com.company.charging.api.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

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
    private String password;
    private String email;
    private BigDecimal balance;
    private BigDecimal accountBalance;

}
