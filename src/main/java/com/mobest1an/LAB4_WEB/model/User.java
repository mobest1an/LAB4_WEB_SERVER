package com.mobest1an.LAB4_WEB.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue()
    private Long id;
    private String username;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Check> checkList;
}
