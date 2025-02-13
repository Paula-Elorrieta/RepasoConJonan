package ariketa2;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class HiloServidor extends Thread {

	private Socket clientSocket;
	private ArrayList<Usuario> usuarios;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public HiloServidor(Socket socket, ArrayList<Usuario> usuarios) {
		this.clientSocket = socket;
		this.usuarios = usuarios;
		FillUsers();
	}

	public void FillUsers() {
		usuarios.add(new Usuario("admin", "admin", createHash("admin")));
		usuarios.add(new Usuario("admin1", "admin1", createHash("admin1")));
		usuarios.add(new Usuario("admin2", "admin2", createHash("admin2")));
	}

	public void run() {
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());

			out.writeUTF("Zer nahi duzu egin? 1-Erregistratu  2-Saioa hasi");
			out.flush();
			String aukera = in.readUTF();

			if ("1".equals(aukera)) {
				out.writeUTF("Sartu erabiltzailea:");
				out.flush();
				String erabiltzailea = in.readUTF();
				out.writeUTF("Sartu izena:");
				out.flush();
				String izena = in.readUTF();
				out.writeUTF("Sartu pasahitza:");
				out.flush();
				String pasahitza = createHash(in.readUTF());

				Usuario u = new Usuario(erabiltzailea, izena, pasahitza);
				synchronized (usuarios) {
					usuarios.add(u);
				}
				out.writeUTF("Erregistroa arrakastatsua!");
				out.flush();

			} else if ("2".equals(aukera)) {
				out.writeUTF("Sartu erabiltzailea:");
				out.flush();
				String erabiltzailea = in.readUTF();
				out.writeUTF("Sartu pasahitza:");
				out.flush();
				String pasahitza = createHash(in.readUTF());

				boolean encontrado = false;
				for (Usuario usuario : usuarios) {
					if (usuario.getErabiltzailea().equals(erabiltzailea) && usuario.getPasahitza().equals(pasahitza)) {
						out.writeUTF("Ongi etorri " + usuario.getIzena());
						out.flush();
						encontrado = true;
						break;
					}
				}

				if (!encontrado) {
					out.writeUTF("Erabiltzailea edo pasahitza ez da zuzena.");
					out.flush();
				}
			}

			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String createHash(String esaldia) {
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
