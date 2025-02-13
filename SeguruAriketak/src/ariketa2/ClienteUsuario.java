package ariketa2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteUsuario {

	private Socket socketCliente;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public static void main(String[] args) {
		ClienteUsuario cliente = new ClienteUsuario();
		cliente.konexioa();
	}

	public void konexioa() {
		try {
			socketCliente = new Socket("localhost", 12345);
			out = new ObjectOutputStream(socketCliente.getOutputStream());
			in = new ObjectInputStream(socketCliente.getInputStream());


			Scanner sc = new Scanner(System.in);

			System.out.println(in.readUTF());
			String aukera = sc.nextLine();
			out.writeUTF(aukera);
			out.flush();

			if ("1".equals(aukera)) {
				System.out.println(in.readUTF());
				out.writeUTF(sc.nextLine());
				out.flush();
				System.out.println(in.readUTF());
				out.writeUTF(sc.nextLine());
				out.flush();
				System.out.println(in.readUTF());
				out.writeUTF(sc.nextLine());
				out.flush();

				System.out.println(in.readUTF());

			} else if ("2".equals(aukera)) {
				System.out.println(in.readUTF());
				out.writeUTF(sc.nextLine());
				out.flush();
				System.out.println(in.readUTF());
				out.writeUTF(sc.nextLine());
				out.flush();

				System.out.println(in.readUTF());
			}

			sc.close();
			socketCliente.close();
		} catch (IOException e) {
			System.out.println("Errorea konexioa egiterakoan: " + e.getMessage());
		}
	}
}
