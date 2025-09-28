package br.com.matotvron.tccgymmanagementapp.telas.principaltabfragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.constants.ReportStatusConstant;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;
import br.com.matotvron.tccgymmanagementapp.background.tasks.reports.GetPendingReportsTask;
import br.com.matotvron.tccgymmanagementapp.telas.adapter.MaintanenceReportAdapter;
import br.com.matotvron.tccgymmanagementapp.telas.relatorios.CadastrarRelatorioActivity;

public class RelatoriosFragment extends Fragment {

    private FloatingActionButton fabCreateReport;
    private SwipeRefreshLayout swpRefreshPendingReports;
    private RecyclerView rvPendingReports;
    private ProgressBar pbPendingReports;
    private TextView tvPendingReports;
    private GetPendingReportsTask task;
    private MaintanenceReportAdapter pendingReportsAdapter;

    public RelatoriosFragment() {
    }
    public static RelatoriosFragment newInstance(String param1, String param2) {
        RelatoriosFragment fragment = new RelatoriosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_relatorios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        pendingReportsAdapter = new MaintanenceReportAdapter(getContext(), new ArrayList<>(), ReportStatusConstant.PENDING);
        rvPendingReports.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvPendingReports.setAdapter(pendingReportsAdapter);
        configureTask();
        fabCreateReport.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CadastrarRelatorioActivity.class));
        });
        swpRefreshPendingReports.setOnRefreshListener(() -> {
            swpRefreshPendingReports.setRefreshing(true);
            task.execute();
        });
        task.execute();
    }

    private void configureTask(){
        task = new GetPendingReportsTask(getContext()){

            @Override
            protected void preExecuteBackground() {
                super.preExecuteBackground();
                tvPendingReports.setVisibility(GONE);
                pbPendingReports.setVisibility(VISIBLE);
                rvPendingReports.setVisibility(GONE);
            }

            @Override
            protected void postExecuteBackground(TaskResults taskResults) {
                super.postExecuteBackground(taskResults);
                swpRefreshPendingReports.setRefreshing(false);
                if(getResult().isEmpty()){
                    tvPendingReports.setVisibility(VISIBLE);
                    pbPendingReports.setVisibility(GONE);
                    rvPendingReports.setVisibility(GONE);
                    clearList();
                }else{
                    tvPendingReports.setVisibility(GONE);
                    pbPendingReports.setVisibility(GONE);
                    rvPendingReports.setVisibility(VISIBLE);
                    clearList();
                    pendingReportsAdapter.getItems().addAll(getResult());
                    pendingReportsAdapter.notifyItemRangeInserted(0, getResult().size());
                }
            }
        };
    }

    private void clearList(){
        int oldIndex = pendingReportsAdapter.getItemCount();
        pendingReportsAdapter.getItems().clear();
        pendingReportsAdapter.notifyItemRangeRemoved(0, oldIndex);
    }

    private void initViews(@NonNull View view){
        swpRefreshPendingReports = view.findViewById(R.id.swp_refresh_pending_reports);
        rvPendingReports = view.findViewById(R.id.rv_pending_reports);
        pbPendingReports = view.findViewById(R.id.pb_pending_reports);
        tvPendingReports = view.findViewById(R.id.tv_no_pending_reports);
        fabCreateReport = view.findViewById(R.id.fab_cadastrar_relatorio);
    }
}