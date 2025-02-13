package controlador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Zerbitzaria {
	public static final int PORT = 9999;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ArrayList<Cliente> clientes = new ArrayList<>();

	public static void main(String[] args) {
		Zerbitzaria z = new Zerbitzaria();
		z.zerbitzariaMartxanJarri();
	}

	private void zerbitzariaMartxanJarri() {

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Zerbitzaria martxan...");

			while (true) {
				clientSocket = serverSocket.accept();
				System.out.println("Konektatua: " + clientSocket.getInetAddress());
				HiloServidor hilo = new HiloServidor(clientSocket, clientes);
				hilo.start();
			}

		} catch (IOException e) {
			System.out.println("Errorea zerbitzaria martxan jartzeko: " + e.getMessage());
		}

	}

}
