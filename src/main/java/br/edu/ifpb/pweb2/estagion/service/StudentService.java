package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Student student) throws Exception {
        boolean emailExists = repository.findByEmail(student.getEmail()).isPresent();
        boolean usernameExists = repository.findByUsername(student.getUsername()).isPresent();

        if (emailExists || usernameExists) {
            throw new Exception("Email ou nome de usuário já existe.");
        }

        student.setPassword(passwordEncoder.encode(student.getPassword()));

        repository.save(student);
    }
}
