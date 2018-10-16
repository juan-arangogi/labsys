package edu.co.amigo.labsys.repository;

import edu.co.amigo.labsys.domain.TipoMuestra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoMuestra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoMuestraRepository extends JpaRepository<TipoMuestra, Long> {

}
