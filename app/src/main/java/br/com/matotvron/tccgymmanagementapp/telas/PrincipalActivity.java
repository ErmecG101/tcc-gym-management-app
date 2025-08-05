package br.com.matotvron.tccgymmanagementapp.telas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Gym;
import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.viewpager.PrincipalActivityViewPager;
import br.com.matotvron.tccgymmanagementapp.telas.about.gym.AboutGymActivity;
import br.com.matotvron.tccgymmanagementapp.telas.about.us.AboutUsActivity;
import br.com.matotvron.tccgymmanagementapp.telas.profile.ProfileActivity;
import br.com.matotvron.tccgymmanagementapp.telas.settings.ConfiguracoesActivity;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ViewPager2 viewPagerPrincipal;
    private TabLayout tlPrincipalItems;
    private PrincipalActivityViewPager viewPagerAdapter;
    private Toolbar toolbarPrincipalActivity;

    final private String[] tabTitles = {"Inicial", "Equipamentos", "Pedidos"};
    final int[] tabIcons = {R.drawable.baseline_home_24, R.drawable.baseline_view_stream_24, R.drawable.baseline_insert_drive_file_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbarPrincipalActivity = findViewById(R.id.toolbarPrincipalActivity);
        setSupportActionBar(toolbarPrincipalActivity);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarPrincipalActivity, R.string.default_exception_description, R.string.default_exception_title);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView tvGymName = headerView.findViewById(R.id.tv_gym_name);
        tvGymName.setText(new DefaultPreferences<Gym>(this).obterPreference(PreferencesMap.PREF_GYM_OBJ, Gym.class).getName());

        viewPagerPrincipal = findViewById(R.id.viewPagerPrincipal);
        tlPrincipalItems = findViewById(R.id.tlPrincipalItems);

        viewPagerAdapter = new PrincipalActivityViewPager(this);
        viewPagerPrincipal.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tlPrincipalItems, viewPagerPrincipal,
                (tab, position) -> {
                    tab.setText(tabTitles[position]);
                    tab.setIcon(tabIcons[position]);
                }).attach();

        findViewById(R.id.btn_principal_aboutus).setOnClickListener((v) -> startActivity(new Intent(this, AboutUsActivity.class)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == android.R.id.home){
            Toast.makeText(this, "On Menu Up Clicked!", Toast.LENGTH_SHORT).show();
            return true;
        }else if(itemId == R.id.settingsMenuItemToolBar){
            startActivity(new Intent(this, ConfiguracoesActivity.class));
            return true;
        }else if(itemId == R.id.exitMenuItemToolBar){
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.aboutGymMenuItem){
            startActivity(new Intent(this, AboutGymActivity.class));
            return true;
        }else if(id == R.id.profileMenuItem){
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }else if(id == R.id.settingsMenuItem){
            startActivity(new Intent(this, ConfiguracoesActivity.class));
            return true;
        }else if(id == R.id.logOffMenuItem){
            logout();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        try{
            DefaultPreferences<User> userPreferences = new DefaultPreferences<>(this);
            userPreferences.apagarPreferences(PreferencesMap.PREF_USER_OBJ);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}