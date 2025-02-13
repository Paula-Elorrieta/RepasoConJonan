package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

import vista.ServidorVista;

public class Servidor {
	private ServerSocket serverSocket;
	public static final int port = 3333;
	private ServidorVista servidorVista;
	private HashSet<String> aliasSet; // Erabiltzaileak identifikatzeko
	private HashMap<String, Socket> clienteSockets; // Bezeroen socket-ak gordetzeko

	public Servidor() {
		servidorVista = new ServidorVista(this);
		aliasSet = new HashSet<>();
		clienteSockets = new HashMap<>();
	}

	// Bista jaso mezuak hobeto erakusteko
	public ServidorVista getServidorVista() {
		return servidorVista;
	}

	// Alias kontrolatzeko metodoak
	public synchronized void gehituAlias(String alias, Socket socket) {
		aliasSet.add(alias);
		clienteSockets.put(alias, socket);
	}

	public synchronized void ezabatuAlias(String alias) {
		aliasSet.remove(alias);
		clienteSockets.remove(alias);

	}

	public synchronized boolean aliasBadago(String alias) {
		return aliasSet.contains(alias);
	}

	// Bezeroa deskonektatzeko metodoa, mezu bat bidaltzen dio bezeroari gero socket
	// hori izteko
	public void bezeroaDeskonektatu(String alias) {
		Socket socket = clienteSockets.get(alias);
		if (socket != null) {
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Deskonektatu");
				socket.close();
				ezabatuAlias(alias);
				servidorVista.bezeroEzabatu(alias);
				servidorVista.textuErakutsi(alias + " se ha desconectado.");
			} catch (IOException e) {
				servidorVista.textuErakutsi("Error bezeroan deskonektatzean " + e.getMessage());
			}
		}
	}

	public void zerbitzariaPiztu() {
		try {
			serverSocket = new ServerSocket(port);
			servidorVista.textuErakutsi("Esperando conexiones...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				String alias = in.readLine();

				// Alias errepikatua bada, errorea bidali eta konexioa itxi, ez du bezeroa
				// gehitu
				synchronized (this) {
					if (aliasBadago(alias)) {
						out.println("AliasEnUso");
						clientSocket.close();
					} else {
						gehituAlias(alias, clientSocket);
						servidorVista.bezeroGehitu(alias);
						servidorVista.textuErakutsi(alias + " ha entrado al chat.");
						out.println("Konektatuta");

						new Thread(new HariHartzailea(clientSocket, alias, this)).start();
					}
				}
			}
		} catch (IOException e) {
			zerbitzariaItzali();
		}

	}

	// Zerbitzaria itzali
	public void zerbitzariaItzali() {
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
				servidorVista.textuErakutsi("Servidor detenido.");
				System.exit(0);

			}
		} catch (IOException e) {
			servidorVista.textuErakutsi("Error zerbitzarian itzaltzean: " + e.getMessage());
		}
	}

	// Zerbitzari instantzia sortu eta zerbitzaria piztu
	public static void main(String[] args) {
		Servidor servidor = new Servidor();
		servidor.zerbitzariaPiztu();
	}
}