package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.dao.StudentDAO;
import br.edu.ifpb.pweb2.estagion.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDAO studentDAO;

    public void saveStudent(Student student) {
        studentDAO.create(student);
    }

    public Student findStudentById(Long id) {
        return studentDAO.find(id);
    }

    public List<Student> findAllStudents() {
        return studentDAO.findAll();
    }

    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    public void deleteStudent(Long id) {
        studentDAO.delete(id);
    }
}
