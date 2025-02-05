package com.example.zerrendaerrepasoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ZerrendaAdapter extends ArrayAdapter<Lekua> {

    ArrayList<Lekua> lekuak;
    ArrayList<Lekua> lekuakFiltratuta = new ArrayList<>();

    public ZerrendaAdapter(Context context, ArrayList<Lekua> items) {
        super(context, 0, items);
        this.lekuak = items;
        this.lekuakFiltratuta.addAll(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.errenkada, parent, false);
        }

        Lekua lekua = getItem(position);
        TextView textID = convertView.findViewById(R.id.textID);
        TextView textIzena = convertView.findViewById(R.id.textName);
        TextView textDeskribapena = convertView.findViewById(R.id.textDescripcion);

        textID.setText(String.valueOf(getId(lekua)));
        textIzena.setText(lekua.getIzena());
        textDeskribapena.setText(lekua.getDeskribapena());


        return convertView;
    }

    public int getId(Lekua lekua) {
        int id = 0;
        for (int i = 0; i < lekuak.size(); i++) {
            if (lekuak.get(i).getIzena().equalsIgnoreCase(lekua.getIzena())) {
                id = i + 1;
            }
        }

        return id;

    }
}
