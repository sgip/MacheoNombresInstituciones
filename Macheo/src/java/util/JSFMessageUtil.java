/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author marcos
 */

public class JSFMessageUtil {

    public void sendInfoMessageToUser(String message, String detail) {
        FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, message, detail);
        addMessageToJsfContext(facesMessage);
    }

    public void sendErrorMessageToUser(String message, String detail) {
        FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_ERROR, message, detail);
        addMessageToJsfContext(facesMessage);
    }

    private FacesMessage createMessage(Severity severity, String mensagemErro, String detalleError) {
        return new FacesMessage(severity, mensagemErro, detalleError);
    }

    private void addMessageToJsfContext(FacesMessage facesMessage) {
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}