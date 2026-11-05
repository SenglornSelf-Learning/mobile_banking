package com.mobile_banking.transaction.model;
import java.math.BigDecimal;

import com.mobile_banking.commons.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;

@Entity 
@Table(name = "transactions")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "remarks", length = 255)
    private String remarks;
}
