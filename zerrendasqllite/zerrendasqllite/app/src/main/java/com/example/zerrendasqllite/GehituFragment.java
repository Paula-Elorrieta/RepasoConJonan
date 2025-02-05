package com.example.zerrendasqllite;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class GehituFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gehitu, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.getSupportActionBar().setTitle(R.string.gehituTitulua);

        TextView editTextNombre = view.findViewById(R.id.editTextNombre);
        TextView editTextDescripcion = view.findViewById(R.id.editTextDescripcion);

        Spinner spinnerLibre = view.findViewById(R.id.spinnerLibre);
        String[] libreItems = getResources().getStringArray(R.array.librea);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, libreItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLibre.setAdapter(adapter);

        Button buttonGehitu = view.findViewById(R.id.buttonGehitu);
        Button buttonItzuli = view.findViewById(R.id.buttonItzuli);

        buttonItzuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        buttonGehitu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = editTextNombre.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String libre = spinnerLibre.getSelectedItem().toString();
                Boolean librea;

                if (nombre.isEmpty() || descripcion.isEmpty()) {
                    Toast.makeText(requireContext(), "Ezin da hutsik utzi",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nombre.isEmpty()) {
                    editTextNombre.setError("Izena ezin da hutsik utzi");
                    return;
                }

                if (descripcion.isEmpty()) {
                    editTextDescripcion.setError("Deskribapena ezin da hutsik utzi");
                    return;
                }

                if (libre.equals(spinnerLibre.getItemAtPosition(0))) {
                    librea = true;
                } else {
                    librea = false;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Sortu nahiko duzu lengoaia hau?");
                builder.setPositiveButton("Bai", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ZerrendaDAO zerrendaDAO = new ZerrendaDAO(requireContext());
                        long id = zerrendaDAO.gehituLengoaia(nombre, descripcion, librea);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });

                builder.setNegativeButton("Ez", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "Ez da gehitu", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

            }
        });


        return view;
    }
}