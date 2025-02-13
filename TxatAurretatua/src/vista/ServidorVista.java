package vista;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import modelo.Servidor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServidorVista extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JTextArea textArea;
	private final DefaultListModel<String> clientListModel;

	public ServidorVista(Servidor servidor) {
		setTitle("Ventana servidor");
		setSize(632, 539);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 61, 350, 367);
		getContentPane().add(scrollPane);

		clientListModel = new DefaultListModel<>();
		JList<String> clientList = new JList<>(clientListModel);
		JScrollPane scrollPane_1 = new JScrollPane(clientList);
		scrollPane_1.setBounds(370, 11, 236, 417);
		getContentPane().add(scrollPane_1);

		JButton stopButton = new JButton("Salir");
		stopButton.setBounds(10, 439, 180, 23);
		stopButton.addActionListener(e -> servidor.zerbitzariaItzali());
		getContentPane().add(stopButton);

		JLabel lblNewLabel = new JLabel("CONEXIONES ACTUALES =" + clientList.getModel().getSize());
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 13, 350, 38);
		getContentPane().add(lblNewLabel);

		setVisible(true);

		// Konexio kopurua eguneratu 1 segundoan behin
		new Thread(() -> {
			while (true) {
				int konexioTamaina = clientList.getModel().getSize();
				lblNewLabel.setText("CONEXIONES ACTUALES = " + konexioTamaina);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		// Taulan bi klick egitean bezeroa deskonektatu
		clientList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String selectedAlias = clientList.getSelectedValue();
					if (selectedAlias != null) {
						servidor.bezeroaDeskonektatu(selectedAlias);
					}
				}
			}
		});

	}

	// Zerrendari bezero kudeaketa gehitu/ezabatu
	public void bezeroGehitu(String alias) {
		clientListModel.addElement(alias);

	}

	public void bezeroEzabatu(String alias) {
		clientListModel.removeElement(alias);

	}

	// Textu eremuan testua erakutsi
	public void textuErakutsi(String evento) {
		textArea.append(evento + "\n");
	}
}
