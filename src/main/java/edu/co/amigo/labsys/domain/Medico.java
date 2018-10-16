package edu.co.amigo.labsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;

import edu.co.amigo.labsys.domain.enumeration.Especialidad;

/**
 * A Medico.
 */
@Entity
@Table(name = "medico")
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "documento")
    private String documento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "especialidad")
    private Especialidad especialidad;

    @Column(name = "tarjeta_profesional")
    private String tarjetaProfesional;

    @OneToMany(mappedBy = "medico")
    private Set<Orden> ordens = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("medicos")
    private EntidadSalud entidadSalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public Medico tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public Medico documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public Medico nombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Medico apellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Medico especialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
        return this;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getTarjetaProfesional() {
        return tarjetaProfesional;
    }

    public Medico tarjetaProfesional(String tarjetaProfesional) {
        this.tarjetaProfesional = tarjetaProfesional;
        return this;
    }

    public void setTarjetaProfesional(String tarjetaProfesional) {
        this.tarjetaProfesional = tarjetaProfesional;
    }

    public Set<Orden> getOrdens() {
        return ordens;
    }

    public Medico ordens(Set<Orden> ordens) {
        this.ordens = ordens;
        return this;
    }

    public Medico addOrden(Orden orden) {
        this.ordens.add(orden);
        orden.setMedico(this);
        return this;
    }

    public Medico removeOrden(Orden orden) {
        this.ordens.remove(orden);
        orden.setMedico(null);
        return this;
    }

    public void setOrdens(Set<Orden> ordens) {
        this.ordens = ordens;
    }

    public EntidadSalud getEntidadSalud() {
        return entidadSalud;
    }

    public Medico entidadSalud(EntidadSalud entidadSalud) {
        this.entidadSalud = entidadSalud;
        return this;
    }

    public void setEntidadSalud(EntidadSalud entidadSalud) {
        this.entidadSalud = entidadSalud;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medico medico = (Medico) o;
        if (medico.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medico.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medico{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", tarjetaProfesional='" + getTarjetaProfesional() + "'" +
            "}";
    }
}
