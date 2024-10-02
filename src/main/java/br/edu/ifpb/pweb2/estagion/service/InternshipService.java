package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Internship;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipService {

    @Autowired
    private InternshipRepository internshipRepository;

    public void save(Internship estagio) {
        internshipRepository.save(estagio);
    }

    public Internship findById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Internship not found By ID: " + id));
    }

    public Page<Internship> listInternhipsInProgress(Pageable pageable){
        return internshipRepository.findAll(pageable);
    }
}
