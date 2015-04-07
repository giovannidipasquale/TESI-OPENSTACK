import java.util.List;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.storage.block.Volume;

public class GestioneStorage 
{
	public GestioneStorage() {}
	
	//LISTING DEI VOLUMI
	public void ListingVolume(OSClient os)
	{		
		List<? extends Volume> listavolumi = os.blockStorage().volumes().list();	
		System.out.println("\nI block storage sono i seguenti: \n");
		System.out.println(listavolumi.toString());
	}
	
	//CREAZIONE DEI VOLUMI
	public Volume CreaVolume(OSClient os, String nomeVolume, String descrizioneVolume, int memoriaVolume) throws InterruptedException
	{
		Volume v = os.blockStorage().volumes()
	             .create(Builders.volume()
	                .name(nomeVolume)
	                .description(descrizioneVolume)
	                .size(memoriaVolume)
	                .build());
		Thread.sleep(10000);
		System.out.println("\nVolume creato: \n"+ v.toString());
		return v;
	}
	
	//CANCELLAZIONE DEI VOLUMI
	public void CancellaVolume(OSClient os, String volumeId) throws InterruptedException
	{
		System.out.println(os.blockStorage().volumes().delete(volumeId));
		Thread.sleep(10000);
		System.out.println("\nVolume cancellato: \n");
	}
}
