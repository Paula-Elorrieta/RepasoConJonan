package vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.MusicaC;
import modelo.Musica;

import java.util.ArrayList;

import javax.swing.JButton;

public class MenuV extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public MenuV() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 796, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnObras = new JButton("Ver obras");
		btnObras.setBounds(243, 41, 291, 64);
		contentPane.add(btnObras);

		JButton btnObrasBen = new JButton("Ver obras guau guau");
		btnObrasBen.setBounds(243, 163, 291, 64);
		contentPane.add(btnObrasBen);

		JButton btnAnadir = new JButton("Añadir obras");
		btnAnadir.setBounds(243, 313, 291, 64);
		contentPane.add(btnAnadir);

		btnObras.addActionListener(e -> {
			ObrasV obrasV = new ObrasV();
			obrasV.setVisible(true);
			dispose();
		});

		btnObrasBen.addActionListener(e -> {
			ObrasBenV obrasBenV = new ObrasBenV();
			obrasBenV.setVisible(true);
			dispose();
		});
		
		btnAnadir.addActionListener(e -> {
			MusicaC musicaC = new MusicaC();
			ArrayList<Musica> obrakBerriak = musicaC.leerJsonMusica();
			
			musicaC.insertarMusica(obrakBerriak);
			
			JOptionPane.showMessageDialog(null, "Obras añadidas correctamente");
		});

	}

}
