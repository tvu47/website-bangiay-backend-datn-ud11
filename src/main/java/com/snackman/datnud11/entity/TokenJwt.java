package com.snackman.datnud11.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@Entity
@Table(name = "token_jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenJwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "is_expire")
    private boolean isExpire;
    @Column(name = "is_iat")
    private boolean isIat;
    @Column(name = "username")
    private String username;
}
