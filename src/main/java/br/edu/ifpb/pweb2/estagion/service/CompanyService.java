package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(int id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Transactional
    public Company findByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj).orElse(null);
    }

    @Transactional
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Transactional
    public Company tryAuthenticate(String email, String password) {
        Company company = companyRepository.findByEmail(email).orElse(null);

        if (company != null && company.getPassword().equals(password)) {
            return company;
        }

        return null;
    }
}
