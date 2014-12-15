/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lilian
 */
@Entity
@Table(name = "instituciones_macheada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InstitucionesMacheada.findAll", query = "SELECT i FROM InstitucionesMacheada i"),
    @NamedQuery(name = "InstitucionesMacheada.findByIdInstitucionesMacheadas", query = "SELECT i FROM InstitucionesMacheada i WHERE i.idInstitucionesMacheadas = :idInstitucionesMacheadas")})
public class InstitucionesMacheada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_instituciones_macheadas")
    private Integer idInstitucionesMacheadas;
    @JoinColumn(name = "institucion2", referencedColumnName = "id_institucion")
    @ManyToOne
    private InstitucionTemp institucion2;
    @JoinColumn(name = "institucion1", referencedColumnName = "id_institucion")
    @ManyToOne
    private InstitucionTemp institucion1;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;

    public InstitucionesMacheada() {
    }

    public InstitucionesMacheada(Integer idInstitucionesMacheadas) {
        this.idInstitucionesMacheadas = idInstitucionesMacheadas;
    }

    public Integer getIdInstitucionesMacheadas() {
        return idInstitucionesMacheadas;
    }

    public void setIdInstitucionesMacheadas(Integer idInstitucionesMacheadas) {
        this.idInstitucionesMacheadas = idInstitucionesMacheadas;
    }

    public InstitucionTemp getInstitucion2() {
        return institucion2;
    }

    public void setInstitucion2(InstitucionTemp institucion2) {
        this.institucion2 = institucion2;
    }

    public InstitucionTemp getInstitucion1() {
        return institucion1;
    }

    public void setInstitucion1(InstitucionTemp institucion1) {
        this.institucion1 = institucion1;
    }
    
    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInstitucionesMacheadas != null ? idInstitucionesMacheadas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstitucionesMacheada)) {
            return false;
        }
        InstitucionesMacheada other = (InstitucionesMacheada) object;
        if ((this.idInstitucionesMacheadas == null && other.idInstitucionesMacheadas != null) || (this.idInstitucionesMacheadas != null && !this.idInstitucionesMacheadas.equals(other.idInstitucionesMacheadas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.InstitucionesMacheada[ idInstitucionesMacheadas=" + idInstitucionesMacheadas + " ]";
    }
    
}
