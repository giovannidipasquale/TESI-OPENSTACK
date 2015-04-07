import org.openstack4j.api.*;
import org.openstack4j.openstack.*;

//TEMPO DI CREAZIONE DI UNA MACCHINA VIRTUALE
public class PrimoCasoTest 
{		
	public static void main(String[] args)
	{
		//FILE CONFIGURAZIONE
		ConfigurationProperties props = new ConfigurationProperties();
		
		OSFactory.enableLegacyEndpointHandling(true);
		
		//AUTENTICAZIONE
		String Username = props.getProperty("Username");
		String Password = props.getProperty("Password");
		String tenantName = props.getProperty("tenantName");
		
		Autenticazione login = new Autenticazione(Username, Password, tenantName);
		OSClient os = OSFactory.builder()
				.endpoint(login.authURL())
	            .credentials(login.getUsername(), login.getPassword())
	            .tenantName(login.getTenant())
	            .authenticate();
		
		double startTime = System.currentTimeMillis();
		
		//CREAZIONE SERVER
		GestioneServer gs = new GestioneServer();
		gs.CreazioneServer(os, props.getProperty("nomeServer"), props.getProperty("FlavorId"), props.getProperty("imageId"));
		
		//TEMPO ESECUZIONE
		double endtime = System.currentTimeMillis();
		double tempo = (endtime - startTime)/1000;
		System.out.println("\nTempo Programma: \n" + String.valueOf(tempo));		
	}
}
