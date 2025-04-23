package br.com.matotvron.tccgymmanagementapp.telas.principaltabfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.telas.relatorios.CadastrarRelatorioActivity;

public class RelatoriosFragment extends Fragment {

    private FloatingActionButton fabCreateReport;

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

        fabCreateReport = view.findViewById(R.id.fab_cadastrar_relatorio);

        fabCreateReport.setOnClickListener((v) -> {
            startActivity(new Intent(getActivity(), CadastrarRelatorioActivity.class));
        });
    }
}