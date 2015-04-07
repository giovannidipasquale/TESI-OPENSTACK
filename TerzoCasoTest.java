import java.io.IOException;
import org.openstack4j.api.OSClient;
import org.openstack4j.openstack.OSFactory;

public class TerzoCasoTest 
{
	public static void main(String[] args) throws IOException
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
		
		//CREAZIONE CONTAINER
		GestioneFile gf = new GestioneFile();
		gf.CreaContainer(os, props.getProperty("container"));
		
		double endtime = System.currentTimeMillis();
		double tempo = (endtime - startTime)/1000;
		System.out.println("\nTEMPO CREAZIONE CONTAINER: \n" + String.valueOf(tempo));
		
		//CREAZIONE DELL'OGGETTO
		gf.CreaOggetto(os, props.getProperty("container"), props.getProperty("objectName"), props.getProperty("localPath"));
		
		double endtime1 = System.currentTimeMillis();
		double tempo1 = (endtime1 - startTime)/1000;
		System.out.println("\nTEMPO CREAZIONE OGGETTO: \n" + String.valueOf(tempo1)+ "\n") ;
		
		//DOWNLOAD OGGETTO
		gf.downloadFile(os, props.getProperty("container"), props.getProperty("objectName"), props.getProperty("destPath"));
		
		double endtime2 = System.currentTimeMillis();
		double tempo2 = (endtime2 - startTime)/1000;
		System.out.println("\nTEMPO DOWNLOAD FILE: \n" + String.valueOf(tempo2));
		
		//CANCELLAZIONE DELL'OGGETTO
		gf.CancellaOggetto(os, props.getProperty("container"), props.getProperty("objectName"));

		double endtime3 = System.currentTimeMillis();
		double tempo3 = (endtime3 - startTime)/1000;
		System.out.println("\nTEMPO CANCELLAZIONE OGGETTO: \n" + String.valueOf(tempo3));
		
		//CANCELLAZIONE DI UN CONTAINER
		gf.CancellaContainer(os, props.getProperty("nomeContainer"));
		
		double endtime4 = System.currentTimeMillis();
		double tempo4 = (endtime4 - startTime)/1000;
		System.out.println("\nTEMPO CANCELLAZIONE CONTAINER: \n" + String.valueOf(tempo4));
		
		//TEMPO ESECUZIONE
		double endtime5 = System.currentTimeMillis();
		double tempo5 = (endtime5 - startTime)/1000;
		System.out.println("\nTEMPO PROGRAMMA: \n" + String.valueOf(tempo5));
	}
}
