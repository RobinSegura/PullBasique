package main;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import ConnexionPackage.*;

public class Server {

	public static void main(String args[]) {

		java.util.Properties props = System.getProperties();

		int status = 0;

		ORB orb = null;

		try {

			orb = ORB.init(args, props);
			run(orb);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = 1;
		}

		if (orb != null) {
			try {
				orb.destroy();
			} catch (Exception ex) {
				ex.printStackTrace();
				status = 1;
			}
		}

		System.exit(status);
	}

	static int run(ORB orb) throws Exception {
		org.omg.CORBA.Object obj;
		org.omg.PortableServer.POA rootPOA = org.omg.PortableServer.POAHelper
				.narrow(orb.resolve_initial_references("RootPOA"));

		org.omg.PortableServer.POAManager manager = rootPOA.the_POAManager();

		Connexion_impl coImpl = new Connexion_impl();
		obj = rootPOA.servant_to_reference(coImpl);
		Connexion co = ConnexionHelper.narrow(obj);

		obj = orb.resolve_initial_references("NameService");

		NamingContext ctx = NamingContextHelper.narrow(obj);

		if (ctx == null) {
			System.out.println("Le composant NameService n'est pas un repertoire");
			return 0;
		}

		NameComponent[] name = new NameComponent[1];

		name[0] = new NameComponent("Hello", "");

		ctx.rebind(name, co);

		/*
		 * String ref = orb.object_to_string(hello); String refFile =
		 * "Hello.ref"; java.io.PrintWriter out = new java.io.PrintWriter( new
		 * java.io.FileOutputStream(refFile)); out.println(ref); out.close();
		 */

		System.out.println("Objet cree et reference");

		manager.activate();
		orb.run();

		return 0;
	}
}
