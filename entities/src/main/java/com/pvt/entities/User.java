package com.pvt.entities;

import com.pvt.constants.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes <tt>User</tt> entity
 */

@javax.persistence.Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "orders", callSuper = false)
@ToString(exclude = {"password", "orders"})
public class User extends Entity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Integer userId;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

}
