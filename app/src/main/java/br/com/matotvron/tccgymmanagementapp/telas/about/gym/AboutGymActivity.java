package br.com.matotvron.tccgymmanagementapp.telas.about.gym;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import br.com.matotvron.tccgymmanagementapp.background.models.Gym;
import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.gym.GetGymDataTask;
import br.com.matotvron.tccgymmanagementapp.telas.LoginActivity;

public class AboutGymActivity extends AppCompatActivity {

    private TextView tvGymIdAbout, tvGymNameAbout, tvGymDocumentAbout,
    tvGymPhoneAbout, tvGymEmailAbout, tvGymAddresAbout;
    private Gym gym;
    private SwipeRefreshLayout swpRefreshGym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_gym);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbar_about_gym_act));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapearVariaveis();
        swpRefreshGym.setOnRefreshListener(this::obtainDataGym);

        obtainDataGym();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void mapearVariaveis(){
        tvGymIdAbout = findViewById(R.id.tv_gym_id_about);
        tvGymNameAbout = findViewById(R.id.tv_gym_name_about);
        tvGymDocumentAbout = findViewById(R.id.tv_gym_document_about);
        tvGymPhoneAbout = findViewById(R.id.tv_gym_phone_about);
        tvGymEmailAbout = findViewById(R.id.tv_gym_email_about);
        tvGymAddresAbout = findViewById(R.id.tv_gym_address_about);
        swpRefreshGym = findViewById(R.id.swp_refresh_gym);
    }

    private void obtainDataGym(){
        gym = new DefaultPreferences<Gym>(this).obterPreference(PreferencesMap.PREF_GYM_OBJ, Gym.class);
        new GetGymDataTask(this, gym.getId()){
            @Override
            protected void preExecuteBackground() {
                super.preExecuteBackground();
                findViewById(R.id.ll_content_gym).setVisibility(View.GONE);
                findViewById(R.id.ll_loading_gym).setVisibility(View.VISIBLE);
            }

            @Override
            protected void postExecuteBackground(TaskResults taskResults) {
                super.postExecuteBackground(taskResults);
                if(swpRefreshGym.isRefreshing())
                    swpRefreshGym.setRefreshing(false);
                findViewById(R.id.ll_content_gym).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_loading_gym).setVisibility(View.GONE);

                if(taskResults == TaskResults.GYM_NOT_FOUND){
                    new DefaultPreferences<User>(context).apagarPreferences(PreferencesMap.PREF_USER_OBJ);
                    new DefaultPreferences<Gym>(context).apagarPreferences(PreferencesMap.PREF_GYM_OBJ);
                }
                atribuirValores();
            }
        }.execute();
    }

    private void atribuirValores(){
        gym = new DefaultPreferences<Gym>(this).obterPreference(PreferencesMap.PREF_GYM_OBJ, Gym.class);
        if(gym == null || gym.getId() == null || gym.getId().isEmpty()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            Toast.makeText(this, "Academia não encontrada, Login inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = "#"+ gym.getId().substring(gym.getId().length()-4).toUpperCase();
        tvGymIdAbout.setText(id);
        tvGymNameAbout.setText(gym.getName());
        tvGymDocumentAbout.setText(gym.getDocument());
        tvGymPhoneAbout.setText(gym.getPhoneNumber());
        tvGymEmailAbout.setText(gym.getEmail());
        tvGymAddresAbout.setText(gym.getAddress());
    }
}