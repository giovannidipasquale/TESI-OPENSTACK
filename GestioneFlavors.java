import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;

public class GestioneFlavors 
{
	public GestioneFlavors() {}
	
	//CREA MACCHINA (FLAVOR)
	public Flavor CreaFlavor(OSClient os, String nameFlavor, int ram, int numbervcpu, int spaceDisk)
	{			
			Flavor flavor = Builders.flavor()
			                        .name(nameFlavor)
			                        .ram(ram)
			                        .vcpus(numbervcpu)
			                        .disk(spaceDisk)
			                        .build();
			flavor = os.compute().flavors().create(flavor);
			System.out.println("Il flavor √® stato creato ");
			return flavor;
	}
	
	//USA MACCHINA (FLAVOR)
	public Flavor UsaFlavor (OSClient os, String FlavorId)
	{
		Flavor flavor = os.compute().flavors().get(FlavorId);
		System.out.print("\nCaratteristiche Macchina: " + flavor.toString());
		return flavor;
	}
	
	//CANCELLA FLAVOR
	public void CancellaFlavor(String FlavorId, OSClient os)
	{
		if(os.compute().flavors().get(FlavorId)!=null)
		{
			os.compute().flavors().delete(FlavorId);
			System.out.println("\nFlavor cancellato ");
		}
		else
			System.out.println("\nFlavor non presente ");
	}
}
