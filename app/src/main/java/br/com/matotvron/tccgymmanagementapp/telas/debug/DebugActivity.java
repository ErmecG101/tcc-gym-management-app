package br.com.matotvron.tccgymmanagementapp.telas.debug;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.users.GetAllUsersTask;
import br.com.matotvron.tccgymmanagementapp.telas.LoginActivity;

public class DebugActivity extends AppCompatActivity {

    private Button btnTestRequest, btnLoginScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_debug);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnTestRequest = findViewById(R.id.btnTestRequest);
        btnLoginScreen = findViewById(R.id.btnLoginScreen);

        btnTestRequest.setOnClickListener((v) -> {
            Toast.makeText(this, "Request enviada!", Toast.LENGTH_SHORT).show();
            GetAllUsersTask task = new GetAllUsersTask(this){
                @Override
                protected void postExecuteBackground(TaskResults taskResults) {
                    super.postExecuteBackground(taskResults);
                    
                    if(taskResults == TaskResults.SUCCESS){
                        Log.d("Teste", "Valor: "+bodyJson);
                        Toast.makeText(context, "Request returned Sucessfully!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Sorry, it seems like something went wrong.", Toast.LENGTH_SHORT).show();
                        Log.d("Teste", "Resultado: "+taskResults.name());
                    }
                }
            };
            task.execute();
        });

        btnLoginScreen.setOnClickListener((v) -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }
}