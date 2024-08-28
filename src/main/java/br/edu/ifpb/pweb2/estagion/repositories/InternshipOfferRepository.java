package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Integer> {
    List<InternshipOffer> findByWeeklyWorkload(String weeklyWorkload);
    List<InternshipOffer> findByStatus(StatusInternshipOffer status);
    List<InternshipOffer> findByCompanyId(Integer id);
}
