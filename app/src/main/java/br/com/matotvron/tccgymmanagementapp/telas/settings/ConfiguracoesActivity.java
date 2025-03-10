package br.com.matotvron.tccgymmanagementapp.telas.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.matotvron.tccgymmanagementapp.R;

public class ConfiguracoesActivity extends AppCompatActivity {

    private CardView cvConfigDebug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracoes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cvConfigDebug = findViewById(R.id.cvConfigDebug);

        cvConfigDebug.setOnClickListener((v) -> startActivity(new Intent(this, DebugSettingsActivity.class)));
    }
}