
package inputOutput;

/**
 Create class ConnectionData
 in package inputOutput;
 
 */
public class ConnectionData {
    //member variables
    private String type;
    private String url;
    private String ipaddress;
    private String port;
    private String database;
    private String login;
    private String password;
    
    /*Method toString() should concatenate member variables url, 
ipaddress, port, and database*/
    
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getIpaddress()
    {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress)
    {
        this.ipaddress = ipaddress;
    }
    public String getport()
    {
        return port;
    }
    public void setPort(String port)
    {
        this.port = port;
    }
    public String getDatabase()
    {
        return database;
    }
    public void setDatabase(String database)
    {
        this.database = database;
    }
    public String getLogin()
    {
        return login;
    }
    public void setLogin(String login)
    {
        this.login = login;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    @Override
    public String toString()
    {
        //"jdbc:postgresql://localhost:5432/dvdrental"
        return url + "://" + ipaddress + ":" + port + "/" + database;
      
    }

    
    
    
    
    
    
}    

