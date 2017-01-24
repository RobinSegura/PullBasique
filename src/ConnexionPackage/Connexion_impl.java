package ConnexionPackage;

import java.util.ArrayList;

public class Connexion_impl extends ConnexionPOA {

	private static ArrayList<String> clients = new ArrayList<String>();
	private ArrayList<String[]> DBmessages;

	@Override
	public void connect(String pseudo) {
		// TODO Auto-generated method stub
		System.out.println(pseudo + " s'est connecté");
		clients.add(pseudo);
	}

	@Override
	public void disconnect(String pseudo) {
		// TODO Auto-generated method stub

		System.out.println(pseudo + " s'est déconnecté");
		clients.remove(pseudo);
	}

	@Override
	public String[] getClients() {
		// TODO Auto-generated method stub
		String[] tab = new String[clients.size()];
		return clients.toArray(tab);
	}

	@Override
	public void sendMessages(String from, String to, String message) {
		// TODO Auto-generated method stub
		System.out.println("Un message est stocké !");
		String[] incoming = { from, to, message };
		DBmessages.add(incoming);
	}

	@Override
	public String[] getMessages(String pseudo) {
		// TODO Auto-generated method stub
		System.out.println("Des messages sont récupérés !");
		ArrayList<String> retrieve = new ArrayList<String>();

		for (int i = 0; i < DBmessages.size(); i++) {
			if (DBmessages.get(i)[1].equals(pseudo)){
				retrieve.add(DBmessages.get(i)[0] + " : " + DBmessages.get(i)[1]);
			}
		}
		DBmessages.clear();
		String[] tab = new String[retrieve.size()];
		return retrieve.toArray(tab);
	}

	public ArrayList<String[]> getDBmessages() {
		return DBmessages;
	}
}
