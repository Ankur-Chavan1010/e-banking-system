package com.ebanking.entity;

import com.ebanking.constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private UUID roleId;


    @Enumerated(EnumType.STRING)
    @Column(name = "role_name" , nullable = false , unique = true)
    private RoleType roleName;
}
