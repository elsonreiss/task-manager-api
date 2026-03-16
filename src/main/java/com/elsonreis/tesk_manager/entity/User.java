package com.elsonreis.tesk_manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode ser vazio!")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String name;

    @NotBlank(message = "O email não pode estar vazio!")
    @Email(message = "o email precisa ser válido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    @Size(min = 6, message = "A senha precisa de no minimo 6 caracteres")
    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Board> boards;
}
