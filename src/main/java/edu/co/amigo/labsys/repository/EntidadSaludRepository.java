package edu.co.amigo.labsys.repository;

import edu.co.amigo.labsys.domain.EntidadSalud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EntidadSalud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadSaludRepository extends JpaRepository<EntidadSalud, Long> {

}
