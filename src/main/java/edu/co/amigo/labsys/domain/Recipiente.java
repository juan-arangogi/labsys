package edu.co.amigo.labsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Recipiente.
 */
@Entity
@Table(name = "recipiente")
public class Recipiente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "recipiente")
    private Set<TipoMuestra> tipoMuestras = new HashSet<>();
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

    public Recipiente descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<TipoMuestra> getTipoMuestras() {
        return tipoMuestras;
    }

    public Recipiente tipoMuestras(Set<TipoMuestra> tipoMuestras) {
        this.tipoMuestras = tipoMuestras;
        return this;
    }

    public Recipiente addTipoMuestra(TipoMuestra tipoMuestra) {
        this.tipoMuestras.add(tipoMuestra);
        tipoMuestra.setRecipiente(this);
        return this;
    }

    public Recipiente removeTipoMuestra(TipoMuestra tipoMuestra) {
        this.tipoMuestras.remove(tipoMuestra);
        tipoMuestra.setRecipiente(null);
        return this;
    }

    public void setTipoMuestras(Set<TipoMuestra> tipoMuestras) {
        this.tipoMuestras = tipoMuestras;
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
        Recipiente recipiente = (Recipiente) o;
        if (recipiente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipiente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipiente{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
