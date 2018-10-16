package edu.co.amigo.labsys.repository;

import edu.co.amigo.labsys.domain.Procedimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Procedimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedimientoRepository extends JpaRepository<Procedimiento, Long> {

}
