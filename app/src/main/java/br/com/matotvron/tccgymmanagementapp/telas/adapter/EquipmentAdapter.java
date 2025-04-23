package br.com.matotvron.tccgymmanagementapp.telas.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;
import br.com.matotvron.tccgymmanagementapp.telas.equipment.EquipmentViewActivity;

public class EquipmentAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private final List<Equipment> list;
    private final Context context;

    public EquipmentAdapter(Context context, List<Equipment> list) {
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
        holder.cvItemEquip.setOnClickListener((v) -> {
            Intent i = new Intent(context, EquipmentViewActivity.class);
            i.putExtra("equipment", new Gson().toJson(list.get(position)));
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView tvTitulo, tvDescricao;
    CardView cvItemEquip;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitulo = itemView.findViewById(R.id.item_equip_tit);
        tvDescricao = itemView.findViewById(R.id.item_equip_desc);
        cvItemEquip = itemView.findViewById(R.id.cv_item_equip);
    }
}
