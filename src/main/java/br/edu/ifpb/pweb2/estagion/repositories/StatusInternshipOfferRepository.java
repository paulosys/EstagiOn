package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusInternshipOfferRepository extends JpaRepository<StatusInternshipOffer, Integer> {
}
