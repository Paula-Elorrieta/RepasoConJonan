package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// Hari hau bezero bakoitzeko sortuko da, eta mezuak jasotzen ditu
public class HariHartzailea implements Runnable {

	private Socket clientSocket;
	private Servidor zerbitzaria;
	private String alias;
	private BufferedReader in; // Mezuak jasotzeko

	public HariHartzailea(Socket clientSocket, String alias, Servidor zerbitzaria) throws IOException {
		this.clientSocket = clientSocket;
		this.alias = alias;
		this.zerbitzaria = zerbitzaria;
		this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	@Override
	public void run() {
		try {
			String mensaje;
			while ((mensaje = in.readLine()) != null) {
				if ("Deskonektatu".equals(mensaje)) {
					break;
				}
				zerbitzaria.getServidorVista().textuErakutsi(mensaje);
			}
		} catch (IOException e) {
		} finally {
			try {
				if (!clientSocket.isClosed()) {
					clientSocket.close();
				}
			} catch (IOException e) {
				zerbitzaria.getServidorVista().textuErakutsi("Error socket iztean " + alias + ": " + e.getMessage());
			}
			zerbitzaria.getServidorVista().bezeroEzabatu(alias);
			zerbitzaria.ezabatuAlias(alias);
		}
	}

}
