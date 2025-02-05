package com.example.zerrendaerrepasoa;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new ZerrendaFragment())
                    .commitNow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.itxi) {
//            Fragment currentFragment = getSupportFragmentManager()
//                    .findFragmentById(R.id.fragmentContainerView);
//
//            // instanceof es un operador que comprueba si un objeto es una instancia de una clase
//            // Asi no se cierra la aplicacion si no se esta en el fragmento principal
//            if (currentFragment instanceof LehenengoFragment) {
//                LehenengoFragment fragment = (LehenengoFragment) currentFragment;
//                fragment.itxi();
//            }
//            return true;

        } else if (id == R.id.gehituMenua) {
            UpdateCreateFragment gehituFragment = new UpdateCreateFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("lekuak", null);
            bundle.putSerializable("lekua", null);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, gehituFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
//        } else if (id == R.id.bilatu) {
//            LinearLayout searchContainer = findViewById(R.id.searchContainer);
//            searchContainer.setVisibility(searchContainer.getVisibility() == LinearLayout.VISIBLE ?
//                    LinearLayout.GONE : LinearLayout.VISIBLE);
//            return true;
//        } else if (id == R.id.hizkuntza) {
//            if (!Locale.getDefault().getLanguage().equals("es")) {
//                aldatuHizkuntza("es");
//            } else {
//                aldatuHizkuntza("eu");
//            }
//            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void aldatuHizkuntza(String idioma) {
        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);
        Configuration config = new Configuration();

        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        recreate();
        Toast.makeText(this, "Hizkuntza aldatuta: " + idioma, Toast.LENGTH_SHORT).show();
    }


}