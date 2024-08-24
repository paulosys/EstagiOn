package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Company extends User {
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 14)
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 20)
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Size(max = 100)
    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Size(max = 100)
    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Size(max = 255)
    @Column(name = "main_activity", length = 255)
    private String mainActivity;

    @Size(max = 255)
    @Column(name = "company_url", length = 255)
    private String companyUrl;

    @Lob
    @Column(name = "pdf")
    private byte[] pdf;
}
