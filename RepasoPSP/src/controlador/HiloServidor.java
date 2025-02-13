package controlador;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class HiloServidor extends Thread {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socketCliente;
	private ArrayList<Cliente> clientes;

	public HiloServidor(Socket clientSocket, ArrayList<Cliente> clientes) {
		this.socketCliente = clientSocket;
		this.clientes = new ArrayList<>();
		rellenarClientes();

		try {
			this.socketCliente.setSoTimeout(30000);
		} catch (Exception e) {
			System.out.println("No se pudo establecer timeout en el socket: " + e.getMessage());
		}
	}

	private void rellenarClientes() {
		clientes = new ArrayList<Cliente>();
		clientes.add(new Cliente("Jon", hashPassword("1234")));
		clientes.add(new Cliente("Ane", hashPassword("1234")));
		clientes.add(new Cliente("Mikel", hashPassword("1234")));
		clientes.add(new Cliente("Amagoia", hashPassword("1234")));
		clientes.add(new Cliente("Iker", hashPassword("1234")));
	}

	public void run() {
		try {
			out = new ObjectOutputStream(socketCliente.getOutputStream());
			in = new ObjectInputStream(socketCliente.getInputStream());
			Cliente cliente;

			while (true) {
				try {
					cliente = (Cliente) in.readObject();
				} catch (EOFException | SocketException e) {
					System.out.println("Cliente desconectado.");
					break;
				}

				boolean encontrado = false;
				for (Cliente usuario : clientes) {
					if (usuario.getNombre().equals(cliente.getNombre())
							&& usuario.getContrasena().equals(hashPassword(cliente.getContrasena()))) {
						out.writeUTF("Ondo logueatu zara");
						out.flush();
						encontrado = true;

						out.writeUTF(
								"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png");
						out.flush();

						try {
							String url = in.readUTF();
							if (!url.equals(
									"https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_92x30dp.png")) {
								out.writeUTF("Argazkia aldatu da.");
							} else {
								out.writeUTF("Argazkia ez da aldatu.");
							}
							out.flush();
						} catch (EOFException | SocketException e) {
							System.out.println("Cliente desconectado durante la transmisión de datos.");
							break;
						}
						break;
					}
				}

				if (!encontrado) {
					out.writeUTF("Login txarto.");
					out.flush();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en la comunicación con el cliente: " + e.getMessage());
		} finally {
			try {
				if (socketCliente != null && !socketCliente.isClosed()) {
					socketCliente.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
