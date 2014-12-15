/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import db.DB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcos
 */
public class AgenteSql {
    
    private static final ThreadLocal _agenteSql = new ThreadLocal();

    private static final ThreadLocal _bitacora = new ThreadLocal();

    private static final ThreadLocal _connection = new ThreadLocal();

    private static final ThreadLocal _controlador = new ThreadLocal();

    private static final ThreadLocal _interpreteSql = new ThreadLocal();
    
    
    public static void ejecutarProcedimientoAlmacenado() throws SQLException {

        Connection connPG = null;

        try {
            

                // creamos la conexion
                //connPG = DB.connect("192.168.23.20", "postgres", "mecdgii", "MECDB100");
                connPG = getConnection();
                
                // establecemos que no sea autocommit,
                // asi controlamos la transaccion de manera manual
                connPG.setAutoCommit(false);
                /* instanciamos el objeto callable statement
                 * que usaremos para invocar el SP
                 * La cantidad de "?" determina la cantidad
                 * parametros que recibe el procedimiento
                 */
                CallableStatement prcProcedimientoAlmacenado = connPG.prepareCall("{ call persona_formacion_confirmar(?) }");
                // cargar parametros al SP
                prcProcedimientoAlmacenado.setLong(1, 134938363501098012L);                
                // ejecutar el SP
                prcProcedimientoAlmacenado.execute();
                // confirmar si se ejecuto sin errores
                connPG.commit();
            
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            connPG.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            connPG.close();
        }
    }
    
    public static void ejecutarProcedimientoAlmacenado(String funcion, Object[] args) throws SQLException {

        Connection connPG = null;

        try {
            // creamos la conexion
            connPG = getConnection();
            // establecemos que no sea autocommit, asi controlamos la transaccion de manera manual
            connPG.setAutoCommit(false);
            CallableStatement cStat = connPG.prepareCall("{ call " + funcion + "(?) }");
            // cargar parametros al SP
            cStat.setLong(1, 134938363501098012L);                
            // ejecutar el SP
            cStat.execute();
            // confirmar si se ejecuto sin errores
            connPG.commit();
            
        } catch (Exception e) {
            // deshacer la ejecucion en caso de error
            connPG.rollback();
            // informar por consola
            e.printStackTrace();
        } finally {
            // cerrar la conexion
            connPG.close();
        }
    }
    
    public static Connection getConnection() throws Exception {        
        setConnection(DB.connect());        
        return (Connection) _connection.get();
    }
    
    public static void setConnection(Connection connection) {
        //Bitacora.trace(TLC.class, "setConnection", connection);
        _connection.set(connection);
    }
}
