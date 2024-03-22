package br.edu.ifpb.pweb2.estagion.model;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String course;
    private String institution;
    private List<String> skills;
}
