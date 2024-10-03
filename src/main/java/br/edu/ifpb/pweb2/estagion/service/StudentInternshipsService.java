package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.ApplicationRepository;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInternshipsService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Transactional
    public Page<Application> findByStudentId(Integer id, Pageable pageable) {
        return applicationRepository.findByStudent_id(id, pageable);
    }

}
