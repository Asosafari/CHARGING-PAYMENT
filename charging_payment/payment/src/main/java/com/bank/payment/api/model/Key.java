package com.bank.payment.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:46 AM
 */
@Data
@Entity
@Table(name = "key_payment")
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "charging_user_id", nullable = false)
    @JsonIgnore
    private PaymentUser paymentUser;

}
