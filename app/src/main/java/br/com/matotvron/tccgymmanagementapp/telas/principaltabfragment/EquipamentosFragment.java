package br.com.matotvron.tccgymmanagementapp.telas.principaltabfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.equipments.GetEquipmentsServerTask;
import br.com.matotvron.tccgymmanagementapp.telas.adapter.EquipmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EquipamentosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipamentosFragment extends Fragment {

    private GetEquipmentsServerTask task;

    public EquipamentosFragment() {
    }
    public static EquipamentosFragment newInstance(String param1, String param2) {
        EquipamentosFragment fragment = new EquipamentosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_equipamentos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildTask(view);
        ((SwipeRefreshLayout) view.findViewById(R.id.srl_equipamentos)).setOnRefreshListener(() -> task.execute());
        task.execute();
    }

    private void buildTask(View view){
        task = new GetEquipmentsServerTask(getContext()){

            @Override
            protected void preExecuteBackground() {
                view.findViewById(R.id.pb_equipamentos).setVisibility(View.VISIBLE);
                view.findViewById(R.id.rv_equipamentos).setVisibility(View.GONE);
                view.findViewById(R.id.tv_nenhum_equip_found).setVisibility(View.GONE);
                super.preExecuteBackground();
            }

            @Override
            protected void postExecuteBackground(TaskResults taskResults) {
                super.postExecuteBackground(taskResults);
                view.findViewById(R.id.pb_equipamentos).setVisibility(View.GONE);
                ((SwipeRefreshLayout) view.findViewById(R.id.srl_equipamentos)).setRefreshing(false);
                if(taskResults == TaskResults.SUCCESS){
                    List<Equipment> result = getResult();
                    if(!result.isEmpty()){
                        RecyclerView rvEquipamentos = view.findViewById(R.id.rv_equipamentos);
                        EquipmentAdapter adapter = new EquipmentAdapter(getActivity(), result);
                        rvEquipamentos.setAdapter(adapter);
                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rvEquipamentos.setLayoutManager(layoutManager);
                        adapter.notifyItemRangeInserted(0, result.size());
                        rvEquipamentos.setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tv_nenhum_equip_found).setVisibility(View.GONE);
                    }else{
                        view.findViewById(R.id.rv_equipamentos).setVisibility(View.GONE);
                        view.findViewById(R.id.tv_nenhum_equip_found).setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }


}