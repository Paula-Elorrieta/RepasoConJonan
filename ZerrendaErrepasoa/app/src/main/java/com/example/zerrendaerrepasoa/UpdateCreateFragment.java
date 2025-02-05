package com.example.zerrendaerrepasoa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class UpdateCreateFragment extends Fragment {

    private ArrayList<Lekua> lekuak;
    private Lekua lekua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_create, container, false);
        Bundle bundle = getArguments();
        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        try {
            if (bundle.getSerializable("lekua") != null) {
                lekua = (Lekua) bundle.getSerializable("lekua");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bundle.getSerializable("lekua") != null) {
                lekua = (Lekua) bundle.getSerializable("lekua");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText etIzena = view.findViewById(R.id.editTextNombre);
        EditText etDeskribapena = view.findViewById(R.id.editTextDescripcion);
        EditText etLatitude = view.findViewById(R.id.editTextLatitud);
        EditText etLongitude = view.findViewById(R.id.editTextLongitud);
        Button btnGorde = view.findViewById(R.id.btnGorde);
        Button btnAtzera = view.findViewById(R.id.btnAtzera);

        btnAtzera.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        if (lekua != null) {
            activity.getSupportActionBar().setTitle(R.string.aldatuTitulua);
            etIzena.setText(lekua.getIzena());
            etDeskribapena.setText(lekua.getDeskribapena());
            etLatitude.setText(String.valueOf(lekua.getLatitude()));
            etLongitude.setText(String.valueOf(lekua.getLongitude()));

            btnGorde.setOnClickListener(v -> {
                Lekua lekuaBerria = new Lekua();
                lekuaBerria.setIzena(etIzena.getText().toString());
                lekuaBerria.setDeskribapena(etDeskribapena.getText().toString());
                lekuaBerria.setLatitude(Double.parseDouble(etLatitude.getText().toString()));
                lekuaBerria.setLongitude(Double.parseDouble(etLongitude.getText().toString()));

                //Validaciones
                if (lekua.getIzena().isEmpty() && lekua.getDeskribapena().isEmpty() && etLatitude.getText().toString().isEmpty() && etLongitude.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Datu guztiak sartu behar dira", Toast.LENGTH_SHORT);
                    return;
                }

                if (lekua.getIzena().isEmpty()) {
                    etIzena.setError("Izena ezin da hutsik egon");
                    return;
                }

                if (lekua.getDeskribapena().isEmpty()) {
                    etDeskribapena.setError("Deskribapena ezin da hutsik egon");
                    return;
                }

                if (etLatitude.getText().toString().isEmpty()) {
                    etLatitude.setError("Latitudea ezin da hutsik egon");
                    return;
                }

                if (etLongitude.getText().toString().isEmpty()) {
                    etLongitude.setError("Longitudea ezin da hutsik egon");
                    return;
                }

                if (lekua.getLatitude() < -90 || lekua.getLatitude() > 90) {
                    etLatitude.setError("Latitudea -90 eta 90 artean egon behar da");
                    return;
                }

                if (lekua.getLongitude() < -180 || lekua.getLongitude() > 180) {
                    etLongitude.setError("Longitudea -180 eta 180 artean egon behar da");
                    return;
                }

                LekuaDAO lekuaDAO = new LekuaDAO(requireContext());
                int is_update = lekuaDAO.eguneratuLekua(lekua);

                if (is_update > 0) {
                    Toast.makeText(requireContext(), "Datuak eguneratu dira", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Datuak ezin izan dira eguneratu", Toast.LENGTH_SHORT).show();
                }

                getActivity().getSupportFragmentManager().popBackStack();
            });


        } else {
            activity.getSupportActionBar().setTitle(R.string.gehituTitulua);

            btnGorde.setOnClickListener(v -> {
                Lekua lekuaBerria = new Lekua();
                lekuaBerria.setIzena(etIzena.getText().toString());
                lekuaBerria.setDeskribapena(etDeskribapena.getText().toString());
                lekuaBerria.setLatitude(Double.parseDouble(etLatitude.getText().toString()));
                lekuaBerria.setLongitude(Double.parseDouble(etLongitude.getText().toString()));

                //Validaciones
                if (lekuaBerria.getIzena().isEmpty() && lekuaBerria.getDeskribapena().isEmpty() && etLatitude.getText().toString().isEmpty() && etLongitude.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Datu guztiak sartu behar dira", Toast.LENGTH_SHORT);
                    return;
                }

                if (lekuaBerria.getIzena().isEmpty()) {
                    etIzena.setError("Izena ezin da hutsik egon");
                    return;
                }

                if (lekuaBerria.getDeskribapena().isEmpty()) {
                    etDeskribapena.setError("Deskribapena ezin da hutsik egon");
                    return;
                }

                if (etLatitude.getText().toString().isEmpty()) {
                    etLatitude.setError("Latitudea ezin da hutsik egon");
                    return;
                }

                if (etLongitude.getText().toString().isEmpty()) {
                    etLongitude.setError("Longitudea ezin da hutsik egon");
                    return;
                }

                if (lekuaBerria.getLatitude() < -90 || lekuaBerria.getLatitude() > 90) {
                    etLatitude.setError("Latitudea -90 eta 90 artean egon behar da");
                    return;
                }

                if (lekuaBerria.getLongitude() < -180 || lekuaBerria.getLongitude() > 180) {
                    etLongitude.setError("Longitudea -180 eta 180 artean egon behar da");
                    return;
                }

                LekuaDAO lekuaDAO = new LekuaDAO(requireContext());
                long id = lekuaDAO.gehituLekua(lekuaBerria);

                if (id > 0) {
                    Toast.makeText(requireContext(), "Datuak gorde dira", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(requireContext(), "Datuak ezin izan dira gorde", Toast.LENGTH_SHORT).show();
                }
                getActivity().getSupportFragmentManager().popBackStack();


            });


        }


        return view;
    }
}