package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Authority;
import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.AuthorityRepository;
import br.edu.ifpb.pweb2.estagion.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional
    public List<Company> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Company findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Company findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Transactional
    public Company findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj).orElse(null);
    }

    public void saveInternshipOffer(Company company, InternshipOffer offer) {
        company.getInternshipOffers().add(offer);
        repository.save(company);
    }

    @Transactional
    public void save(Company company) {
        Optional<Company> companyCnpj = repository.findByCnpj(company.getCnpj());

        if (companyCnpj.isPresent()) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }

        company.setPassword(passwordEncoder.encode(company.getPassword()));

        Authority authority = new Authority();
        authority.setId(new Authority.AuthorityId(company.getUsername(), "ROLE_COMPANY"));
        authority.setUsername(company);
        authority.setAuthority("ROLE_COMPANY");

        repository.save(company);
        authorityRepository.save(authority);
    }
}
