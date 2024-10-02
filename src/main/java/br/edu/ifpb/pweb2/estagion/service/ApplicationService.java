package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.EApplicationStatus;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.Student;
import br.edu.ifpb.pweb2.estagion.repositories.ApplicationRepository;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipOfferRepository;
import br.edu.ifpb.pweb2.estagion.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public void applyForInternship(Student student, InternshipOffer internshipOffer) {
        Application application = new Application();

        application.setStudent(student);
        application.setInternshipOffer(internshipOffer);
        application.setStauts(EApplicationStatus.APPLIED);

        applicationRepository.save(application);
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Transactional
    public List<Application> findAllByStauts(EApplicationStatus status) {
        return applicationRepository.findAllByStauts(status);
    }
}
