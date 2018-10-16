package edu.co.amigo.labsys.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;

/**
 * A EntidadSalud.
 */
@Entity
@Table(name = "entidad_salud")
public class EntidadSalud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "documento")
    private String documento;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @OneToMany(mappedBy = "entidadSalud")
    private Set<Orden> ordens = new HashSet<>();

    @OneToMany(mappedBy = "entidadSalud")
    private Set<Medico> medicos = new HashSet<>();

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

    public EntidadSalud tipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public EntidadSalud documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public EntidadSalud razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public EntidadSalud direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public EntidadSalud telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Orden> getOrdens() {
        return ordens;
    }

    public EntidadSalud ordens(Set<Orden> ordens) {
        this.ordens = ordens;
        return this;
    }

    public EntidadSalud addOrden(Orden orden) {
        this.ordens.add(orden);
        orden.setEntidadSalud(this);
        return this;
    }

    public EntidadSalud removeOrden(Orden orden) {
        this.ordens.remove(orden);
        orden.setEntidadSalud(null);
        return this;
    }

    public void setOrdens(Set<Orden> ordens) {
        this.ordens = ordens;
    }

    public Set<Medico> getMedicos() {
        return medicos;
    }

    public EntidadSalud medicos(Set<Medico> medicos) {
        this.medicos = medicos;
        return this;
    }

    public EntidadSalud addMedico(Medico medico) {
        this.medicos.add(medico);
        medico.setEntidadSalud(this);
        return this;
    }

    public EntidadSalud removeMedico(Medico medico) {
        this.medicos.remove(medico);
        medico.setEntidadSalud(null);
        return this;
    }

    public void setMedicos(Set<Medico> medicos) {
        this.medicos = medicos;
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
        EntidadSalud entidadSalud = (EntidadSalud) o;
        if (entidadSalud.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadSalud.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadSalud{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
