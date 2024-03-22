package br.edu.ifpb.pweb2.estagion.dao;

import br.edu.ifpb.pweb2.estagion.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentDAO implements DAO<Student>{
    private static final Map<Long, Student> students = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public void create(Student student) {
        student.setId(nextId++);
        students.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public void delete(Long id) {
        students.remove(id);
    }

    @Override
    public Student find(Long id) {
        return students.get(id);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }
}
