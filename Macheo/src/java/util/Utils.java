/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.egt.commons.util.HexUtils;
import com.egt.commons.util.ThrowableUtils;
import com.egt.core.aplicacion.Bitacora;
import com.egt.core.constants.EAP;
import java.io.File;
import java.security.MessageDigest;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author marcos
 */
public class Utils {
    private static final String PIPE_STRING = "|";

    private static final String PIPE_REGEX = "[" + PIPE_STRING + "]";

    private static final String DOUBLE_QUOTE = "\"";

    private static final String SINGLE_QUOTE = "'";

    private static final Long X18L = 1000000000000000000L;

    /**
     * Digest password using the algorithm especificied and convert the result to a corresponding hex string.
     * If exception, the plain credentials string is returned
     * @param credentials Password or other credentials to use in authenticating this username
     * @param algorithm Algorithm used to do the digest
     * @param encoding Character encoding of the string to digest
     */
    public static String Digest(String credentials, String algorithm, String encoding) {
        try {
            // Obtain a new message digest with "digest" encryption
            MessageDigest md = (MessageDigest) MessageDigest.getInstance(algorithm).clone();
            // encode the credentials
            // Should use the digestEncoding, but that's not a static field
            if (encoding == null) {
                md.update(credentials.getBytes());
            } else {
                md.update(credentials.getBytes(encoding));
            }
            // Digest the credentials and return as hexadecimal
            return HexUtils.convert(md.digest());
        } catch (Exception ex) {
            ex.printStackTrace();
            return credentials;
        }
    }

    private static String getQuote() {
        return System.getProperties().getProperty("path.separator").equals(";") ? DOUBLE_QUOTE : SINGLE_QUOTE;
    }

    public static String getSpooledFilesDir() {
        return getSpooledFilesDir(null);
    }

    public static String getSpooledFilesDir(Long user) {
        String root = EA.getString(EAP.SPOOLED_FILES_DIR);
        String fsep = System.getProperties().getProperty("file.separator");
        String dirs = userdir(user, root, fsep);
        return mkdirs(dirs) ? dirs + fsep : root;
    }

    public static String getSpooledFilesWebDir() {
        return getSpooledFilesWebDir(null);
    }

    public static String getSpooledFilesWebDir(Long user) {
        String root = EA.getString(EAP.SPOOLED_FILES_WEB_DIR);
        String fsep = "/";
        String dirs = userdir(user, root, fsep);
        return dirs + fsep;
    }

    static String userdir(Long user, String root, String fsep) {
        if (user == null) {
            return root;
        }
        String pattern = "00000000000000000000";
        NumberFormat formatter = new DecimalFormat(pattern + ";" + pattern);
        String string = formatter.format(user);
        String dirs = "";
        for (int i = 0; i < string.length(); i = i + 4) {
            dirs += string.substring(i, i + 4) + fsep;
        }
        return root + StringUtils.chomp(dirs, fsep);
    }

    static int thousand(Long number) {
        return 1000 + checksum(number, 10);
    }

    static int checksum(Long number) {
        return checksum(number, 0);
    }

    static int checksum(Long number, int limit) {
        long m = 0;
        long n = number == null ? 0 : number;
        while (n > 0) {
            m += n % 10L;
            n = n / 10;
        }
        int sum = (int) m;
        return limit > 0 && sum > limit ? checksum(m, limit) : sum;
    }

