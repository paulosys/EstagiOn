package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Authority.AuthorityId> {
    List<Authority> findByIdUsername(String username);
}
