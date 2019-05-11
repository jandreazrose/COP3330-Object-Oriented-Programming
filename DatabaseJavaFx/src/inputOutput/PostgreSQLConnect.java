
package inputOutput;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *1.
Include member
 variable Connection connect
2.
Write a
 constructor
 to receive a parameter of class 
ConnectionData
 */
public class PostgreSQLConnect {
    
    Connection connect;
    
    public PostgreSQLConnect(ConnectionData data)
    {
        
/*a.
Call static method
 Class.forName() passing 
argument getType() method call on the instance
 of
ConnectionData
b.
Set variable
connect
 equal to static
 method call
DriverManager.getConnection() passing the 
following arguments   */
        
        
        try
        {
            System.out.println("data is " + data.toString());
            Class.forName(data.getType());
            connect = DriverManager.getConnection(data.toString(), data.getLogin(), data.getPassword());
        }
        
/*a.
catch exception type Exception
b.
output to the user
 that
 the database connection was 
not successful
        *
        */
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        System.out.println("Opened database successfully!");
    }
    
    public Connection getConnection()
    {
        return connect;
    }
}
