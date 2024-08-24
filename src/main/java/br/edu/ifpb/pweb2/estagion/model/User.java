package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;

    @NotNull
    @Email
    @Size(max = 100)
    private String email;
}
