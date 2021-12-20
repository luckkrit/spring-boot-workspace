package com.k9.backend.shopee.models;

import java.util.List;

import javax.persistence.Entity;
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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotBlank
    private String password;
    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
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
