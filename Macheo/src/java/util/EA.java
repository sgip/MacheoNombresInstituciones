/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import constants.EAP;
import constants.J2EE;
import constants.SEV;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author marcos
 */
public class EA {
    
    static {
        init();
    }

    private static final String DIR = "$DIR";

    private static final String EAR = "$EAR";

    private static final String WAR = "$WAR";

    private static final String WEB = "$WEB";

    private static final String JDBC_DRIVER = "$" + EAP.JDBC_DRIVER;

    private static final String JDBC_URL = "$" + EAP.JDBC_URL;

    private static final String JDBC_USER = "$" + EAP.JDBC_USER;

    private static final String JDBC_PASSWORD = "$" + EAP.JDBC_PASSWORD;

    private static final String EYE_CATCHER = "********************";

    private static boolean pdq;

    private static String dir;

    private static String ear;

    private static String war;

    private static String cpf;

    private static String vlp;

    private static String jdbc_driver;

    private static String jdbc_url;

    private static String jdbc_user;

    private static String jdbc_password;

    private static Properties properties;

    private static final int CHECK_NOT_BLANK = 0;

    private static final int CHECK_EXIST = 1;

    private static final int CHECK_DIRECTORY = 2;

    private static final int CHECK_FILE = 3;

    private static final int CHECK_PATH = 4;

    private static void init() {
        pdq = false;
        getProperty("os.name");
        getProperty("os.version");
        getProperty("os.arch");
        getProperty("file.separator");
        getProperty("path.separator");
        getProperty("user.dir");
        dir = getenv(SEV.ENTERPRISE_APPLICATION_DIR, CHECK_DIRECTORY);
        ear = getenv(SEV.ENTERPRISE_APPLICATION_EAR, CHECK_DIRECTORY);
        war = getenv(SEV.ENTERPRISE_APPLICATION_WAR, CHECK_DIRECTORY);
        cpf = getenv(SEV.ENTERPRISE_APPLICATION_PROPERTIES, CHECK_FILE);
        vlp = getenv(SEV.ENTERPRISE_VELOCITY_FILE_RESOURCE_LOADER_PATH, CHECK_PATH);
        jdbc_driver = getenv(SEV.ENTERPRISE_APPLICATION_JDBC_DRIVER, CHECK_NOT_BLANK);
        jdbc_url = getenv(SEV.ENTERPRISE_APPLICATION_JDBC_URL, CHECK_NOT_BLANK);
        jdbc_user = getenv(SEV.ENTERPRISE_APPLICATION_JDBC_USER, CHECK_NOT_BLANK);
        jdbc_password = getenv(SEV.ENTERPRISE_APPLICATION_JDBC_PASSWORD, CHECK_NOT_BLANK);
        String sep = System.getProperties().getProperty("file.separator");
        String dp1 = System.getProperties().getProperty("user.dir");
        String dp2 = SEV.ENTERPRISE_APPLICATION_CODE.toLowerCase();
        String dp3 = "resources";
        String dp4 = "applications";
        String dp5 = "j2ee-apps";
        String dp6 = "config";
        String dp7 = StringUtils.containsIgnoreCase(System.getProperties().getProperty("os.name"), "windows") ? "windows" : "linux";
        String dp8 = "velocity";
        String dp9 = "templates";
        String ds1 = "-" + "web" + "_" + "war";
        String ds2 = "." + "properties";
        File file1 = new File(dp1);
        if (file1.isDirectory()) {
            if (StringUtils.isBlank(dir)) {
                dir = dp1 + sep + dp2 + sep + dp3;
                //Bitacora.trace(SEV.ENTERPRISE_APPLICATION_DIR + "=" + dir);
                isDirectory(dir);
            }
            File file2 = file1.getParentFile();
            if (file2 != null && file2.isDirectory()) {
                if (StringUtils.isBlank(ear)) {
                    ear = file2.getAbsolutePath() + sep + dp4 + sep + dp5 + sep + dp2;
                    //Bitacora.trace(SEV.ENTERPRISE_APPLICATION_EAR + "=" + ear);
                    isDirectory(ear);
                }
                if (StringUtils.isBlank(war)) {
                    war = ear + sep + dp2 + ds1;
                    //Bitacora.trace(SEV.ENTERPRISE_APPLICATION_WAR + "=" + war);
                    isDirectory(war);
                }
            }
            if (StringUtils.isBlank(cpf)) {
                cpf = dir + sep + dp6 + sep + dp7 + sep + dp2 + ds2;
                //Bitacora.trace(SEV.ENTERPRISE_APPLICATION_PROPERTIES + "=" + cpf);
                isFile(cpf);
            }
            if (StringUtils.isBlank(vlp)) {
                vlp = dir + sep + dp8 + sep + dp9;
                //Bitacora.trace(SEV.ENTERPRISE_VELOCITY_FILE_RESOURCE_LOADER_PATH + "=" + vlp);
                isPath(vlp);
            }
        }
        load();
    }

