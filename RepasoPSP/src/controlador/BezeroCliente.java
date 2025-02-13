package controlador;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BezeroCliente {

	private Socket socketCliente;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public static void main(String[] args) {
		BezeroCliente cliente = new BezeroCliente();
		cliente.konexioa();
	}

	public void konexioa() {
		try {
			socketCliente = new Socket("localhost", Zerbitzaria.PORT);
			out = new ObjectOutputStream(socketCliente.getOutputStream());
			in = new ObjectInputStream(socketCliente.getInputStream());

			Scanner sc = new Scanner(System.in);
			System.out.println("Sartu izena:");
			String izena = sc.nextLine();
			System.out.println("Sartu pasahitza:");
			String pasahitza = sc.nextLine();

			Cliente cliente = new Cliente(izena, pasahitza);

			out.writeObject(cliente);
			out.flush();

			Boolean konektatuta = false;
			String mezua = in.readUTF();
			System.out.println(mezua);

			while (!konektatuta) {
				if (mezua.contains("Ondo")) {
					System.out.println("Konektatuta zara.");
					konektatuta = true;

					String argazkia = in.readUTF();
					System.out.println("URl de la imagen: " + argazkia);

					System.out.println("Nahi duzu aldatu? (Bai/Ez)");
					String aukera = sc.nextLine();

					if ("Bai".equals(aukera)) {
						System.out.println("Sartu URL berria:");
						String url = sc.nextLine();
						out.writeUTF(url);
						out.flush();
					} else {
						System.out.println("Ez duzu aldaketarik egin.");
						out.writeUTF(argazkia);
						out.flush();
					}

				} else {
					System.out.println("Ez zaude konektatuta.");
					System.out.println("Sartu izena:");
					izena = sc.nextLine();
					System.out.println("Sartu pasahitza:");
					pasahitza = sc.nextLine();
					cliente = new Cliente(izena, pasahitza);
					out.writeObject(cliente);
					out.flush();
				}
			}

			sc.close();

			socketCliente.close();
		} catch (Exception e) {
			System.out.println("Errorea konexioa egiterakoan: " + e.getMessage());
		}
	}

}
