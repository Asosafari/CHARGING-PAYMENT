package com.company.charging.api.request.dto;

import com.company.charging.api.service.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:1:36 PM
 */
@Builder
@Data
public class  DirectPaymentRequest {
    private Long userId;
    private BigDecimal amount;
    private PaymentType paymentType;

}
