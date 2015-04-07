import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.DLPayload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.options.CreateUpdateContainerOptions;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;

public class GestioneFile 
{	
	public GestioneFile() {}
	
	//NON FUNZIONA
	//IL PROBLEMA E' CAPIRE COME COSTRUIRE MEGLIO IL LINK DELLA RICHIESTA GET:
	//DI DEFAULT FA' SEMPRE HTTP://INDIRIZZO IP:8080/. NOI DOVREMMO FARLO DIVENTARE COME SEGUE:
	//HTTP://INDIRIZZO IP:8080/v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/
	//IN QUESTO MODO DOVREBBE DARCI IL LISTING DI TUTTI I CONTAINER 
	//AUTH Giovanni: AUTH_34cbe4ae849b4ed2b47c40d391d69039
	//AUTH Giuseppe: AUTH_0613b45860a545608c638f40b6bf7938
	//LISTING DEI CONTAINERS
	public void ListaContainer(OSClient os)
	{
		List<? extends SwiftContainer> containers = os.objectStorage()
                .containers()
                .list();
		System.out.println("\nLista dei containers: \n" + containers.toString());
	}
	
	//CREAZIONE DI UN CONTAINER
	public void CreaContainer(OSClient os, String nomeContainer)
	{
		ConfigurationProperties props = new ConfigurationProperties();
		props.getProperty("swiftAccount");
		os.objectStorage().containers().create("v1/" + props.getProperty("swiftAccount") + "/" + nomeContainer,
													CreateUpdateContainerOptions.create()
													.accessAnybodyRead());
		System.out.println("Container Creato");
    }
	
	//CANCELLAZIONE DI UN CONTAINER
	public void CancellaContainer(OSClient os, String nomeContainer)
	{
		ConfigurationProperties props = new ConfigurationProperties();
		props.getProperty("swiftAccount");
		String nomecontainer = "v1/" + props.getProperty("swiftAccount") + "/" + nomeContainer;
		os.objectStorage().containers().delete(nomecontainer);
		System.out.println("Cancellazione avvenuta");
    }
	
	//CREAZIONE DELL'OGGETTO
	public void CreaOggetto(OSClient os, String container, String objectName, String localPath)
	{
		File f = new File(localPath);
		ConfigurationProperties props = new ConfigurationProperties();
		props.getProperty("swiftAccount");
		String etag = os.objectStorage().objects().put(
            "v1/" + props.getProperty("swiftAccount") + "/" + container, objectName, 
                Payloads.create(f), 
                ObjectPutOptions.create());
		System.out.println("Oggetto creato con etichetta = " + etag);
	}
	
	//CANCELLAZIONE DELL'OGGETTO
	public void CancellaOggetto(OSClient os, String container, String objectName)
	{
		ConfigurationProperties props = new ConfigurationProperties();
		props.getProperty("swiftAccount");
		System.out.println(os.objectStorage().objects().delete("v1/" + props.getProperty("swiftAccount") + "/" + container, objectName));
	}
	
	//DOWNLOAD DEL FILE DAL CONTAINER
	public void downloadFile(OSClient os, String container, String objectName, String destPath) throws IOException
	{
		ConfigurationProperties props = new ConfigurationProperties();
		props.getProperty("swiftAccount");
		DLPayload scaricato = os.objectStorage().objects().download("v1/" + props.getProperty("swiftAccount") + "/" + container, objectName);
		File file = new File(destPath);
		scaricato.writeToFile(file);
		FileReader leggi =new FileReader(file);
		BufferedReader b = new BufferedReader(leggi);
		String s;
		while(true) 
		{
			s = b.readLine();
		    if(s==null)
		    	break;
		    System.out.println(s);
		}
		leggi.close();
	}
}
