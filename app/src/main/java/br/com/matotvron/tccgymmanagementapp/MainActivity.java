package br.com.matotvron.tccgymmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.telas.LoginActivity;
import br.com.matotvron.tccgymmanagementapp.telas.PrincipalActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnDebugScreen;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SplashScreen.installSplashScreen(this);

        EdgeToEdge.enable(this);

        DefaultPreferences<User> uPref = new DefaultPreferences<>(this);
        User usuario = uPref.obterPreference(PreferencesMap.PREF_USER_OBJ, User.class);
        Intent intentTelaSeguinte;
        if(usuario.getId() == null || usuario.getId().isEmpty()){
            intentTelaSeguinte = new Intent(this, LoginActivity.class);

        }else{
            intentTelaSeguinte = new Intent(this, PrincipalActivity.class);
        }

        startActivity(intentTelaSeguinte);
        finish();

//        btnDebugScreen = findViewById(R.id.btnDebugScreen);
//        settingsBtn = findViewById(R.id.settingsBtn);
//
//        btnDebugScreen.setOnClickListener((v) -> startActivity(new Intent(getApplicationContext(), DebugActivity.class)));
//        settingsBtn.setOnClickListener((v) -> startActivity(new Intent(getApplicationContext(), ConfiguracoesActivity.class)));
    }
}