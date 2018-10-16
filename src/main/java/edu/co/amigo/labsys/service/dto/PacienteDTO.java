package edu.co.amigo.labsys.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import edu.co.amigo.labsys.domain.enumeration.TipoDocumento;
import edu.co.amigo.labsys.domain.enumeration.Genero;

/**
 * A DTO for the Paciente entity.
 */
public class PacienteDTO implements Serializable {

    private Long id;

    private TipoDocumento tipoDocumento;

    private String documento;

    private String nombres;

    private String apellidos;

    private Genero genero;

    private LocalDate fechaNacimiento;

    private String telefono;

    private String celular;

    private String email;

    private String direccion;

    private String ciudad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PacienteDTO pacienteDTO = (PacienteDTO) o;
        if (pacienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pacienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PacienteDTO{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", genero='" + getGenero() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", celular='" + getCelular() + "'" +
            ", email='" + getEmail() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            "}";
    }
}
