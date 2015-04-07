import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.identity.Role;
import org.openstack4j.model.identity.Tenant;
import org.openstack4j.model.identity.User;

public class GestioneUtenti extends IdentityKeystone
{
	public GestioneUtenti() {}
	
	//CREAZIONE TENANT, USER E ASSEGNAZIONE RUOLO ALL'UTENTE
	public void CreazioneUtente(OSClient os, String tenant, String nameUser, String passwordUser, String emailUser, String ruolo)
	{
		Tenant Bob = os.identity().tenants().create(Builders.tenant().name(tenant).build());
		User Alice = os.identity().users()
		              .create(Builders.user()
		                                .name(nameUser)
		                                .password(passwordUser)
		                                .email(emailUser)
		                                .tenant(Bob).build());
		Role memberRole = os.identity().roles().create(ruolo);
		os.identity().roles().addUserRole(Bob.getId(), Alice.getId(), memberRole.getId());
		os.identity().users().enableUser(Alice.getId(), true);
		System.out.println("Tenant e membro creati");
	}
	
	//AGGIORNA DATI UTENTE
	public void setEmail(OSClient os, String nomeUtente, String newemail)
	{
		User john = os.identity().users().getByName(nomeUtente);
		os.identity().users().update(john.toBuilder().email(newemail).build());
		System.out.println("\nEmail cambiata\n");
	}
	
	public void AbilitaUtente(OSClient os, String nomeUtente, String abilitaUtente)
	{
		User john = os.identity().users().getByName(nomeUtente);
		os.identity().users().enableUser(john.getId(), Boolean.parseBoolean(abilitaUtente));
		if(abilitaUtente.equals("true"))
			System.out.println("\nUtente Abilitato\n");
		else
			System.out.println("\nUtente Disabilito\n");
	}
	
	public void setPassword(OSClient os, String nomeUtente, String newpassword)
	{
		User john = os.identity().users().getByName(nomeUtente);
		os.identity().users().changePassword(john.getId(), newpassword);
		System.out.println("\nPassword cambiata\n");
	}
	
	public void deleteRuolo(OSClient os, String nomeUtente, String ruolo)
	{
		User john = os.identity().users().getByName(nomeUtente);
		Role role = os.identity().roles().getByName(ruolo);
		if (role == null)
			System.out.println("\nL'utente " + john.getName() +" non ricopre nessun ruolo!" );
		else
		{	
			os.identity().roles().delete(role.getId());
			System.out.println("\nIl ruolo di " + john.getName()+" e' stato cancellato con ruolo id: " + role.getId());
		}
	}
	
	public void deleteUtente(OSClient os, String nomeUtente)
	{
		User john = os.identity().users().getByName(nomeUtente);
		os.identity().users().delete(john.getId());
		System.out.println("\nL'utente " + john.getName()+" e' stato cancellato \n");
	}
}
