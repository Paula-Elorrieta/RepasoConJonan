package com.example.zerrendasqllite;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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


public class AldatuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aldatu, container, false);
        Bundle bundle = getArguments();
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.getSupportActionBar().setTitle(R.string.aldatuTitulua);

        Lenguaia lenguaia = (Lenguaia) bundle.getSerializable("lenguaia");
        ArrayList lenguaiak = (ArrayList) bundle.getSerializable("lenguaiak");

        EditText etLenguaiaIzena = view.findViewById(R.id.etLenguaiaIzena);
        EditText etLenguaiaDeskribapena = view.findViewById(R.id.etLenguaiaDeskribapena);
        Spinner spLibrea = view.findViewById(R.id.spinnerLenguaia);
        String[] libreItems = getResources().getStringArray(R.array.librea);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, libreItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLibrea.setAdapter(adapter);

        etLenguaiaIzena.setText(lenguaia.getIzena());
        etLenguaiaDeskribapena.setText(lenguaia.getDeskribapena());
        spLibrea.setSelection(lenguaia.isLibrea() ? 0 : 1);

        Button btnGorde = view.findViewById(R.id.btnGorde);
        Button btnAtzera = view.findViewById(R.id.btnAtzera);

        btnAtzera.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        btnGorde.setOnClickListener(v -> {
            String izena = etLenguaiaIzena.getText().toString();
            String deskribapena = etLenguaiaDeskribapena.getText().toString();
            String librea = spLibrea.getSelectedItem().toString();

            if (izena.isEmpty() || deskribapena.isEmpty()) {
                Toast.makeText(requireContext(), "Ezin da hutsik utzi",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (izena.isEmpty()) {
                etLenguaiaIzena.setError("Izena ezin da hutsik egon");
                return;
            }

            if (deskribapena.isEmpty()) {
                etLenguaiaDeskribapena.setError("Deskribapena ezin da hutsik egon");
                return;
            }

            boolean libreaBol;
            if (librea.equals(spLibrea.getItemAtPosition(0))) {
                libreaBol = true;
            } else {
                libreaBol = false;
            }

            Lenguaia l = new Lenguaia(izena, deskribapena, libreaBol);
            int id = l.getID(lenguaia, lenguaiak);

            AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                    .setTitle("Aldatu lengoaia")
                    .setMessage("Ziur zaude aldatu nahi duzula?")
                    .setPositiveButton("Bai", (dialog, which) -> {
                        ZerrendaDAO zerrendaDAO = new ZerrendaDAO(requireContext());
                        zerrendaDAO.eguneratuLengoaia(id, izena, deskribapena, libreaBol);
                        getActivity().getSupportFragmentManager().popBackStack();
                    })
                    .setNegativeButton("Ez", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create();
            alertDialog.show();
        });


        return view;
    }
}