package br.edu.ifpb.pweb2.estagion.repositories;

import br.edu.ifpb.pweb2.estagion.model.Application;
import br.edu.ifpb.pweb2.estagion.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByCnpj(String cnpj);

    @Query("SELECT a FROM Application a WHERE a.internshipOffer.id = :idOfertaEstagio")
    List<Application> findByOfertaEstagio_IdOfertaEstagio(@Param("idOfertaEstagio") Long idOfertaEstagio);
}
