package com.mobile_banking.userAccount.model;

import java.util.ArrayList;
import java.util.List;

import com.mobile_banking.commons.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_account_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 30, unique = true)
    private String name;

    @OneToMany(mappedBy = "accountType", fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();
}
