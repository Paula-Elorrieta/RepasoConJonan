package ariketa1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Bezeroa {

	private Socket socketCliente;
	private PrintWriter out = null;
	private BufferedReader in = null;

	public static void main(String[] args) {
		Bezeroa b = new Bezeroa();
		b.konexioa();
	}

	public void konexioa() {
		try {
			socketCliente = new Socket("localhost", Zerbitzaria.port);
			out = new PrintWriter(socketCliente.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

			String mezua = in.readLine();
			String esaldia = "";

			if (mezua.contains("esaldia")) {
				System.out.println(mezua);
				Scanner sc = new Scanner(System.in);
				esaldia = sc.nextLine();

				out.println(esaldia);
				sc.close();

			}

			mezua = in.readLine();
			if (mezua.contains("hash kodea")) {
				System.out.println(mezua);
				String hashKodea = hashKodea(esaldia);

				System.out.println("Egindako Hash kodea: " + hashKodea);

				out.println(hashKodea);
			}

			mezua = in.readLine();
			System.out.println(mezua);

		} catch (Exception e) {
			System.out.println("Errorea konexioa egiterakoan: " + e.getMessage());
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