    public static boolean exists(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.exists();
        }
        return b;
    }

    public static boolean isDirectory(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isDirectory();
        }
        return b;
    }

    public static boolean isFile(String pathname) {
        boolean b = StringUtils.isNotBlank(pathname);
        if (b) {
            File file = new File(pathname);
            b = file.isFile();
        }
        return b;
    }

    public static boolean isPath(String path) {
        boolean b = StringUtils.isNotBlank(path);
        if (b) {
            String sep = System.getProperties().getProperty("path.separator");
            String[] pathnames = StringUtils.split(path, sep);
            for (String pathname : pathnames) {
                b &= exists(pathname);
            }
        }
        return b;
    }

    public static boolean mkdir(String dirs) {
        File file = new File(dirs);
        return file.isDirectory() || file.mkdir();
    }

    public static boolean mkdirs(String dirs) {
        File file = new File(dirs);
        return file.isDirectory() || file.mkdirs();
    }

    public static String getMessageFormat(String message, Object[] args) {
        String mensaje = message;
        if (StringUtils.isNotBlank(message)) {
            int n = args == null ? 0 : args.length;
            for (int i = 0; i < n; i++) {
                //args[i] = args[i] == null ? STP.STRING_VALOR_NULO : STP.getString(args[i]);
            }
            mensaje = MessageFormat.format(message, args);
        }
        return mensaje;
    }

    public static String getTraceMessageFormat(String message, Object[] args) {
        return getMessageFormat(message, args);
    }

    public static String[] getStringArray(String variable) {
        String str = StringUtils.trimToEmpty(variable);
        String quote = getQuote();
        if (str.startsWith(quote) && str.endsWith(quote)) {
            str = str.substring(1, str.length() - 1);
        }
        if (StringUtils.isBlank(str)) {
            return null;
        }
        str = StringUtils.trimToEmpty(str);
        String[] args = new String[0];
        boolean split = true;
        for (int i = 0, j = str.indexOf(quote); i < str.length();) {
            if (j < 0) {
                j = str.length();
            }
            args = split ? add(args, StringUtils.split(str.substring(i, j))) : add(args, str.substring(i, j));
            if (j < str.length()) {
                i = j + 1;
                j = str.indexOf(quote, i);
            } else {
                i = str.length();
            }
            split = !split;
        }
        printObjectArray(args);
        return args;
    }

    private static String[] add(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    private static String[] add(String[] a, String b) {
        String[] c = new String[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[a.length] = b;
        return c;
    }

    public static String[] getStringArray(String[] args) {
        return getStringArray(args, 0);
    }

    public static String[] getStringArray(String[] args, int fromIndex) {
        if (args != null && args.length > fromIndex) {
            String[] copy = new String[args.length - fromIndex];
            System.arraycopy(args, fromIndex, copy, 0, copy.length);
            return copy;
        } else {
            return null;
        }
    }

    public static Object[] getObjectArray(String str) {
        return getObjectArray(str, PIPE_REGEX);
    }

    public static Object[] getObjectArray(String str, String regex) {
        return StringUtils.isBlank(str) ? null : getObjectArray(str.split(regex));
    }

    public static Object[] getObjectArray(String[] args) {
        return getObjectArray(args, 0);
    }

    public static Object[] getObjectArray(String[] args, int fromIndex) {
        if (args != null && args.length > fromIndex) {
            Object[] copy = new Object[args.length - fromIndex];
            for (int i = fromIndex; i < args.length; i++) {
                //copy[i - fromIndex] = STP.getObjeto(args[i]);
            }
            return copy;
        } else {
            return null;
        }
    }

    public static Object[] getJavaParameterArray(String str) {
        return getJavaParameterArray(str, PIPE_REGEX);
    }

    public static Object[] getJavaParameterArray(String str, String regex) {
        return StringUtils.isBlank(str) ? null : getJavaParameterArray(str.split(regex));
    }

    public static Object[] getJavaParameterArray(String[] args) {
        return getJavaParameterArray(args, 0);
    }

    public static Object[] getJavaParameterArray(String[] args, int fromIndex) {
        Object[] object = getObjectArray(args, fromIndex);
        if (object != null && object.length > 0) {
            Object[] parameter = new Object[object.length];
            for (int i = 0; i < args.length; i++) {
                if (object[i] instanceof Integer) {
                    parameter[i] = Long.valueOf(object[i].toString());
                } else {
                    parameter[i] = object[i];
                }
            }
            return parameter;
        }
        return object;
    }

    public static String getPipedParameterString(Object[] args) {
        return getDelimitedParameterString(args, PIPE_STRING);
    }

    public static String getDelimitedParameterString(Object[] args, String delimiter) {
        String str = StringUtils.EMPTY;
        String dlm = StringUtils.isBlank(delimiter) ? PIPE_STRING : StringUtils.trimToEmpty(delimiter);
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                //str += dlm + StringUtils.trimToEmpty(STP.getString(args[i]));
            }
            str = str.substring(dlm.length());
        }
        return StringUtils.trimToEmpty(str);
    }

    public static String getQuotedParameterString(Object[] args) {
        String str = StringUtils.EMPTY;
        String dlm = " ";
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                //str += dlm + getQuotedParameterString(STP.getString(args[i]));
            }
            str = str.substring(dlm.length());
        }
        return StringUtils.trimToEmpty(str);
    }

    public static String getQuotedParameterString(String str) {
        String quote = getQuote();
        return quote + StringUtils.trimToEmpty(str) + quote;
    }

    public static String trimPrefix(String message, String prefix) {
        String mensaje = StringUtils.trimToEmpty(message);
        int i = mensaje.indexOf(prefix);
        if (i >= 0) {
            mensaje = mensaje.substring(i + prefix.length());
        }
        return mensaje;
    }

    public static String trimSuffix(String message, String suffix) {
        String mensaje = StringUtils.trimToEmpty(message);
        int i = mensaje.indexOf(suffix);
        if (i > 0) {
            mensaje = mensaje.substring(0, i);
        }
        return mensaje;
    }

    public static void printBOT(String[] args) {
        println("Comenzo @ " + System.currentTimeMillis());
        for (int i = 0; i < args.length; i++) {
            println("args[" + i + "] = " + args[i]);
        }
    }

    public static void printEOT() {
        println("Termino @ " + System.currentTimeMillis());
    }

    public static boolean checkEnterpriseApplicationJDBC() {
        boolean ok = true;
        ok &= checkEnterpriseApplicationProperty(EAP.JDBC_DRIVER);
        ok &= checkEnterpriseApplicationProperty(EAP.JDBC_URL);
        ok &= checkEnterpriseApplicationProperty(EAP.JDBC_USER);
        ok &= checkEnterpriseApplicationProperty(EAP.JDBC_PASSWORD);
        return ok;
    }

    public static boolean checkEnterpriseApplicationProperty(String name) {
        if (StringUtils.isBlank(EA.getString(name))) {
            println("*** propiedad no definida: " + name);
            return false;
        } else {
            printEnterpriseApplicationProperty(name);
            return true;
        }
    }

    public static void printEnterpriseApplicationProperties() {
        printEnterpriseApplicationProperty(EAP.JDBC_DRIVER);
        printEnterpriseApplicationProperty(EAP.JDBC_URL);
        printEnterpriseApplicationProperty(EAP.JDBC_USER);
        printEnterpriseApplicationProperty(EAP.JDBC_PASSWORD);
    }

    public static void printEnterpriseApplicationProperty(String name) {
        if (StringUtils.isBlank(name)) {
            return;
        }
//        String value = name.equals(EAP.JDBC_PASSWORD) ? STP.getString(20, '*') : EA.getString(name);
//        println("*** " + name + " = " + value);
    }

