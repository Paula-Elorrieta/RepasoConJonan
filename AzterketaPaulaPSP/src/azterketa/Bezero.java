package azterketa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * 
 * ZPP exam
 * 
 * Egilea: Paula Fraga Pinta
 * 
 */
public class Bezero {

	public static void main(String[] args) {

		Socket bezeroa;
		try {
			bezeroa = new Socket("localhost", 5000);
			System.out.println("Bezeroa konektuta");

			ObjectOutputStream out = new ObjectOutputStream(bezeroa.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(bezeroa.getInputStream());

			DataOutputStream irteera = new DataOutputStream(bezeroa.getOutputStream());
			DataInputStream sarrera = new DataInputStream(bezeroa.getInputStream());

			Scanner sc = new Scanner(System.in);
			System.out.println(sarrera.readUTF());
			String idZenbakia = sc.nextLine();

			irteera.writeUTF(idZenbakia);

			String testua = sarrera.readUTF();

			if (!testua.equals("*")) {
				System.out.println(testua);
				irteera.writeUTF(hashPassword(sc.nextLine()));
				Eskaera es = (Eskaera) in.readObject();
				
				if (es == null) {
					System.out.println("Pasahitza ez da zuzena");
				} else {
					System.out.println(sarrera.readUTF());
					String respuesta = sc.nextLine();

					if (respuesta.equalsIgnoreCase("0")) {
						out.writeObject(es);
					} else {
						es.setIrudia(respuesta);
						out.writeObject(es);
					}
					
					System.out.println(sarrera.readUTF());
				}

			} else {
				System.out.println("Ez da eskaerarik aurkitu adierazitako ID-rekin. Programatik ateratzen.");
			}

			sc.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}
