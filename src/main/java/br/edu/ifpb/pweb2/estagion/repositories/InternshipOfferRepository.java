package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.Company;
import br.edu.ifpb.pweb2.estagion.model.InternshipOffer;
import br.edu.ifpb.pweb2.estagion.model.StatusInternshipOffer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Integer> {
    List<InternshipOffer> findByWeeklyWorkload(String weeklyWorkload);
    List<InternshipOffer> findByStatus(StatusInternshipOffer status);
    List<InternshipOffer> findByCompanyId(Integer id);
}
