/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import org.primefaces.context.RequestContext;
import util.JSFMessageUtil;

/**
 *
 * @author marcos
 */

public class AbstractMB {

    private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";

    public AbstractMB() {
        super();
    }

    protected void displayErrorMessageToUser(String message, String detail) {
        JSFMessageUtil messageUtil = new JSFMessageUtil();
        messageUtil.sendErrorMessageToUser(message, detail);
    }

    protected void displayInfoMessageToUser(String message, String detail) {
        JSFMessageUtil messageUtil = new JSFMessageUtil();
        messageUtil.sendInfoMessageToUser(message, detail);
    }

    protected void closeDialog() {
        getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);
    }

    protected void keepDialogOpen() {
        getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);
    }

    protected RequestContext getRequestContext() {
        return RequestContext.getCurrentInstance();
    }
}