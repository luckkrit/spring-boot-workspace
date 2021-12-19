package com.k9.backend.shopee.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank
    private String password;
    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 11, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userSeqGen")
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String username;

    @OneToOne
    @JoinColumn
    Address address;

    @OneToOne
    @JoinColumn
    UserDetail userDetail;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

}
