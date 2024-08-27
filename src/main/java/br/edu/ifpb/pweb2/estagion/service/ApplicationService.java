package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.repositories.ApplicationRepository;
import br.edu.ifpb.pweb2.estagion.repositories.CompanyRepository;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipOfferRepository;
import br.edu.ifpb.pweb2.estagion.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InternshipOfferRepository internshipOfferRepository;

    public void applyForInternship(Integer studentId, Integer internshipOfferId) {

        Optional<Student> student = studentRepository.findById(studentId);
        Optional<InternshipOffer> internshipOffer = internshipOfferRepository.findById(internshipOfferId);

        if (student != null && internshipOffer != null) {
            Application application = new Application();

            application.setStudent(student.get());
            application.setInternshipOffer(internshipOffer.get());
            application.setStauts("applied");
            applicationRepository.save(application);
        }
    }
}
