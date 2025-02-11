package vista;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.MusicaC;
import modelo.Musica;

public class ObrasBenV extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAtzera;

    public ObrasBenV() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 898, 515);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] columnNames = {"Obra"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };        
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 862, 279);
        contentPane.add(scrollPane);
        
        btnAtzera = new JButton("Atzera");
        btnAtzera.setBounds(10, 361, 226, 55);
        contentPane.add(btnAtzera);
        
		btnAtzera.addActionListener(e -> {
			MenuV menu = new MenuV();
			menu.setVisible(true);
			dispose();
		});

		kargatuMozart();
    }

    private void kargatuMozart() {
        MusicaC musicaC = new MusicaC();
        ArrayList<Musica> obras = musicaC.obtenerObrasMozart();
        
        for (Musica musica : obras) {
            Object[] rowData = {musica.getDescMusica()};
            tableModel.addRow(rowData);
        }
    }
}