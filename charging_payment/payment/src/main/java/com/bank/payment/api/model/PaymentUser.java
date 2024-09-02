package com.bank.payment.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:1:26 PM
 */
@Entity
@Data
@Table(name = "payment_users")
public class PaymentUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "charging_user_id",unique = true,nullable = false)
    private Long chargingUserId;


    @Column(name = "account_balance", nullable = false)
    @DecimalMin(value = "0.00")
    private BigDecimal accountBalance;


    @OneToOne(mappedBy = "paymentUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Key key;

    @Transient
    private String publicKey;

    @OneToMany(mappedBy = "paymentUser")
    private Set<Payment> payments = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public void softDelete() {
        this.isDeleted = true;
    }


}
