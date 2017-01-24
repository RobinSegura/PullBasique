package ConnexionPackage;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import junit.framework.TestCase;

public class Connexion_implTest extends TestCase {
	
	Connexion connexion;
	Connexion_impl connexionImpl;
	
	String p1;
	String p2;
	org.omg.CORBA.ORB orb = null;
	
	@Test
	public void testConnect() {
		assertEquals(p1, connexionImpl.getClients()[0]);
	}

	@Test
	public void testDisconnect() {
		connexion.disconnect(p1);
		String[] s = connexionImpl.getClients();
		assertEquals(p1, s[0]);
	}

	@Test
	public void testSendMessage() {
		assertEquals(p1, connexionImpl.getDBmessages().get(0)[0]);
		assertEquals(p2, connexionImpl.getDBmessages().get(0)[1]);
		assertEquals("HELLO", connexionImpl.getDBmessages().get(0)[2]);
}

	@Test
	public void testGetMessage() {
		String[] msg = connexion.getMessages(p2);
		System.out.println(msg[0]);
		assertEquals(connexionImpl.getDBmessages().get(0)[0]+" : "+connexionImpl.getDBmessages().get(0)[2], msg[0]);
	}

	@Before
	public void setUp() throws Exception {
		java.util.Properties props = System.getProperties();
		orb = ORB.init(new String[0], props);
		org.omg.CORBA.Object obj;
		org.omg.PortableServer.POA rootPOA = org.omg.PortableServer.POAHelper
				.narrow(orb.resolve_initial_references("RootPOA"));

		org.omg.PortableServer.POAManager manager = rootPOA.the_POAManager();
		
		connexionImpl = new Connexion_impl();
		obj = rootPOA.servant_to_reference(connexionImpl);
		connexion = ConnexionHelper.narrow(obj);
		obj = orb.resolve_initial_references("NameService");

		NamingContext ctx = NamingContextHelper.narrow(obj);
		NameComponent[] name = new NameComponent[1];
		name[0] = new NameComponent("Connexion", "");

		ctx.rebind(name, connexion);
		manager.activate();
		p1 = "TEST";
		p2 = "TEST_2";
		connexion.connect(p1);		
		connexion.connect(p2);
		
		connexion.sendMessages(p1, p2, "HELLO");		

	}

	@After
	public void tearDown() throws Exception {
		connexion.disconnect(p1);
		connexion.disconnect(p2);
	}
}
