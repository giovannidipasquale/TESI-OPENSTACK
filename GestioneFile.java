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
import org.openstack4j.model.storage.object.options.ObjectLocation;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;

public class GestioneFile 
{	
	public GestioneFile() 
	{
		
	}
	
	//DA CONTROLLARE
	//LISTING DEI CONTAINERS
	public void ListaContainer(OSClient os)
	{
		List<? extends SwiftContainer> containers = os.objectStorage()
                .containers()
                .list();
		System.out.println("\nLista dei containers: \n" + containers.toString());
	}
	
	//CREAZIONE DI UN CONTAINER
	public void CreaContainer(OSClient os)
	{
		os.objectStorage().containers().create("v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/Pippo",
													CreateUpdateContainerOptions.create()
													.accessAnybodyRead());
    }
	
	//CANCELLAZIONE DI UN CONTAINER
	public void CancellaContainer(OSClient os)
	{
		String nomecontainer = "v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/nomecontainer";
		os.objectStorage().containers().delete(nomecontainer);
		System.out.println("Cancellazione avvenuta");
    }
	
	//CREAZIONE DELL'OGGETTO
	public void CreaOggetto(OSClient os)
	{
		String path = "/home/giovannidipasquale/git/Autenticazione Openstack/CiaoMondo.java";
		File f = new File(path);
		
		String etag = os.objectStorage().objects().put("v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/cnt569", "ProvaPUT", 
                Payloads.create(f), 
                ObjectPutOptions.create());
		
		System.out.println(etag);
	}
	
	//CANCELLAZIONE DELL'OGGETTO
	public void CancellaOggetto(OSClient os)
	{
		System.out.println(os.objectStorage().objects().delete("v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/cnt5699", "ProvaPUT"));
	}
	
	//CAPIRE MEGLIO COME FUNZIONA
	//COPIA DELL'OGGETTO DA UN CONTAINER ALL'ALTRO
	public void CopiaOggetto(OSClient os)
	{
		String etag = os.objectStorage().objects().copy(ObjectLocation.create("v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/cnt569", "ProvaPUT"), 
														ObjectLocation.create("v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/ppppp", "SCRIPTCIRROS"));
		System.out.println("\nOggetto Copiato\n "+etag);
	}
	
	//DOWNLOAD DEL FILE DAL CONTAINER
	public void downloadFile(OSClient os) throws IOException
	{
		DLPayload scaricato = os.objectStorage().objects().download("v1/AUTH_34cbe4ae849b4ed2b47c40d391d69039/ppppp", "SCRIPTCIRROS");
		System.out.println("\nFile Scaricato: \n"+scaricato.toString());
		File file = new File("prova.txt");
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
