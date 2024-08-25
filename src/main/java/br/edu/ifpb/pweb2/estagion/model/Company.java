package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Company extends User {
    @NotNull
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(max = 14)
    @Column(nullable = false)
    private String cnpj;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String address;

    @NotNull
    @Size(max = 20)
    @Column(nullable = false)
    private String contactPhone;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false)
    private String contactEmail;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false)
    private String contactPerson;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String mainActivity;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String companyUrl;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InternshipOffer> internshipOffers;

    @Lob
    private byte[] pdf;
}
