package modelo;

import java.awt.EventQueue;
import java.io.*;
import java.net.*;
import javax.swing.*;

import vista.*;

public class Cliente {
	private Socket socket;
	private PrintWriter out; // Mezuak bidaltzeko
	private BufferedReader in; // Mezuak jasotzeko
	private String alias; // nickname

	public void konexioa(String host, int port, String alias) {
		this.alias = alias;
		try {
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println(alias);

			String respuesta = in.readLine();
			if ("Konektatuta".equals(respuesta)) {
				SwingUtilities.invokeLater(() -> new ClienteVista(this));
				// Mezuak entzuteko hari bat sortu bat sortu eta hasi
				new Thread(() -> mezuakEntzun()).start();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error konexioan egitean: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			deskonexioa();
		}
	}

	// Metodo hau mezuak entzuteko erabiliko da, deskonexioan egiteko
	private void mezuakEntzun() {
		try {
			String mensaje;
			while (socket != null && !socket.isClosed() && (mensaje = in.readLine()) != null) {
				if ("Deskonektatu".equals(mensaje)) {
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(null, "Zerbitzaria konexioa itzi du", "Deskonexioa",
								JOptionPane.INFORMATION_MESSAGE);
						deskonexioa();
					});
					break;
				}
			}
		} catch (IOException e) {
			// Socket itxi egin bada, errorea ez da erakutsiko
			if (!socket.isClosed()) {
				System.err.println("Error mezuak entzuterakoan: " + e.getMessage());
			}
		}
	}

	// Zerbitzarirako mezua bidaltzeko metodoa
	public void mezuaBidali(String mensaje) {
		if (out != null) {
			out.println(alias + "> " + mensaje);
		}
	}

	// Socket itxi
	public void deskonexioa() {
		try {
			if (out != null) {
				out.println(alias + " se ha desconectado");
			}
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Error konexioa: " + e.getMessage());
		}
	}

	// Bezero bat instantzia sortzean, alias bista ikusiko da.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AliasVista frame = new AliasVista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}