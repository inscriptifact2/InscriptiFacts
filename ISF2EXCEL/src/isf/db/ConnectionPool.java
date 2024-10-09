package isf.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;

public class ConnectionPool
{

    static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    static final String JDBC_DB_URL = "jdbc:oracle:thin:@localhost:4000:isfdb";
    static final String JDBC_USER = "isfdev2";
    static final String JDBC_PASS = "ISFDevusc2_07081966";
    private static SharedPoolDataSource poolDS;

    public ConnectionPool()
    {
    }

    public static Connection getConnection()
        throws SQLException
    {
        return poolDS.getConnection();
    }

    static 
    {
        poolDS = new SharedPoolDataSource();
        DriverAdapterCPDS cpds = new DriverAdapterCPDS();
        try
        {
            cpds.setDriver(JDBC_DRIVER);
            cpds.setUrl(JDBC_DB_URL);
            cpds.setUser(JDBC_USER);
            cpds.setPassword(JDBC_PASS);
            poolDS.setConnectionPoolDataSource(cpds);
            poolDS.setMaxTotal(50);
            poolDS.setMaxConnLifetimeMillis(100L);
        }
        catch(ClassNotFoundException ex)
        {
            Logger.getLogger("isf.db.ConnectionPool").log(Level.SEVERE, null, ex);
        }
    }
}
