package ariketa1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Zerbitzaria {
	public static int port = 12345;
	private ServerSocket serverSocket;
	private BufferedReader in;
	private PrintWriter out;

	public static void main(String[] args) {
		Zerbitzaria z = new Zerbitzaria();
		z.zerbitzariaHasi();
	}

	@SuppressWarnings("resource")
	public void zerbitzariaHasi() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Zerbitzaria hasi da " + port + " portuan.");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Bezeroa konektatu da.");
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				out.println("Zein da esaldia?");
				String esaldia = in.readLine();

				if (esaldia != null) {
					System.out.println("Bezeroak esaldi hau bidali du: " + esaldia);
				}

				String hashZerbitzaria = hashKodea(esaldia);

				out.println("Zein da hash kodea?");
				String hashBezeroa = in.readLine();

				System.out.println("Bezeroaren hash kodea: " + hashBezeroa);
				System.out.println("Zerbitzariaren hash kodea: " + hashZerbitzaria);

				if (hashZerbitzaria.equals(hashBezeroa)) {
					out.println("Hash kodea zuzena da!");
					System.out.println("Hash kodea zuzena da!");
				} else {
					out.println("Hash kodea okerra da!");
					System.out.println("Hash kodea okerra da!");
				}

				clientSocket.close();
			}
		} catch (Exception e) {
			System.out.println("Errorea zerbitzaria exekutatzerakoan: " + e.getMessage());
		}
	}

	private String hashKodea(String esaldia) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(esaldia.getBytes());
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
