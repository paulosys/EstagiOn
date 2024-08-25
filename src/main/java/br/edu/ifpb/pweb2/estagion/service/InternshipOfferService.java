package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipOfferService {
    @Autowired
    private InternshipOfferRepository internshipOfferRepository;

    @Autowired
    private StatusInternshipOfferService statusInternshipOfferService;

    @Autowired
    private CompanyService companyService;

    public List<InternshipOffer> findAll() {
        return internshipOfferRepository.findAll();
    }

    public InternshipOffer findById(int id) {
        return internshipOfferRepository.findById(id).orElse(null);
    }

    public void save(InternshipOffer internshipOffer, Integer companyId) {
        var status = statusInternshipOfferService.findById(1);
        var company = companyService.findById(companyId);

        internshipOffer.setCompany(company);
        internshipOffer.setStatus(status);

        var offer = internshipOfferRepository.save(internshipOffer);

        company.getInternshipOffers().add(offer);
        companyService.save(company);
    }
}