    private static String getProperty(String name) {
        String var = System.getProperties().getProperty(name);
        //Bitacora.trace(name + "=" + var);
        return StringUtils.trimToEmpty(var);
    }

    private static String getenv(String name, int type) {
        String var = System.getenv(name);
        //Bitacora.trace(name + "=" + (StringUtils.containsIgnoreCase(name, "password") ? EYE_CATCHER : var));
        check(var, type);
        return StringUtils.trimToEmpty(var);
    }

    private static boolean check(String path, int type) {
        boolean b = StringUtils.isNotBlank(path);
        if (b) {
            switch (type) {
                case CHECK_EXIST:
                    return exists(path);
                case CHECK_DIRECTORY:
                    return isDirectory(path);
                case CHECK_FILE:
                    return isFile(path);
                case CHECK_PATH:
                    return isPath(path);
                default:
                    return true;
            }
        }
        return b;
    }

    private static boolean exists(String pathname) {
        boolean b = Utils.exists(pathname);
        if (!b) {
            //Bitacora.trace(EYE_CATCHER + " " + pathname + " NO EXISTE " + EYE_CATCHER);
        }
        return b;
    }

    private static boolean isDirectory(String pathname) {
        boolean b = Utils.isDirectory(pathname);
        if (!b) {
            //Bitacora.trace(EYE_CATCHER + " " + pathname + " NO EXISTE O NO ES UN DIRECTORIO " + EYE_CATCHER);
        }
        return b;
    }

    private static boolean isFile(String pathname) {
        boolean b = Utils.isFile(pathname);
        if (!b) {
            //Bitacora.trace(EYE_CATCHER + " " + pathname + " NO EXISTE O NO ES UN ARCHIVO " + EYE_CATCHER);
        }
        return b;
    }

    private static boolean isPath(String path) {
        boolean b = StringUtils.isNotBlank(path);
        if (b) {
            String sep = System.getProperties().getProperty("path.separator");
            String[] pathnames = StringUtils.split(path, sep);
            for (String pathname : pathnames) {
                b &= isDirectory(pathname);
            }
        }
        return b;
    }

    private static void load() {
        properties = new Properties();
        try {
            if (Utils.isFile(cpf)) {
                FileInputStream inStream = new FileInputStream(cpf);
                properties.load(inStream);
                inStream.close();
                trace();
                pdq = true;
            } else {
                pdq = false;
            }
        } catch (Exception ex) {
            //Bitacora.logFatal(ex);
        }
    }

    private static void trace() {
        String[] names = new String[properties.stringPropertyNames().size()];
        properties.stringPropertyNames().toArray(names);
        Arrays.sort(names);
        String valor;
        String value;
        for (String name : names) {
            value = properties.getProperty(name);
            valor = getString(name);
            //Bitacora.trace(name + "=" + (StringUtils.containsIgnoreCase(name, "password") ? EYE_CATCHER : valor));
            if (StringUtils.isBlank(valor)) {
            } else if (value.startsWith(DIR)) {
                exists(valor);
            } else if (value.startsWith(EAR)) {
                exists(valor);
            } else if (value.startsWith(WAR)) {
                exists(valor);
            }
        }
    }

    public static boolean isLoaded() {
        return pdq;
    }

    public static String getDir() {
        return dir;
    }

    public static String getEar() {
        return ear;
    }

    public static String getWar() {
        return war;
    }

    public static String getConfigPropertiesFile() {
        return cpf;
    }

    public static String getVelocityFileResourceLoaderPath() {
        return vlp;
    }

    public static String getString(String key) {
        String value = properties.getProperty(key, "");
        if (StringUtils.isBlank(value)) {
            return "";
        } else if (value.startsWith(DIR)) {
            return value.replace(DIR, dir);
        } else if (value.startsWith(EAR)) {
            return value.replace(EAR, ear);
        } else if (value.startsWith(WAR)) {
            return value.replace(WAR, war);
        } else if (value.startsWith(WEB)) {
            return value.replace(WEB, "/" + J2EE.ENTERPRISE_APPLICATION_CODE.toLowerCase() + "-web");
        } else if (value.startsWith(JDBC_DRIVER)) {
            return value.replace(JDBC_DRIVER, jdbc_driver);
        } else if (value.startsWith(JDBC_URL)) {
            return value.replace(JDBC_URL, jdbc_url);
        } else if (value.startsWith(JDBC_USER)) {
            return value.replace(JDBC_USER, jdbc_user);
        } else if (value.startsWith(JDBC_PASSWORD)) {
            return value.replace(JDBC_PASSWORD, jdbc_password);
        }
        return value;
    }
    
}
