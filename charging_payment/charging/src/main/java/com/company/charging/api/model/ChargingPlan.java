package com.company.charging.api.model;

import com.company.charging.api.plans.BasicChargingPlan;
import com.company.charging.api.plans.DefaultChargingPlan;
import com.company.charging.api.plans.PremiumChargingPlan;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
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
@Table(name = "charging_plans")
@Getter
@Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BasicChargingPlan.class, name = "BASIC"),
        @JsonSubTypes.Type(value = PremiumChargingPlan.class, name = "PREMIUM"),
        @JsonSubTypes.Type(value = DefaultChargingPlan.class, name = "DEFAULT")
})
public abstract class ChargingPlan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plan_name",unique = true, nullable = false)
    private String planName;

    @Column(name = "rate_per_unit",nullable = false)
    private BigDecimal ratePerUnit;

    @Column(name = "price_per_unit",nullable = false)
    private BigDecimal pricePerUnit;


    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToMany(mappedBy = "chargingPlans")
    private Set<User> users = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "Charging_plan_type",nullable = false)
    private ChargingPlanType chargingPlanType;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;


    public void softDelete() {
        this.isDeleted = true;
    }



}
