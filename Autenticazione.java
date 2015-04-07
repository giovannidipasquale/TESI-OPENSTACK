public class Autenticazione 
{
	private String username;
	private String password;
	private String tenant;
	
	public Autenticazione() {}
	
	public Autenticazione(String username, String password, String tenant) 
	{
		this.username = username;
		this.password = password;
		this.tenant = tenant;
	}
	
	public String getUsername() 
	{
		return username;
	}
	
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getTenant() 
	{
		return tenant;
	}
	
	public void setTenant(String tenant) 
	{
		this.tenant = tenant;
	}
	
	public String authURL() 
	{ 
	    ConfigurationProperties props = new ConfigurationProperties();
	    String ipServer = props.getProperty("ipServer");
	    return String.format("http://%s:5000%s", ipServer, "/v2.0");
	}
}
