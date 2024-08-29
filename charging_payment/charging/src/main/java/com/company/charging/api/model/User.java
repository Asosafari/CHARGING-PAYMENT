package com.company.charging.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.Email;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:12:18 AM
 */
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 3, max = 12)
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 8,max = 12)
    private String password;

    @Column(unique = true, nullable = false)
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "transactions")
    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_charging_plan",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "charging_plan_id"))
    private Set<ChargingPlan> chargingPlans = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    public void softDelete() {
        this.isDeleted = true;
        this.isActive = false;
    }

}
