/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author marcos
 */
public class J2EE {
    
    public static final String ENTERPRISE_APPLICATION_CODE = "atenea";

    public static final String ENTERPRISE_APPLICATION_NAME = "Sigmec - Sistema Integrado para la Gestión del MEC";

    public static final String ENTERPRISE_APPLICATION_JDBC_DATABASE = "jdbc/" + ENTERPRISE_APPLICATION_CODE;

    public static final String ENTERPRISE_APPLICATION_JDBC_DATASOURCE = "java:comp/env/" + ENTERPRISE_APPLICATION_JDBC_DATABASE;
}