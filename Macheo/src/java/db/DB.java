/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import constants.EAP;
import java.sql.Connection;
import util.EA;

/**
 *
 * @author marcos
 */
public class DB {

    public static Connection con_pg;
    
    public static Connection connect() throws Exception {
        //Bitacora.trace(DB.class, "connect");
        Connection connection = null;
        if (EA.isLoaded()) {
//          String dataSourceName = EA.getString(EAP.JDBC_DATASOURCE);
//          connection = connect(dataSourceName);
            if (connection == null) {
                String driver = EA.getString(EAP.JDBC_DRIVER);
                String url = EA.getString(EAP.JDBC_URL);
                String user = EA.getString(EAP.JDBC_USER);
                String password = EA.getString(EAP.JDBC_PASSWORD);
                
                System.out.println("driver: "+ driver);
                System.out.println("url: "+ url);
                System.out.println("user: "+ user);
                System.out.println("password: "+ password);
                
                connection = connect(driver, url, user, password);
            }
        }
        return connection;
    }

    public static Connection connect(String pHost, String pUser, String pPassword, String pDataBase) throws Exception {
        try {
            String databaseURL = "jdbc:postgresql://" + pHost + "/" + pDataBase;
            Class.forName("org.postgresql.Driver");
            con_pg = java.sql.DriverManager.getConnection(databaseURL, pUser, pPassword);
            //System.out.println("Conexion con Postgresql Establecida..");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return con_pg;
    }
}
