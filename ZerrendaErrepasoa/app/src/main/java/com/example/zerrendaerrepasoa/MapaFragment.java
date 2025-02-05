package com.example.zerrendaerrepasoa;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class MapaFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private MapView mapaIkuspegia;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.getSupportActionBar().setTitle("Mapa");
        Bundle bundle = getArguments();
        Lekua lekua = (Lekua) bundle.getSerializable("lekua");

        Configuration.getInstance().setUserAgentValue(requireActivity().getPackageName());

        mapaIkuspegia = view.findViewById(R.id.mapa);
        mapaIkuspegia.setTileSource(TileSourceFactory.MAPNIK);
        mapaIkuspegia.setBuiltInZoomControls(true);
        mapaIkuspegia.setMultiTouchControls(true);
        mapaIkuspegia.getController().setZoom(15.0);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        if (lekua != null) {
            GeoPoint lekuaPunto = new GeoPoint(lekua.getLatitude(), lekua.getLongitude());
            markadoreaEzarri(lekuaPunto, lekua);
            mapaIkuspegia.getController().setCenter(lekuaPunto);
        } else {
            ubikazioaEskatu();
        }

        return view;
    }

    private void ubikazioaEskatu() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            GeoPoint userLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                            markadoreaEzarri(userLocation, new Lekua(0, "Hemen nago", "Zure kokapena", location.getLatitude(), location.getLongitude()));
                            mapaIkuspegia.getController().setCenter(userLocation);
                        } else {
                            Log.i("BilerakDetailsFragment", "No se pudo obtener la ubicación.");
                        }
                    }
                });
    }

    private void markadoreaEzarri(GeoPoint punto, Lekua lekua) {
        Marker marcador = new Marker(mapaIkuspegia);
        marcador.setPosition(punto);
        marcador.setTitle(lekua.getIzena());
        marcador.setSnippet(lekua.getDeskribapena());
        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapaIkuspegia.getOverlays().add(marcador);
        mapaIkuspegia.invalidate();
    }
}