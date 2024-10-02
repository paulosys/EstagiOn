package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    @Transactional
    public  List<InternshipOffer> findByCompanyId(Integer company) {
        List<InternshipOffer> result = internshipOfferRepository.findByCompanyId(company);
        return result;
    }

    @Transactional
    public List<InternshipOffer> findByStatus(StatusInternshipOffer status) {
        List<InternshipOffer> result = internshipOfferRepository.findByStatus(status);
        return result;
    }

    public InternshipOffer findById(int id) {
        return internshipOfferRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        InternshipOffer internshipOffer = this.findById(id);
        internshipOfferRepository.delete(internshipOffer);
    }

    public void save(InternshipOffer internshipOffer, Company company) {
        var status = statusInternshipOfferService.findById(1);

        internshipOffer.setCompany(company);
        internshipOffer.setStatus(status);

        var offer = internshipOfferRepository.save(internshipOffer);

        companyService.saveInternshipOffer(company, offer);
    }

    @Transactional
    public List<InternshipOffer> findByWeeklyWorkload(String weeklyWorkload) {
        return internshipOfferRepository.findByWeeklyWorkload(weeklyWorkload);
    }
}
