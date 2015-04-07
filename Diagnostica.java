import java.util.Map;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.VNCConsole;
import org.openstack4j.model.compute.VNCConsole.Type;

public class Diagnostica 
{		
	public Diagnostica() {}
	
	public void Analizza(OSClient os, String idServer)
	{
		String consoleOutput = os.compute().servers().getConsoleOutput(idServer, 50);
		System.out.println("\nL'output della console √® il seguente: \n\n" + consoleOutput);
		VNCConsole console = os.compute().servers().getVNCConsole(idServer, Type.NOVNC);
		System.out.println("\nL'output della console √® il seguente: \n\n" + console.toString());
		Map<String, ? extends Number> diagnostics = os.compute().servers().diagnostics(idServer);
		System.out.println("\nL'output della diagnostica √® la seguente: \n\n" + diagnostics.toString());
	}
}
