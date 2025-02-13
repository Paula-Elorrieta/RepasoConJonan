package ariketa2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Zerbitzaria {

    private static final int PORT = 12345;
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public static void main(String[] args) {
        new Zerbitzaria().iniciarServidor();
    }

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Zerbitzaria martxan dago " + PORT + " portuan...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Bezero konektatua.");
                new HiloServidor(clientSocket, usuarios).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
