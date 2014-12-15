/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lilian
 */
@Entity
@Table(name = "institucion_temp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InstitucionTemp.findAll", query = "SELECT i FROM InstitucionTemp i"),
    @NamedQuery(name = "InstitucionTemp.findByIdInstitucion", query = "SELECT i FROM InstitucionTemp i WHERE i.idInstitucion = :idInstitucion"),
    @NamedQuery(name = "InstitucionTemp.findByNombreIntitucion", query = "SELECT i FROM InstitucionTemp i WHERE i.nombreIntitucion = :nombreIntitucion"),
    @NamedQuery(name = "InstitucionTemp.findByCodigoDepartamento", query = "SELECT i FROM InstitucionTemp i WHERE i.codigoDepartamento = :codigoDepartamento"),
    @NamedQuery(name = "InstitucionTemp.findByNombreDepartamento", query = "SELECT i FROM InstitucionTemp i WHERE i.nombreDepartamento = :nombreDepartamento"),
    @NamedQuery(name = "InstitucionTemp.findByCodigoDistrito", query = "SELECT i FROM InstitucionTemp i WHERE i.codigoDistrito = :codigoDistrito"),
    @NamedQuery(name = "InstitucionTemp.findByNombreDistrito", query = "SELECT i FROM InstitucionTemp i WHERE i.nombreDistrito = :nombreDistrito"),
    @NamedQuery(name = "InstitucionTemp.findByCodigoPrincipal", query = "SELECT i FROM InstitucionTemp i WHERE i.codigoPrincipal = :codigoPrincipal"),
    @NamedQuery(name = "InstitucionTemp.findByCodigoSecundario", query = "SELECT i FROM InstitucionTemp i WHERE i.codigoSecundario = :codigoSecundario"),
    @NamedQuery(name = "InstitucionTemp.findByTipo", query = "SELECT i FROM InstitucionTemp i WHERE i.tipo = :tipo")})
public class InstitucionTemp implements Serializable {
    @Size(max = 500)
    @Column(name = "nombre_institucion_original")
    private String nombreInstitucionOriginal;
    @OneToMany(mappedBy = "institucion2")
    private Collection<InstitucionesMacheada> institucionesMacheadaCollection;
    @OneToMany(mappedBy = "institucion1")
    private Collection<InstitucionesMacheada> institucionesMacheadaCollection1;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_institucion")
    private Integer idInstitucion;
    @Size(max = 500)
    @Column(name = "nombre_intitucion")
    private String nombreIntitucion;
    @Size(max = 20)
    @Column(name = "codigo_departamento")
    private String codigoDepartamento;
    @Size(max = 100)
    @Column(name = "nombre_departamento")
    private String nombreDepartamento;
    @Size(max = 20)
    @Column(name = "codigo_distrito")
    private String codigoDistrito;
    @Size(max = 100)
    @Column(name = "nombre_distrito")
    private String nombreDistrito;
    @Size(max = 20)
    @Column(name = "codigo_principal")
    private String codigoPrincipal;
    @Size(max = 20)
    @Column(name = "codigo_secundario")
    private String codigoSecundario;
    @Column(name = "tipo")
    private Integer tipo;

    public InstitucionTemp() {
    }

    public InstitucionTemp(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getNombreIntitucion() {
        return nombreIntitucion;
    }

    public void setNombreIntitucion(String nombreIntitucion) {
        this.nombreIntitucion = nombreIntitucion;
    }

    public String getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(String codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getCodigoSecundario() {
        return codigoSecundario;
    }

    public void setCodigoSecundario(String codigoSecundario) {
        this.codigoSecundario = codigoSecundario;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstitucion != null ? idInstitucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstitucionTemp)) {
            return false;
        }
        InstitucionTemp other = (InstitucionTemp) object;
        if ((this.idInstitucion == null && other.idInstitucion != null) || (this.idInstitucion != null && !this.idInstitucion.equals(other.idInstitucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.InstitucionTemp[ idInstitucion=" + idInstitucion + " ]";
    }

    @XmlTransient
    public Collection<InstitucionesMacheada> getInstitucionesMacheadaCollection() {
        return institucionesMacheadaCollection;
    }

    public void setInstitucionesMacheadaCollection(Collection<InstitucionesMacheada> institucionesMacheadaCollection) {
        this.institucionesMacheadaCollection = institucionesMacheadaCollection;
    }

    @XmlTransient
    public Collection<InstitucionesMacheada> getInstitucionesMacheadaCollection1() {
        return institucionesMacheadaCollection1;
    }

    public void setInstitucionesMacheadaCollection1(Collection<InstitucionesMacheada> institucionesMacheadaCollection1) {
        this.institucionesMacheadaCollection1 = institucionesMacheadaCollection1;
    }

    public String getNombreInstitucionOriginal() {
        return nombreInstitucionOriginal;
    }

    public void setNombreInstitucionOriginal(String nombreInstitucionOriginal) {
        this.nombreInstitucionOriginal = nombreInstitucionOriginal;
    }
    
}
