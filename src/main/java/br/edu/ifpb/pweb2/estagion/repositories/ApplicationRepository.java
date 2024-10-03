package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.EApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Page<Application> findAllByStauts(EApplicationStatus status, Pageable pageable);
    Page<Application> findByStudent_id(Integer id, Pageable pageable);

    @Query("SELECT a FROM Application a WHERE a.internshipOffer.id = :internshipOfferId and a.stauts <> 'ACCEPTED'")
    List<Application> ListApplicatioByOffer(@Param("internshipOfferId") Integer internshipOfferId);

    Application findByInternshipOfferIdAndStudentId(Integer offerId, Integer studentId);
}
