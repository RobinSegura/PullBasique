package main;

import java.util.Scanner;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import ConnexionPackage.*;

import org.omg.CORBA.ORBPackage.InvalidName;

public class Client {

	public static void main(String args[]) {
		java.util.Properties props = System.getProperties();

		int status = 0;
		org.omg.CORBA.ORB orb = null;

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

	static void run(ORB orb) {
		org.omg.CORBA.Object obj = null;

		try {

			obj = orb.resolve_initial_references("NameService");
		} catch (InvalidName e) {
			e.printStackTrace();
			System.exit(1);
		}

		NamingContext ctx = NamingContextHelper.narrow(obj);

		if (ctx == null) {
			System.out.println("Le composant NameService n'est pas un repertoire");
			System.exit(1);
		}

		NameComponent[] name = new NameComponent[1];

		name[0] = new NameComponent("Hello", "");

		try {
			obj = ctx.resolve(name);
		} catch (Exception e) {
			System.out.println("Composant inconnu");
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 * String refFile = "Hello.ref"; java.io.BufferedReader in = new
		 * java.io.BufferedReader( new java.io.FileReader(refFile)); String ref
		 * = in.readLine(); System.out.println("IOR :"+ref); obj =
		 * orb.string_to_object(ref); //obj =
		 * orb.string_to_object("relfile:/Hello.ref");
		 */

		Connexion connexion = ConnexionHelper.narrow(obj);
		boolean disconnected = false;

		// Recupération du pseudo
		Scanner scp = new Scanner(System.in);
		System.out.println("Veuillez entrez votre pseudo :");
		String pseudo = scp.nextLine();
		System.out.println("Bienvenue " + pseudo);
		connexion.connect(pseudo);

		System.out.println("Récupération des clients connectés ...");
		String[] clientsCo = connexion.getClients();
		System.out.println("Actuellement, les clients connectés sont :");
		for (int i = 0; i < clientsCo.length; i++) {
			System.out.print(clientsCo[i] + ", ");
		}

		while (!disconnected) {
			System.out.println("");
			System.out.println(
					"Tapez le pseudo de votre destinataire (DECO : arrêt du tchat / CHECK : récupération message) :");
			Scanner scpd = new Scanner(System.in);
			String answer = scpd.nextLine();
			if (answer.equals("DECO")) {
				System.out.println("Au revoir " + pseudo);
				connexion.disconnect(pseudo);
				disconnected = true;
			} else if (answer.equals("CHECK")) {
				System.out.println("Récupération de vos messages ...");
				System.out.println("Voici la liste de vos messages :");
				String[] recupMess = connexion.getMessages(pseudo);
				for (int i = 0; i < recupMess.length; i++) {
					System.out.println(recupMess[i]);
				}
			} else {
				System.out.println("Quel message souhaitez-vous envoyé à " + answer);
				Scanner scpm = new Scanner(System.in);
				String msg = scpm.nextLine();
				connexion.sendMessages(pseudo, answer, msg);
			}

		}

	}

}
