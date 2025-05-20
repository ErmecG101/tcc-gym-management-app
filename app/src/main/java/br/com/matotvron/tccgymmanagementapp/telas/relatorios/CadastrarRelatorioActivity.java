package br.com.matotvron.tccgymmanagementapp.telas.relatorios;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;
import br.com.matotvron.tccgymmanagementapp.background.models.MaintenanceRequest;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.maintenancerequest.SubmitMaintenanceRequestTask;
import br.com.matotvron.tccgymmanagementapp.telas.adapter.EquipmentReducedSelectableAdapter;

public class CadastrarRelatorioActivity extends AppCompatActivity {

    private SearchView svSelectedEquipments;
    private RecyclerView rvAddedEquipments;
    private Button addBtn;
    private FloatingActionButton fabSubmit;
    private EquipmentReducedSelectableAdapter adapter;
    private TextView tvNoEquipmentsAdded;
    private EditText editTextObservation, editTextDescription;

    private final ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode() == CadastrarRelatorioActivity.RESULT_OK){
                if(o.getData() != null && o.getData().hasExtra("currAddedEquipList")){
                    Type listType = new TypeToken<List<Equipment>>(){}.getType();
                    String selectedToAddList = o.getData().getStringExtra("currAddedEquipList");
                    Gson g = new Gson();
                    adapter.clearList();
                    adapter.addItems(g.fromJson(selectedToAddList, listType));
                    if(adapter.getEquipments() == null || adapter.getEquipments().isEmpty()) {
                        rvAddedEquipments.setVisibility(GONE);
                        tvNoEquipmentsAdded.setVisibility(VISIBLE);
                    }else{
                        rvAddedEquipments.setVisibility(VISIBLE);
                        tvNoEquipmentsAdded.setVisibility(GONE);
                    }
                }else{
                    Toast.makeText(CadastrarRelatorioActivity.this, "No equipments selected.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CadastrarRelatorioActivity.this, "Erro ao adicionar equipamentos.", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_relatorio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(findViewById(R.id.toolbar_create_report));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        svSelectedEquipments = findViewById(R.id.search_view_selected_equips);
        tvNoEquipmentsAdded = findViewById(R.id.tv_no_equipments_added_label);
        editTextObservation = findViewById(R.id.edit_text_observation);
        editTextDescription = findViewById(R.id.edit_text_report_description);
        fabSubmit = findViewById(R.id.fab_submit);
        addBtn = findViewById(R.id.btn_add_equipments);
        rvAddedEquipments = findViewById(R.id.rv_added_equipments);
        rvAddedEquipments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new EquipmentReducedSelectableAdapter(this, new ArrayList<>(), false);
        rvAddedEquipments.setAdapter(adapter);
        svSelectedEquipments.setOnSearchClickListener((v) -> {
            findViewById(R.id.tv_selected_equips_title).setVisibility(GONE);
            findViewById(R.id.btn_add_equipments).setVisibility(GONE);
        });

        svSelectedEquipments.setOnCloseListener(() -> {
            findViewById(R.id.tv_selected_equips_title).setVisibility(VISIBLE);
            findViewById(R.id.btn_add_equipments).setVisibility(VISIBLE);
            return false;
        });



        addBtn.setOnClickListener((v) -> {
            String addedEquipListJson = new Gson().toJson(adapter.getEquipments());
            Intent i = new Intent(this, SelecionarEquipamentosActivity.class);
            Log.d("AAAB", "Json SENT: "+addedEquipListJson);
            i.putExtra("currAddedEquipList", addedEquipListJson);

            activityLauncher.launch(i);
        });

        fabSubmit.setOnClickListener((v) -> {
            MaintenanceRequest mRequest = new MaintenanceRequest();
            mRequest.setDescription(editTextDescription.getText().toString());
            mRequest.setObservation(editTextObservation.getText().toString());
            mRequest.setEquipments(adapter.getEquipments());

            SubmitMaintenanceRequestTask task = new SubmitMaintenanceRequestTask(this, mRequest){
                @Override
                protected void preExecuteBackground() {
                    super.preExecuteBackground();
                    fabSubmit.setEnabled(false);
                }

                @Override
                protected void postExecuteBackground(TaskResults taskResults) {
                    super.postExecuteBackground(taskResults);
                    fabSubmit.setEnabled(true);
                    if(taskResults == TaskResults.SUCCESS){
                        Toast.makeText(context, "Relatório enviado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(context, "Falha ao enviar relatório.", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            task.execute();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!adapter.getEquipments().isEmpty()){
            rvAddedEquipments.setVisibility(VISIBLE);
            tvNoEquipmentsAdded.setVisibility(GONE);
        }else{
            rvAddedEquipments.setVisibility(GONE);
            tvNoEquipmentsAdded.setVisibility(VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}