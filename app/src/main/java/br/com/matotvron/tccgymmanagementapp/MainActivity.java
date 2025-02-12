package br.com.matotvron.tccgymmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.matotvron.tccgymmanagementapp.telas.settings.ConfiguracoesActivity;
import br.com.matotvron.tccgymmanagementapp.telas.debug.DebugActivity;

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

        btnDebugScreen = findViewById(R.id.btnDebugScreen);
        settingsBtn = findViewById(R.id.settingsBtn);

        if(BuildConfig.DEV){
            AlertDialog dialog = new AlertDialog.Builder(this
            ).setMessage("Aplicativo em modo DEV!")
                    .setNeutralButton("Ok",(dialog1, which) -> {}).create();
            dialog.show();
        }

        btnDebugScreen.setOnClickListener((v) -> startActivity(new Intent(getApplicationContext(), DebugActivity.class)));
        settingsBtn.setOnClickListener((v) -> startActivity(new Intent(getApplicationContext(), ConfiguracoesActivity.class)));
    }
}