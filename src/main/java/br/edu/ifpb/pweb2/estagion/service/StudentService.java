package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.repositories.StudentRepository;
import br.edu.ifpb.pweb2.estagion.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Student student) {
        repository.save(student);
    }

    public Student tryAuthenticate(String username, String password) {
        Student student = repository.findByUsername(username).orElse(null);

        if (student != null && student.getPassword().equals(password)) {
            return student;
        }

        return null;
    }
}
