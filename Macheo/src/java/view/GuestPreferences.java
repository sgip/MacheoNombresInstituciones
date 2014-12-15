/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author marcos
 */
import entity.util.JpaUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@ManagedBean(name = "guestPreferences")
@SessionScoped

public class GuestPreferences implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    public GuestPreferences() {  
        // obtener datos iniciales
        this.obtenerDatosUsuario();
    }  
    // </editor-fold>
    //private String theme = "aristo"; //default

    private String theme;

    public String getTheme() {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if(params.containsKey("theme")) {
                    theme = params.get("theme");
            }

            return theme;
    }

    public void setTheme(String theme) {
            this.theme = theme;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Método para obtener datos de usuario logeado">
    public void obtenerDatosUsuario() {
        theme = "redmond";
    }
    // </editor-fold>   
}
