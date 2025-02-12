package br.com.matotvron.tccgymmanagementapp.telas.settings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DebugPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;

public class DebugSettingsActivity extends AppCompatActivity {

    private EditText editIpServidor;
    private Button btnSalvarServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_debug_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DebugPreferences preferences = new DebugPreferences(this);
        editIpServidor = findViewById(R.id.editIpServidor);
        btnSalvarServidor = findViewById(R.id.btnSalvarServidor);
        editIpServidor.setText(preferences.obterPreference(PreferencesMap.PREF_DEBUG_IP));
        btnSalvarServidor.setOnClickListener((v) -> {
            String text = editIpServidor.getText().toString();
            if(text.isEmpty() || text.isBlank()){
                AlertDialog aviso = new AlertDialog.Builder(this)
                        .setMessage("Por favor, digite um IP").setNeutralButton("Ok", null).create();
                aviso.show();
                return;
            }
            preferences.salvar(PreferencesMap.PREF_DEBUG_IP, text);
            Toast.makeText(this, "Ip configurado com sucesso!", Toast.LENGTH_SHORT).show();
        });

    }
}