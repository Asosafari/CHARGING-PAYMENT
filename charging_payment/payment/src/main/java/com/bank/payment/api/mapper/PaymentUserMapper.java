package com.bank.payment.api.mapper;

import com.bank.payment.api.dto.PaymentUserRequest;
import com.bank.payment.api.model.PaymentUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:2:54 AM
 */


@Mapper
public interface PaymentUserMapper {

     PaymentUser mapToModel(PaymentUserRequest paymentUserRequest);
}
