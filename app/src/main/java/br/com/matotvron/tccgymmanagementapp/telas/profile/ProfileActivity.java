package br.com.matotvron.tccgymmanagementapp.telas.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.users.GetUserDataTask;
import br.com.matotvron.tccgymmanagementapp.telas.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsuNome, tvUsu4UltCod, tvUsuEmail, tvUsuPhone, tvUsuGymName;
    private SwipeRefreshLayout swpRefreshProfile;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbar_profile_act));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapearVariaveis();
        atribuirValores();

        swpRefreshProfile.setOnRefreshListener(() -> {
            new GetUserDataTask(this, user.getId()){
                @Override
                protected void preExecuteBackground() {
                    super.preExecuteBackground();
                    findViewById(R.id.ll_content_profile).setVisibility(TextView.GONE);
                    findViewById(R.id.ll_loading_profile).setVisibility(TextView.VISIBLE);
                }

                @Override
                protected void postExecuteBackground(TaskResults taskResults) {
                    super.postExecuteBackground(taskResults);
                    swpRefreshProfile.setRefreshing(false);
                    findViewById(R.id.ll_content_profile).setVisibility(TextView.VISIBLE);
                    findViewById(R.id.ll_loading_profile).setVisibility(TextView.GONE);
                    atribuirValores();
                }
            }.execute();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void mapearVariaveis(){
        tvUsuNome = findViewById(R.id.tv_usu_name);
        tvUsu4UltCod = findViewById(R.id.tv_usu_4_digits_code);
        tvUsuEmail = findViewById(R.id.tv_usu_email);
        tvUsuPhone = findViewById(R.id.tv_usu_phone);
        tvUsuGymName = findViewById(R.id.tv_usu_gym_dto_name);
        swpRefreshProfile = findViewById(R.id.swp_refresh_profile);
    }

    private void atribuirValores(){

        user = new DefaultPreferences<User>(this).obterPreference(PreferencesMap.PREF_USER_OBJ, User.class);
        if(user == null || user.getId() == null || user.getId().isEmpty()){
            Toast.makeText(this, "Erro, login inválido.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        tvUsuNome.setText(user.getName());
        String strBuilder = "#" +
                user.getId().substring(user.getId().length() - 4).toUpperCase();
        tvUsu4UltCod.setText(strBuilder);
        tvUsuEmail.setText(user.getEmail());
        tvUsuPhone.setText(user.getPhoneNumber());
        tvUsuGymName.setText(user.getGymDTO().getName());
    }
}