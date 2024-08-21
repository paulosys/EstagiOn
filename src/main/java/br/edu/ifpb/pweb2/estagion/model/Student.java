package br.edu.ifpb.pweb2.estagion.model;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String cpf;
    private String course;
    private String institution;
    private String city;
    private String state;
    private List<String> skills;
}
