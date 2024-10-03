package br.edu.ifpb.pweb2.estagion.model;

import br.edu.ifpb.pweb2.estagion.validators.CNPJ;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @NotNull(message = "O CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter exatamente 14 caracteres")
    @CNPJ
    @Column(nullable = false, length = 14)
    private String cnpj;

    @NotNull(message = "O endereço é obrigatório")
    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "O telefone de contato é obrigatório")
    @Size(max = 20, message = "O telefone de contato deve ter no máximo 20 caracteres")
    @Column(nullable = false, length = 20)
    private String contactPhone;

    @NotNull(message = "O email de contato é obrigatório")
    @Email(message = "O email de contato deve ser válido")
    @Size(max = 100, message = "O email de contato deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String contactEmail;

    @NotNull(message = "A pessoa de contato é obrigatória")
    @Size(max = 100, message = "A pessoa de contato deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String contactPerson;

    @NotNull(message = "A principal atividade é obrigatória")
    @Size(max = 255, message = "A principal atividade deve ter no máximo 255 caracteres")
    @Column(nullable = false)
    private String mainActivity;

    @NotNull(message = "O URL da empresa é obrigatório")
    @Size(max = 255, message = "O URL da empresa deve ter no máximo 255 caracteres")
    @Column(nullable = false)
    private String companyUrl;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InternshipOffer> internshipOffers;

    @Lob
    private byte[] pdf;
}
