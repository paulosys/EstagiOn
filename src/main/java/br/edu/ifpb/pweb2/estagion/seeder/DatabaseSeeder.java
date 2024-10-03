package br.edu.ifpb.pweb2.estagion.seeder;

import br.edu.ifpb.pweb2.estagion.model.Authority;
import br.edu.ifpb.pweb2.estagion.model.Coordinator;
import br.edu.ifpb.pweb2.estagion.model.Skill;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import br.edu.ifpb.pweb2.estagion.repositories.AuthorityRepository;
import br.edu.ifpb.pweb2.estagion.repositories.CoordinatorRepository;
import br.edu.ifpb.pweb2.estagion.repositories.SkillRepository;
import br.edu.ifpb.pweb2.estagion.repositories.StatusInternshipOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    StatusInternshipOfferRepository statusInternshipOfferRepository;

    @Autowired
    CoordinatorRepository coordinatorRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            seedCoordinator();
            seedSkills();
            seedInternshipOfferStatus();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void seedCoordinator() {
        if (coordinatorRepository.count() == 0) {
            Coordinator coordinator = new Coordinator();
            coordinator.setUsername("Coordenador");
            coordinator.setName("Frederico Pereira");
            coordinator.setEmail("coordenador@email.com");
            coordinator.setPassword(passwordEncoder.encode("estagion"));

            Authority authority = new Authority();
            authority.setId(new Authority.AuthorityId(coordinator.getUsername(), "ROLE_ADMIN"));
            authority.setUsername(coordinator);
            authority.setAuthority("ROLE_ADMIN");

            coordinator.setAuthorities(List.of(authority));

            coordinatorRepository.save(coordinator);
            authorityRepository.save(authority);
        }
    }

    private void seedSkills() {
        if (skillRepository.count() == 0) {
            skillRepository.save(new Skill("Java"));
            skillRepository.save(new Skill("Python"));
            skillRepository.save(new Skill("JavaScript"));
            skillRepository.save(new Skill("C#"));
            skillRepository.save(new Skill("C++"));
            skillRepository.save(new Skill("Ruby"));
            skillRepository.save(new Skill("PHP"));
            skillRepository.save(new Skill("Swift"));
            skillRepository.save(new Skill("Kotlin"));
            skillRepository.save(new Skill("Go"));
        }
    }

    private void seedInternshipOfferStatus() {
        if (statusInternshipOfferRepository.count() == 0) {
            statusInternshipOfferRepository.save(new StatusInternshipOffer("CRIADO"));
            statusInternshipOfferRepository.save(new StatusInternshipOffer("EM ANDAMENTO"));
            statusInternshipOfferRepository.save(new StatusInternshipOffer("OCULTO"));
        }
    }
}
