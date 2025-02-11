package vista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.MusicaC;
import modelo.Musica;

public class ParanoiaPaula extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAtzera;
    private JComboBox<String> comboCompositor;
    private ArrayList<Musica> obras;

    public ParanoiaPaula() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 821, 510);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        String[] columnNames = {"Compositor", "Nace", "Muere", "Obra", "Estilo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 785, 300);
        contentPane.add(scrollPane);

        comboCompositor = new JComboBox<>();
        comboCompositor.setBounds(10, 10, 200, 30);
        contentPane.add(comboCompositor);
        comboCompositor.addActionListener(e -> filtrarPorCompositor());

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
        obras = musicaC.obtenerTodasObras();  

        Set<String> compositoresSet = new HashSet<>();
        for (Musica musica : obras) {
            compositoresSet.add(musica.getCompositor().getNomCompo());
        }

        comboCompositor.addItem("Todos"); 
        for (String compositor : compositoresSet) {
            comboCompositor.addItem(compositor);
        }

        actualizarTabla(obras);
    }

    private void filtrarPorCompositor() {
        String compositorSeleccionado = (String) comboCompositor.getSelectedItem();
        if (compositorSeleccionado == null) return;

        if (compositorSeleccionado.equals("Todos")) {
            actualizarTabla(obras);  
        } else {
            ArrayList<Musica> obrasFiltradas = new ArrayList<>();
            for (Musica musica : obras) {
                if (musica.getCompositor().getNomCompo().equals(compositorSeleccionado)) {
                    obrasFiltradas.add(musica);
                }
            }
            actualizarTabla(obrasFiltradas);
        }
    }

    private void actualizarTabla(ArrayList<Musica> listaObras) {
        tableModel.setRowCount(0); 

        for (Musica musica : listaObras) {
            Object[] rowData = {
                musica.getCompositor().getNomCompo(),
                musica.getCompositor().getNaceCompo(),
                musica.getCompositor().getMuereCompo(),
                musica.getDescMusica(),
                musica.getEstilo().getNomEstilo()
            };
            tableModel.addRow(rowData);
        }
    }
}
