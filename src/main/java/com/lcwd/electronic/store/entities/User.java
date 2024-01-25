package com.lcwd.electronic.store.entities;

import lombok.*;


import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class User   {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    @Column(name = "user_name")
    private  String name;
    @Column(name = "user_email",unique = true)
    private  String email;
    @Column(name = "user_password",length = 1000)
    private String password;
    private String gender;
    @Column(length = 1000)
    private  String about;
    @Column(name = "user_image_name")
    private String imagename;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Cart cart;



}
