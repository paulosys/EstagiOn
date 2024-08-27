package br.edu.ifpb.pweb2.estagion.service;

import br.edu.ifpb.pweb2.estagion.model.Coordinator;
import br.edu.ifpb.pweb2.estagion.repositories.CoordinatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;
    public Coordinator findByEmail(String email) {
        return coordinatorRepository.findByEmail(email).orElse(null);
    }

    public void save(Coordinator coordinator) {
        coordinatorRepository.save(coordinator);
    }

    public Coordinator tryAuthenticate(String email, String password) {
        Coordinator coordinator = coordinatorRepository.findByEmail(email).orElse(null);

        if (coordinator != null && coordinator.getPassword().equals(password)) {
            return coordinator;
        }

        return null;
    }
}
