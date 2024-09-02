package com.bank.payment.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:1:27 PM
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private PaymentUser paymentUser;

    @Column(name = "amount", nullable = false)
    @DecimalMin(value = "0.00")
    private BigDecimal amount;

    @Column(name = "is_success", nullable = false)
    private boolean isSuccess;

    @Transient
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public void softDelete() {
        this.isDeleted = true;
    }
}
