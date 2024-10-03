package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull
    private Student student;

    @ManyToOne
    @JoinColumn(name = "internship_offer", nullable = false)
    @NotNull
    private InternshipOffer internshipOffer;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EApplicationStatus stauts;
}
