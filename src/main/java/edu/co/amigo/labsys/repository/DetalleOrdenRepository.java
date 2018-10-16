package edu.co.amigo.labsys.repository;

import edu.co.amigo.labsys.domain.DetalleOrden;
import edu.co.amigo.labsys.domain.Orden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the DetalleOrden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findAllByOrden(Orden orden);
}
