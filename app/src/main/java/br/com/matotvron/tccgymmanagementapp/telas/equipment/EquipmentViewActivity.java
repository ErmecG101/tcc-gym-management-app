package br.com.matotvron.tccgymmanagementapp.telas.equipment;

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

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;

public class EquipmentViewActivity extends AppCompatActivity {

    private TextView tvEquipName, tvEquipPropertyNumber, tvEquipType,
            tvEquipDescription, tvEquipPurchaseDate, tvEquipOriginalValue, tvEquipCurrentValue,
            tvEquipDepreciationPercentage, tvEquipDurability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_equipment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (!getIntent().hasExtra("equipment")) {
            Toast.makeText(this, this.getString(R.string.equip_selected_not_found_toast), Toast.LENGTH_SHORT).show();
            finish();
        }

        setSupportActionBar(findViewById(R.id.toolbar_view_equipment));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assignFieldObjVariables();
        assignValues();


    }
    private void assignValues(){
        Equipment equipment = new Gson().fromJson(getIntent().getStringExtra("equipment"), Equipment.class);

        tvEquipName.setText(equipment.getName());
        tvEquipPropertyNumber.setText(equipment.getPropertyNumber());
        tvEquipType.setText(equipment.getEquipmentType().getName());
        tvEquipDescription .setText(equipment.getDescription());
        tvEquipPurchaseDate.setText(String.format(this.getString(R.string.equip_date_of_acquistion_view),new SimpleDateFormat("dd/MM/yyyy", getResources().getConfiguration().getLocales().get(0)).format(equipment.getPurchaseDate())));
        tvEquipOriginalValue.setText(String.format(this.getString(R.string.equip_original_value_view),equipment.getOriginalValue()));
        tvEquipCurrentValue.setText(String.format(this.getString(R.string.equip_current_value_view),equipment.getCurrentValue()));
        tvEquipDepreciationPercentage.setText(String.format(this.getString(R.string.equip_depreciation_view), equipment.getDepreciationPercentage()));
        tvEquipDurability.setText(String.format(this.getString(R.string.equip_durability_view), equipment.getDurability()));
    }

    private void assignFieldObjVariables() {
        tvEquipName = findViewById(R.id.tv_equip_name);
        tvEquipPropertyNumber = findViewById(R.id.tv_equip_property_number);
        tvEquipType = findViewById(R.id.tv_equip_type);
        tvEquipDescription = findViewById(R.id.tv_equip_description);
        tvEquipPurchaseDate = findViewById(R.id.tv_equip_purchase_date);
        tvEquipOriginalValue = findViewById(R.id.tv_equip_original_value);
        tvEquipCurrentValue = findViewById(R.id.tv_equip_current_value);
        tvEquipDepreciationPercentage = findViewById(R.id.tv_equip_depreciation_percentage);
        tvEquipDurability = findViewById(R.id.tv_equip_durability);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}