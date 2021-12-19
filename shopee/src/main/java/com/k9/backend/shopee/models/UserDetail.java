package com.k9.backend.shopee.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;

@Entity
@Table(name = "userdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @NotBlank
    private String firstname;
    @NotBlank
    private String phone;
    @Id
    @SequenceGenerator(name = "userDetailSeqGen", sequenceName = "userDetailSeq", initialValue = 11, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userDetailSeqGen")
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String lastname;

    @OneToOne(mappedBy = "userDetail")
    User user;

}
