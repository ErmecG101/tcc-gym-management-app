package br.com.matotvron.tccgymmanagementapp.telas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.users.LoginTask;

public class LoginActivity extends AppCompatActivity {
    
    private Button btnLogin;
    private TextInputLayout userLayout, passLayout;
    private TextInputEditText userTextInput, passTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        btnLogin = findViewById(R.id.btnLogin);

        userLayout = findViewById(R.id.tiUser);
        passLayout = findViewById(R.id.tiPass);

        userTextInput = findViewById(R.id.tieUser);
        passTextInput = findViewById(R.id.tiePass);
        
        btnLogin.setOnClickListener((v) -> {
            if(userTextInput.getText() == null || userTextInput.getText().toString().isEmpty()){
                userLayout.setError("Campo não pode ser vazio!");
            }else{
                userLayout.setError(null);
            }
            if(passTextInput.getText() == null || passTextInput.getText().toString().isEmpty()){
                passLayout.setError("Campo não pode ser vazio!");
            }else{
                passLayout.setError(null);
            }
            
            if(passLayout.getError() != null || userLayout.getError() != null){
                Toast.makeText(this, "Por favor, preencha os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginTask task = new LoginTask(this, userTextInput.getText().toString(), passTextInput.getText().toString()){
                @Override
                protected void postExecuteBackground(TaskResults taskResults) {
                    super.postExecuteBackground(taskResults);
                    if(taskResults == TaskResults.SUCCESS){
                        startActivity(new Intent(context, PrincipalActivity.class));
                    }else if(taskResults == TaskResults.WRONG_CREDENTIALS){
                        Toast.makeText(context, "Credenciais erradas.", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            task.execute();
        });
    }

    private void validateFields(){

    }
}