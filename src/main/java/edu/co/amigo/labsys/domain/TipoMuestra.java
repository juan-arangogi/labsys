package edu.co.amigo.labsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A TipoMuestra.
 */
@Entity
@Table(name = "tipo_muestra")
public class TipoMuestra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JsonIgnoreProperties("tipoMuestras")
    private Recipiente recipiente;

    @OneToMany(mappedBy = "tipoMuestra")
    private Set<Procedimiento> procedimientos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoMuestra descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Recipiente getRecipiente() {
        return recipiente;
    }

    public TipoMuestra recipiente(Recipiente recipiente) {
        this.recipiente = recipiente;
        return this;
    }

    public void setRecipiente(Recipiente recipiente) {
        this.recipiente = recipiente;
    }

    public Set<Procedimiento> getProcedimientos() {
        return procedimientos;
    }

    public TipoMuestra procedimientos(Set<Procedimiento> procedimientos) {
        this.procedimientos = procedimientos;
        return this;
    }

    public TipoMuestra addProcedimiento(Procedimiento procedimiento) {
        this.procedimientos.add(procedimiento);
        procedimiento.setTipoMuestra(this);
        return this;
    }

    public TipoMuestra removeProcedimiento(Procedimiento procedimiento) {
        this.procedimientos.remove(procedimiento);
        procedimiento.setTipoMuestra(null);
        return this;
    }

    public void setProcedimientos(Set<Procedimiento> procedimientos) {
        this.procedimientos = procedimientos;
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
        TipoMuestra tipoMuestra = (TipoMuestra) o;
        if (tipoMuestra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoMuestra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoMuestra{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
