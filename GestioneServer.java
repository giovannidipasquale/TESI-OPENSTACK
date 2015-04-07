import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;

public class GestioneServer extends ComputeNova
{
	private Server server;
	
	//FILE CONFIGURAZIONE
	ConfigurationProperties props = new ConfigurationProperties();
	
	//COSTRUTTORE
	public GestioneServer(){}
	
	//CREA SERVER
	public void CreazioneServer(OSClient os, String nomeServer, String flavorId, String imageId) 
	{				
		ServerCreate sc = Builders.server()
	            .name(nomeServer)
	            .flavor(flavorId)
	            .image(imageId)
	            .build();
	    server = os.compute().servers().boot(sc);
		while(true)
		{
			if(os.compute().servers().get(server.getId()).getStatus().toString()=="ACTIVE")
				break;
		}
	}
	
	//CANCELLA TUTTI I SERVER
	public void CancellaTutti(OSClient os)
	{
		int contatore = 0;
		boolean t = true;
		List<? extends Server> listaserver = os.compute().servers().list(false);
		String [] idServer = new String[listaserver.size()];
		Pattern p = Pattern.compile("id");
	    Matcher m = p.matcher(listaserver.toString());
	    while(m.find())
	    {
	        (listaserver.toString()).substring(m.start()+3, m.start()+39);
	        idServer[contatore] = (listaserver.toString()).substring(m.start()+3, m.start()+39);			        
	        os.compute().servers().delete(idServer[contatore]);
	        contatore++;
	    }
	    while(t)
  		{
  		    if(os.compute().servers().list(false).size()==0)
  		    {
  		    	t=false;
  		    }
  		}
	}
	
	//CANCELLA UN SOLO SERVER
	public void CancellaUnServer(OSClient os, String serverId)
	{
		boolean t = true;
		if(os.compute().servers().list(false).size()!=0)
		{
			os.compute().servers().delete(serverId);
		    while(t)
	  		{
		    	if(os.compute().servers().get(serverId).getInstanceName().isEmpty()) //VEDERE SE FUNZIONA
		    	{
	  		    	t=false;
	  		    }
	  		}
		}
		else
			System.out.println("\nNon ci sono server da cancellare");
	}
	
	//OPERAZIONI SERVER
	public void OperazioniServer(OSClient os, String serverId, String operazione)
	{	
		boolean t = true;
		os.compute().servers().action(serverId, Action.valueOf(operazione));
		while(t)
  		{
	    	if(os.compute().servers().get(serverId).getStatus().equals("Paused") || 
	    	   os.compute().servers().get(serverId).getStatus().equals("Suspended") ||
	    	   os.compute().servers().get(serverId).getStatus().equals("Active") ||
	    	   os.compute().servers().get(serverId).getStatus().equals("Shutoff") ||
	    	   os.compute().servers().get(serverId).getStatus().equals("Rescued") ||
	    	   os.compute().servers().get(serverId).getStatus().equals("Stopped"))
	    	{
  		    	t=false;
  		    }
  		}
	}
}
