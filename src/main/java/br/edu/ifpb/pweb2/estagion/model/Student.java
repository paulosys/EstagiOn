package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends User {
    @NotNull(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 caracteres")
    private String cpf;

    @NotNull(message = "O primeiro nome é obrigatório")
    @Size(min = 2, max = 50, message = "O primeiro nome deve ter entre 2 e 50 caracteres")
    private String firstName;

    @NotNull(message = "O último nome é obrigatório")
    @Size(min = 2, max = 50, message = "O último nome deve ter entre 2 e 50 caracteres")
    private String lastName;

    @NotNull()
    @Size(max = 100, message = "O curso deve ter no máximo 100 caracteres")
    private String course;

    @NotNull()
    @Size(max = 100, message = "A instituição deve ter no máximo 100 caracteres")
    private String institution;

    @ManyToMany
    @JoinTable(
            name = "student_skill",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;
}
