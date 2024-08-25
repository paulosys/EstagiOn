package br.edu.ifpb.pweb2.estagion.seeder;

import br.edu.ifpb.pweb2.estagion.model.Skill;
import br.edu.ifpb.pweb2.estagion.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public void run(String... args) {
        try {
            seedSkills();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}
