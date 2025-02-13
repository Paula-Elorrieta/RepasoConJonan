package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.Cliente;
import modelo.Servidor;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;

public class ClienteVista extends JFrame {
	private static final long serialVersionUID = 1L;
	private final Cliente cliente;
	private final JTextArea textArea;
	private final JTextField textField;

	public ClienteVista(Cliente cliente) {
		this.cliente = cliente;
		setTitle("TXAT BEZEROA: " + cliente.getAlias());
		setSize(548, 429);
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 54, 384, 318);
		getContentPane().add(scrollPane);

		textArea.append(cliente.getAlias() + "> konektatu da.\n");

		textField = new JTextField();
		textField.setBounds(10, 11, 384, 32);
		getContentPane().add(textField);

		JButton irtenButton = new JButton("Salir");
		irtenButton.setBounds(404, 51, 108, 32);
		irtenButton.addActionListener(e -> {
			cliente.deskonexioa();
			System.exit(0);
		});
		getContentPane().add(irtenButton);

		JButton enviarButton = new JButton("Enviar");
		enviarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje = textField.getText().trim();

				if (mensaje.equalsIgnoreCase("*") || mensaje.equalsIgnoreCase("Irten")) {
					cliente.deskonexioa();
					System.exit(0);
					return;
				}

				if (!mensaje.isEmpty()) {
					cliente.mezuaBidali(mensaje);
					textArea.append(cliente.getAlias() + "> " + mensaje + "\n");
					textField.setText("");
				}
			}
		});
		enviarButton.setBounds(404, 11, 108, 32);
		getContentPane().add(enviarButton);

		setVisible(true);
	}
}
