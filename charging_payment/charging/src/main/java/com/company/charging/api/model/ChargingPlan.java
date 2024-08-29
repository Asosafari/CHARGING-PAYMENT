package com.company.charging.api.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:12:17 AM
 */

@Entity
@Data
@SuperBuilder
@Table(name = "charging_plan")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ChargingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name",unique = true, nullable = false)
    private String planName;

    @Column(name = "rate_per_unit",nullable = false)
    private BigDecimal ratePerUnit;


    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;


    @Builder.Default
    @ManyToMany(mappedBy = "chargingPlans")
    private Set<User> users = new HashSet<>();

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;


    public void softDelete() {
        this.isDeleted = true;
    }

}
