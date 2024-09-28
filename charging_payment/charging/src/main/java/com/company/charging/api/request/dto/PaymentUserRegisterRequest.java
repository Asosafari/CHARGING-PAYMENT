package com.company.charging.api.request.dto;

import com.company.charging.api.service.PaymentType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:1:16 PM
 */
@Data
public class PaymentUserRegisterRequest {
    private Long  chargingUserId;
    private BigDecimal amount;
    private boolean isAuthorize;
    private PaymentType paymentType;
    private BigDecimal accountBalance;

}
