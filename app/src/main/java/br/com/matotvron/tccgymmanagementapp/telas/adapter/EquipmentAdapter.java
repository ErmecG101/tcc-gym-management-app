package br.com.matotvron.tccgymmanagementapp.telas.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipments;

public class EquipmentAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private final List<Equipments> list;
    private final Context context;

    public EquipmentAdapter(Context context, List<Equipments> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.item_equipamento, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitulo.setText(list.get(position).getName());
        holder.tvDescricao.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView tvTitulo, tvDescricao;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitulo = itemView.findViewById(R.id.item_equip_tit);
        tvDescricao = itemView.findViewById(R.id.item_equip_desc);
    }
}
