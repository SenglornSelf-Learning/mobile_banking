package com.mobile_banking.accountManagement.entity;

import java.util.List;

import com.mobile_banking.commons.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import lombok.Setter;

@Entity 
@Table(name = "user_accounts")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_number", nullable = false, length = 10)
    private String accountNumber;
    
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(name = "user_accounts", joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false), 
    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    private List<User> users;

}
