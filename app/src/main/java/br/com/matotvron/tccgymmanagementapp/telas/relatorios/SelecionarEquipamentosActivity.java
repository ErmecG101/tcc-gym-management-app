package br.com.matotvron.tccgymmanagementapp.telas.relatorios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.equipments.GetEquipmentsServerTask;
import br.com.matotvron.tccgymmanagementapp.telas.adapter.EquipmentReducedSelectableAdapter;

public class SelecionarEquipamentosActivity extends AppCompatActivity {

    EquipmentReducedSelectableAdapter adapter;
    RecyclerView rvItemsSelectToAdd;
    SwipeRefreshLayout swpRefreshEquipsSelect;
    TextView tvNoEquipsFound;
    GetEquipmentsServerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selecionar_equipamentos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<Equipment> currAddedEquips = new ArrayList<>();

        if(getIntent().hasExtra("currAddedEquipList")){
            Type listType = new TypeToken<List<Equipment>>(){}.getType();
            String listFromExtra = getIntent().getStringExtra("currAddedEquipList");
            Gson g = new Gson();
            currAddedEquips.addAll(Objects.requireNonNull(g.fromJson(listFromExtra, listType)));
        }

        rvItemsSelectToAdd = findViewById(R.id.rv_select_equips_to_add);
        rvItemsSelectToAdd.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new EquipmentReducedSelectableAdapter(this, new ArrayList<>(), true);
        rvItemsSelectToAdd.setAdapter(adapter);
        swpRefreshEquipsSelect = findViewById(R.id.swp_refresh_equips_select);
        tvNoEquipsFound = findViewById(R.id.tv_no_equips_found);
        configureTask();
        swpRefreshEquipsSelect.setOnRefreshListener(() -> task.execute());
        task.execute();




    }

    private void configureTask(){
        task = new GetEquipmentsServerTask(this){
            @Override
            protected void postExecuteBackground(TaskResults taskResults) {
                super.postExecuteBackground(taskResults);
                swpRefreshEquipsSelect.setRefreshing(false);
                if(taskResults == TaskResults.SUCCESS){
                    if(getResult() == null || getResult().isEmpty()){
                        rvItemsSelectToAdd.setVisibility(RecyclerView.GONE);
                        tvNoEquipsFound.setVisibility(RecyclerView.VISIBLE);
                        return;
                    }
                    adapter.clearList();
                    adapter.addItems(getResult());
                    rvItemsSelectToAdd.setVisibility(RecyclerView.VISIBLE);
                    tvNoEquipsFound.setVisibility(RecyclerView.GONE);
                }else {
                    rvItemsSelectToAdd.setVisibility(RecyclerView.GONE);
                    tvNoEquipsFound.setVisibility(RecyclerView.VISIBLE);

                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        setResult(SelecionarEquipamentosActivity.RESULT_OK);
        super.onBackPressed();
    }
}