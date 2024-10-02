package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InternshipOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "O título da oferta é obrigatório")
    @Size(min = 1, max = 150, message = "O título da oferta deve ter entre 1 e 50 caracteres")
    private String title;

    @NotNull(message = "A descrição é obrigatória")
    @Size(min = 10, max = 1000, message = "A descrição deve ter entre 10 e 1000 caracteres")
    @Column(length = 1000, nullable = false)
    private String description;

    @NotNull(message = "A principal atividade é obrigatória")
    @Size(min = 10, max = 500, message = "A principal atividade deve ter entre 10 e 500 caracteres")
    @Column(length = 500, nullable = false)
    private String mainActivity;

    @NotNull(message = "A carga horária semanal é obrigatória")
    private String weeklyWorkload;

    private String stipendAmount;

    private Boolean transportVoucher;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusInternshipOffer status;

    @NotNull(message = "Os pré-requisitos são obrigatórios")
    @Size(min = 1, max = 1000, message = "Os pré-requisitos devem ter entre 10 e 1000 caracteres")
    @Column(length = 1000, nullable = false)
    private String prerequisites;

    @NotNull(message = "As habilidades requeridas são obrigatórias")
    @Size(min = 1, max = 1000, message = "As habilidades requeridas devem ter entre 10 e 1000 caracteres")
    @Column(length = 1000, nullable = false)
    private String requiredSkills;

    @NotNull(message = "As habilidades desejadas são obrigatórias")
    @Size(min = 1, max = 1000, message = "As habilidades desejadas devem ter entre 10 e 1000 caracteres")
    @Column(length = 1000, nullable = false)
    private String desiredSkills;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @ToString.Exclude
    private Company company;

    private Boolean isFilled;
}