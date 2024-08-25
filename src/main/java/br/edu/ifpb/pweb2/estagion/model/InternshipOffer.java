package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InternshipOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String mainActivity;

    @NotNull
    private String weeklyWorkload;

    private String stipendAmount;

    private Boolean transportVoucher;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusInternshipOffer status;

    @NotNull
    private String prerequisites;
    @NotNull
    private String requiredSkills;
    @NotNull
    private String desiredSkills;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @ToString.Exclude
    private Company company;
}
