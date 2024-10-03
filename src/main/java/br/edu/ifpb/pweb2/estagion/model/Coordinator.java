package br.edu.ifpb.pweb2.estagion.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coordinator extends User {
    @NotNull
    @Size(min = 2, max = 50)
    private String Name;
}