//  public static boolean checkEnvironmentJDBC() {
//      boolean ok = true;
//      ok &= checkEnvironmentVariable(SEV.JDBC_DRIVER);
//      ok &= checkEnvironmentVariable(SEV.JDBC_URL);
//      ok &= checkEnvironmentVariable(SEV.JDBC_USER);
//      ok &= checkEnvironmentVariable(SEV.JDBC_PASSWORD);
//      return ok;
//  }
//
    public static boolean checkEnvironmentVariable(String name) {
        if (StringUtils.isBlank(System.getenv(name))) {
            println("*** variable de entorno no definida: " + name);
            return false;
        } else {
            printEnvironmentVariable(name);
            return true;
        }
    }

    public static String[] checkEnvironmentVariableStringArray(String name) {
        if (checkEnvironmentVariable(name)) {
            String[] args = getStringArray(System.getenv(name));
            if (args == null || args.length < 1) {
                println("*** variable de entorno invalida: " + name);
            } else {
                return args;
            }
        }
        return null;
    }

//  public static void printEnvironment() {
//      printEnvironmentVariable(SEV.JDBC_DRIVER);
//      printEnvironmentVariable(SEV.JDBC_URL);
//      printEnvironmentVariable(SEV.JDBC_USER);
//      printEnvironmentVariable(SEV.JDBC_PASSWORD);
//      printEnvironmentVariable(SEV.JAVA_PARAMETER_LIST);
//  }
//
    public static void printEnvironmentVariable(String name) {
        if (StringUtils.isBlank(name)) {
            return;
        }
//      String var = name.equals(SEV.JDBC_PASSWORD) ? STP.getString(20, '*') : System.getenv(name);
        String var = System.getenv(name);
        println("*** " + name + " = " + var);
    }

    public static void printObjectArray(Object[] args) {
        if (args == null) {
            println("args = NULL");
        } else if (args.length == 0) {
            println("args.length = 0");
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    println("args [" + i + "] = NULL");
                } else {
                    println("args [" + i + "] = (" + args[i].getClass().getSimpleName() + ")" + args[i]);
                }
            }
        }
    }

    public static void traceObjectArray(Object[] args) {
        if (args == null) {
            Bitacora.trace("args = NULL");
        } else if (args.length == 0) {
            Bitacora.trace("args.length = 0");
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    Bitacora.trace("args [" + i + "] = NULL");
                } else {
                    Bitacora.trace("args [" + i + "] = (" + args[i].getClass().getSimpleName() + ")" + args[i]);
                }
            }
        }
    }

    public static void println(String x) {
        Bitacora.trace(x);
    }

    public static String replaceWhereClause(String q1, String q2) {
        if (StringUtils.isBlank(q1)) {
            return null;
        }
        if (StringUtils.isBlank(q2)) {
            return q1;
        }
        int o1 = indexOfWord(q1, "ORDER");
        int u1 = indexOfWord(q1, "UNION");
        int g1 = indexOfWord(q1, "GROUP");
        int w1 = indexOfWord(q1, "WHERE");
        if (o1 < 0) {
            o1 = q1.length();
        }
        if (u1 < 0) {
            u1 = o1;
        }
        if (g1 < 0) {
            g1 = u1;
        }
        if (w1 < 0) {
            w1 = g1;
        }
        int o2 = indexOfWord(q2, "ORDER");
        int u2 = indexOfWord(q2, "UNION");
        int g2 = indexOfWord(q2, "GROUP");
        int w2 = indexOfWord(q2, "WHERE");
        if (o2 < 0) {
            o2 = q2.length();
        }
        if (u2 < 0) {
            u2 = o2;
        }
        if (g2 < 0) {
            g2 = u2;
        }
        if (w2 < 0) {
            w2 = g2;
        }
        return q1.substring(0, w1).trim() + '\n' + q2.substring(w2, g2).trim() + '\n' + q1.substring(g1).trim();
    }

    private static int indexOfWord(String str, String searchStr) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(searchStr)) {
            return -1;
        }
        String s = ' ' + StringUtils.trimToEmpty(str).toLowerCase() + ' ';
        String w = StringUtils.trimToEmpty(searchStr).toLowerCase();
        char[] L = {' ', ')', '\f', '\n', '\r', '\t'};
        char[] R = {' ', '(', '\f', '\n', '\r', '\t'};
        int i = -1;
        for (int l = 0; i < 0 && l < L.length; l++) {
            for (int r = 0; i < 0 && r < R.length; r++) {
                i = s.indexOf(L[l] + w + R[r]);
            }
        }
        return i > 0 ? i - 1 : i;
    }

    private static final char[] R2D2 = {'\'', '<', '>', '*', '%'};

    private static final String R2D2_PREFIX = "(_";

    private static final String R2D2_SUFFIX = "_)";

    public static String encodeSelect(String select) {
        if (StringUtils.isBlank(select)) {
            return null;
        }
        String str = select.trim();
        for (int i = 0; i < R2D2.length; i++) {
            str = str.replace(String.valueOf(R2D2[i]), R2D2_PREFIX + (int) R2D2[i] + R2D2_SUFFIX);
        }
        return str;
    }

    public static String decodeSelect(String select) {
        if (StringUtils.isBlank(select)) {
            return null;
        }
        String str = select.trim();
        for (int i = 0; i < R2D2.length; i++) {
            str = str.replace(R2D2_PREFIX + (int) R2D2[i] + R2D2_SUFFIX, String.valueOf(R2D2[i]));
        }
        return str;
    }

    public static void createEjbSubcontext() {
        Context c;
        Context s;
        try {
            c = new InitialContext();
            Bitacora.trace(c.getClass(), "createSubcontext", "comp:java/env/ejb");
            s = c.createSubcontext("comp:java");
            traceContext(c, "comp:java");
            s = s.createSubcontext("env");
            traceContext(c, "comp:java/env");
            s = s.createSubcontext("ejb");
            traceContext(c, "comp:java/env/ejb");
        } catch (NamingException ex) {
            Bitacora.logFatal(ThrowableUtils.getString(ex));
        }
    }

    /*public static void bind2InitialContext(String name, Object obj) {
        Context c;
        try {
            c = new InitialContext();
            Bitacora.trace(c.getClass(), "bind", name, obj);
            c.bind(name, obj);
        } catch (NamingException ex) {
            Bitacora.logFatal(ThrowableUtils.getString(ex));
        }
    }*/

    // <editor-fold defaultstate="collapsed" desc="trace">
    public void trace(String objeto, String metodo, String contexto) {
        System.out.println(objeto + "." + metodo + "(" + contexto + ")");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        System.out.println(objeto + "." + metodo + "(" + facesContext + ")");
        if (facesContext == null) {
            return;
        }
        traceContext();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        System.out.println("request ..................... " + request);
        System.out.println("request.getAuthType ......... " + request.getAuthType());
        System.out.println("request.getUserPrincipal .... " + request.getUserPrincipal());
        Principal principal = facesContext.getExternalContext().getUserPrincipal();
        System.out.println("principal ................... " + principal);
        if (principal != null) {
            System.out.println("principal.getName ........... " + principal.getName());
            System.out.println("isSuperUsuario .............. " + request.isUserInRole("SuperUsuario"));
            System.out.println("isUsuarioEstandar ........... " + request.isUserInRole("UsuarioEstandar"));
            System.out.println("isUsuarioBasico.. ........... " + request.isUserInRole("UsuarioBasico"));
        }
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        HttpSession session = request.getSession(false);
        System.out.println("session ..................... " + facesContext.getExternalContext().getSession(false));
        System.out.println("session.getId ............... " + session.getId());
        String key = null;
        Object object = null;
        Set sessionKeys = facesContext.getExternalContext().getSessionMap().keySet();
        if (sessionKeys.isEmpty()) {
        } else {
            Iterator iterator = sessionKeys.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (object instanceof String) {
                    key = (String) object;
                    object = facesContext.getExternalContext().getSessionMap().get(key);
                    if (object != null) {
                        System.out.println(key + " = (" + object.getClass().getName() + ") " + object);
                    }
                }
            }
        }
        System.out.println("request.getContextPath ...... " + request.getContextPath());
        System.out.println("request.getServletPath ...... " + request.getServletPath());
        System.out.println("request.getPathInfo ......... " + request.getPathInfo());
        System.out.println("request.getRequestURI ....... " + request.getRequestURI());
        System.out.println("request.getContextPathURL ... " + request.getRequestURL().toString());
        String clave = null;
        System.out.println("*** parametros ***");
        Iterator iterator = request.getParameterMap().keySet().iterator();
        while (iterator.hasNext()) {
            clave = (String) iterator.next();
            System.out.println(clave + " = " + request.getParameter(clave));
        }
        String cookieName = null;
        System.out.println("**** cookies  ****");
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookieName = cookies[i].getName();
                System.out.println(cookieName + " = " + cookies[i].getValue());
            }
        }
    }

    public static void traceContext() {
        Context c;
        try {
            c = new InitialContext();
            Bitacora.trace(c.getClass(), "list", c.getNameInNamespace());
//          traceContext(c, "java:comp");
        } catch (NamingException ex) {
            Bitacora.logFatal(ThrowableUtils.getString(ex));
        }
    }

    public static void traceContext(String name) {
        Context c;
        try {
            c = new InitialContext();
            Bitacora.trace(c.getClass(), "list", c.getNameInNamespace());
            traceContext(c, name);
        } catch (NamingException ex) {
            Bitacora.logFatal(ThrowableUtils.getString(ex));
        }
    }

    public static void traceContext(Context c, String name) {
        NameClassPair ncp;
        NamingEnumeration<NameClassPair> ncpenum;
        try {
            ncpenum = c.list(name);
            while (ncpenum.hasMore()) {
                ncp = ncpenum.next();
                if (ncp.getClassName().endsWith("javaURLContext")) {
                    traceContext(c, ncp.getName());
                } else {
                    Bitacora.logTrace(ncp.getName() + " = " + ncp.getClassName());
                }
            }
        } catch (NamingException ex) {
            Bitacora.logFatal(ThrowableUtils.getString(ex));
        }
    }
    // </editor-fold>

}
