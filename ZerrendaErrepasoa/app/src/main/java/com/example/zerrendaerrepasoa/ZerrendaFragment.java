package com.example.zerrendaerrepasoa;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;


public class ZerrendaFragment extends Fragment {

    private ArrayList<Lekua> lekuak;
    private ZerrendaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zerrenda, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.getSupportActionBar().setTitle("Zerrenda");

        ListView listView = view.findViewById(R.id.listView);
        LekuaDAO lekuaDAO = new LekuaDAO(requireContext());
        SearchView searchView = view.findViewById(R.id.searchView);
        lekuak = lekuaDAO.getLekuak();

        if (lekuak.isEmpty()) {
            lekuaDAO.gehituLekua(new Lekua(1, "Eiffel Tower", "Famous tower in Paris", 48.8584, 2.2945));
            lekuaDAO.gehituLekua(new Lekua(2, "Statue of Liberty", "Iconic statue in New York", 40.6892, -74.0445));
            lekuaDAO.gehituLekua(new Lekua(3, "Great Wall of China", "Ancient wall in China", 40.4319, 116.5704));
            lekuaDAO.gehituLekua(new Lekua(4, "Machu Picchu", "Historic Incan site in Peru", -13.1631, -72.5450));
            lekuaDAO.gehituLekua(new Lekua(5, "Taj Mahal", "Famous mausoleum in India", 27.1751, 78.0421));
            lekuak = lekuaDAO.getLekuak();
        }

        // A la hora del hacer el filtrado
        adapter = new ZerrendaAdapter(requireContext(), new ArrayList<>(lekuak));
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtraketaEgin(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtraketaEgin(newText);
                return true;
            }
        });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Lekua lekua = (Lekua) parent.getItemAtPosition(position);
            LayoutInflater inflater1 = LayoutInflater.from(requireContext());
            View dialogView = inflater1.inflate(R.layout.dialog_lenguaia, null);

            Button btnAldatu = dialogView.findViewById(R.id.btnAldatu);
            Button btnEzabatu = dialogView.findViewById(R.id.btnEzabatu);
            Button btnMapa = dialogView.findViewById(R.id.btnMapa);

            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .create();

            btnEzabatu.setOnClickListener(v -> {
                AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                        .setTitle("Ezabatu")
                        .setMessage("Ziur zaude ezabatu nahi duzula?")
                        .setPositiveButton("Bai", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                int id = (int) lekua.getId();
                                lekuaDAO.ezabatuLekua(id);
                                adapter.remove(lekua);
                                dialogInterface.dismiss();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Ez", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            });

            btnAldatu.setOnClickListener(v -> {
                UpdateCreateFragment fragment = new UpdateCreateFragment();
                Bundle bundle = new Bundle();

                if (lekua != null) {
                    bundle.putSerializable("lekua", lekua);
                } else {
                    bundle.putSerializable("lekua", null);
                }

                bundle.putSerializable("lekuak", (Serializable) lekuak);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit();
                dialog.dismiss();
            });

            btnMapa.setOnClickListener(v -> {
                MapaFragment fragment = new MapaFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("lekua", lekua);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .addToBackStack(null)
                        .commit();
                dialog.dismiss();
            });

            dialog.show();

        });


        return view;

    }

    private void filtraketaEgin(String izena) {
        ArrayList<Lekua> filtrados;

        if (izena.isEmpty()) {
            filtrados = new ArrayList<>(lekuak);
        } else {
            filtrados = new ArrayList<>();
            for (Lekua lenguaia : lekuak) {
                if (lenguaia.getIzena().toLowerCase().contains(izena.toLowerCase())) {
                    filtrados.add(lenguaia);
                }
            }
        }
        adapter.clear();
        adapter.addAll(filtrados);
        adapter.notifyDataSetChanged();
    }


}