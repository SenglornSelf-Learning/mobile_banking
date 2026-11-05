package com.mobile_banking.roleAuthority.model;

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
@Table(name = "tbl_authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "authority_name", nullable = false, length = 50, unique = true)
    private String authorityName;

    // Inverse side of the relationship with Roles
    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private List<RoleAuthority> roleAuthorities = new ArrayList<>();
}
