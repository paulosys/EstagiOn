package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
