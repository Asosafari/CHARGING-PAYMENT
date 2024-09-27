package com.company.charging.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.UpdateTimestamp;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 3, max = 12)
    private String username;

    @Column(name = "password", nullable = false)
//    @Size(min = 8,max = 12)
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

    @Column(name = "is_Authorized", nullable = false)
    private boolean isAuthorized;

    @Builder.Default
    @Column(name = "transactions")
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();


    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_charging_plan",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "charging_plan_id"))
    private Set<ChargingPlan> chargingPlans = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column()
    private LocalDateTime updateDate;

    public void softDelete() {
        this.isDeleted = true;
        this.isActive = false;
    }
    public void addRole(Role role){
        roles.add(role);
    }

}
