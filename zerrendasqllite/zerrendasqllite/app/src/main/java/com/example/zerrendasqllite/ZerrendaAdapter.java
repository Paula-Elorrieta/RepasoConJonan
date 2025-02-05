package com.example.zerrendasqllite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ZerrendaAdapter extends ArrayAdapter<Lenguaia> {

    ArrayList<Lenguaia> lenguaiak;
    ArrayList<Lenguaia> lenguaiakFiltratuta = new ArrayList<>();

    public ZerrendaAdapter(Context context, ArrayList<Lenguaia> items) {
        super(context, 0, items);
        this.lenguaiak = items;
        this.lenguaiakFiltratuta.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.errenkada, parent, false);
        }

        Lenguaia lenguaia = getItem(position);
        TextView textID = convertView.findViewById(R.id.textID);
        TextView textIzena = convertView.findViewById(R.id.textName);
        TextView textDeskribapena = convertView.findViewById(R.id.textDescripcion);
        TextView textLibrea = convertView.findViewById(R.id.textLibrea);

        textID.setText(String.valueOf(getId(lenguaia)));
        textIzena.setText(lenguaia.getIzena());
        textDeskribapena.setText(lenguaia.getDeskribapena());
        textLibrea.setText(lenguaia.isLibrea() ? "Software libre" : "Software propietario");


        return convertView;
    }

//    public void filtraketaEgin(String texto) {
//
//        items.clear();
//
//        if (texto.isEmpty()) {
//            items.addAll(itemsFiltratuta);
//        } else {
//            texto = texto.toLowerCase();
//            for (Item item : itemsFiltratuta) {
//                if (item.getIzena().toLowerCase().contains(texto)) {
//                    items.add(item);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

    public int getId(Lenguaia lenguaia) {
        int id = 0;
        for (int i = 0; i < lenguaiak.size(); i++) {
            if (lenguaiak.get(i).getIzena().equalsIgnoreCase(lenguaia.getIzena())) {
                id = i + 1;
            }
        }

        return id;

    }
}