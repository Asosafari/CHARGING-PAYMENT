package com.company.charging.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:12:24 AM
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "charging_plan_id",nullable = false,unique = true)
    private ChargingPlan chargingPlan;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "is_success")
    private boolean isSuccess;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type",nullable = false)
    private TransactionType transactionType;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;



    @Transient
    private String description;
    @Transient
    private String publicKey;

    public void softDelete() {
        this.isDeleted = true;
    }

}
