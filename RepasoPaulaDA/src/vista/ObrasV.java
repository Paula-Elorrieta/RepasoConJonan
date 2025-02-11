package vista;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.MusicaC;
import modelo.Musica;

public class ObrasV extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnAtzera;

	public ObrasV() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 821, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] columnNames = { "Compositor", "Nace", "Muere", "Obra", "Estilo" };
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 785, 340);
		contentPane.add(scrollPane);
		
		btnAtzera = new JButton("Atzera");
		btnAtzera.setBounds(10, 414, 161, 46);
		contentPane.add(btnAtzera);
		
		btnAtzera.addActionListener(e -> {
			MenuV menu = new MenuV();
			menu.setVisible(true);
			dispose();
		});

		taulaKargatu();
	}

	private void taulaKargatu() {
		MusicaC musicaC = new MusicaC();
		ArrayList<Musica> obras = musicaC.obtenerTodasObras();

		for (Musica musica : obras) {
			Object[] rowData = { musica.getCompositor().getNomCompo(), musica.getCompositor().getNaceCompo(),
					musica.getCompositor().getMuereCompo(), musica.getDescMusica(), musica.getEstilo().getNomEstilo() };
			tableModel.addRow(rowData);
		}
	}
}
