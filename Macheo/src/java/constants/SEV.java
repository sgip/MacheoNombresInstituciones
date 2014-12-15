/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author marcos
 */
public class SEV {
    
    public static final String ENTERPRISE_APPLICATION_CODE = J2EE.ENTERPRISE_APPLICATION_CODE.toUpperCase();

    public static final String ENTERPRISE_APPLICATION_DIR = ENTERPRISE_APPLICATION_CODE + "_DIR";

    public static final String ENTERPRISE_APPLICATION_EAR = ENTERPRISE_APPLICATION_CODE + "_EAR";

    public static final String ENTERPRISE_APPLICATION_WAR = ENTERPRISE_APPLICATION_CODE + "_WAR";

    public static final String ENTERPRISE_APPLICATION_JDBC_DRIVER = ENTERPRISE_APPLICATION_CODE + "_" + EAP.JDBC_DRIVER;

    public static final String ENTERPRISE_APPLICATION_JDBC_URL = ENTERPRISE_APPLICATION_CODE + "_" + EAP.JDBC_URL;

    public static final String ENTERPRISE_APPLICATION_JDBC_USER = ENTERPRISE_APPLICATION_CODE + "_" + EAP.JDBC_USER;

    public static final String ENTERPRISE_APPLICATION_JDBC_PASSWORD = ENTERPRISE_APPLICATION_CODE + "_" + EAP.JDBC_PASSWORD;

    public static final String ENTERPRISE_APPLICATION_PROPERTIES = ENTERPRISE_APPLICATION_CODE + "_PROPERTIES";

    public static final String ENTERPRISE_VELOCITY_FILE_RESOURCE_LOADER_PATH = ENTERPRISE_APPLICATION_CODE + "_" + EAP.VELOCITY_FILE_RESOURCE_LOADER_PATH;

    public static final String JAVA_PARAMETER_LIST = "JAVA_PARAMETER_LIST";

    public static final String EXPORT_RUNNER_LOG = "EXPORT_RUNNER_LOG";

    public static final String REPORT_RUNNER_LOG = "REPORT_RUNNER_LOG";

    public static final String SQLPRC_RUNNER_LOG = "SQLPRC_RUNNER_LOG";
}