package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.InternshipOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Page<InternshipOffer> findByCompanyId(Integer company, Pageable page) {
        return internshipOfferRepository.findByCompanyId(company, page);
    }

    @Transactional
    public Page<InternshipOffer> findByStatus(StatusInternshipOffer status, Pageable pageable) {
        return internshipOfferRepository.findByStatus(status, pageable);
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
    public Page<InternshipOffer> findByWeeklyWorkload(String weeklyWorkload, Pageable pageable) {
        return internshipOfferRepository.findByWeeklyWorkload(weeklyWorkload, pageable);
    }

    @Transactional()
    public Optional<InternshipOffer> getOfferWithApplications(Integer id) {
        return internshipOfferRepository.findById(id);
    }
    public InternshipOffer updateOffer(InternshipOffer internshipOffer) {
        return internshipOfferRepository.save(internshipOffer);
    }
}
