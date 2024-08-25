package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.StatusInternshipOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusInternshipOfferService {
    @Autowired
    private StatusInternshipOfferRepository statusInternshipOfferRepository;

    public List<StatusInternshipOffer> findAll() {
        return statusInternshipOfferRepository.findAll();
    }

    public StatusInternshipOffer findById(int id) {
        return statusInternshipOfferRepository.findById(id).orElse(null);
    }
}
