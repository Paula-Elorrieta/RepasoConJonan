package com.example.zerrendasqllite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class LehenengoFragment extends Fragment {

    private ZerrendaAdapter adapter;
    private List<Lenguaia> lenguaiaList;
    private ArrayList<Lenguaia> lenguaiakFiltratuta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lehenengo, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.getSupportActionBar().setTitle(R.string.lenguaiaTitulua);

        ListView listView = view.findViewById(R.id.listView);
        LinearLayout searchContainer = view.findViewById(R.id.searchContainer);
        SearchView searchView = view.findViewById(R.id.searchView);

        ZerrendaDAO zerrendaDAO = new ZerrendaDAO(requireContext());
        lenguaiaList = zerrendaDAO.lortuLengoaiak();

        if (lenguaiaList.isEmpty()) {
            zerrendaDAO.gehituLengoaia("Java", "Java lengoaia", false);

        }

        adapter = new ZerrendaAdapter(requireContext(), new ArrayList<>(lenguaiaList));
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
            Lenguaia lenguaia = (Lenguaia) parent.getItemAtPosition(position);
            LayoutInflater inflater1 = LayoutInflater.from(requireContext());
            View dialogView = inflater1.inflate(R.layout.dialog_lenguaia, null);

            Button btnAldatu = dialogView.findViewById(R.id.btnAldatu);
            Button btnEzabatu = dialogView.findViewById(R.id.btnEzabatu);

            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .create();

            btnEzabatu.setOnClickListener(v -> {
                AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                        .setTitle("Ezabatu")
                        .setMessage("Ziur zaude ezabatu nahi duzula?")
                        .setPositiveButton("Bai", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = lenguaia.getID(lenguaia, lenguaiaList);
                                zerrendaDAO.ezabatuLengoaia(id);
                                adapter.remove(lenguaia);
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
                AldatuFragment fragment = new AldatuFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("lenguaia", lenguaia);
                bundle.putSerializable("lenguaiak", (Serializable) lenguaiaList);
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


    public void itxi() {
        requireActivity().finish();
    }

    private void filtraketaEgin(String izena) {
        ArrayList<Lenguaia> filtrados;

        if (izena.isEmpty()) {
            filtrados = new ArrayList<>(lenguaiaList);
        } else {
            filtrados = new ArrayList<>();
            for (Lenguaia lenguaia : lenguaiaList) {
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