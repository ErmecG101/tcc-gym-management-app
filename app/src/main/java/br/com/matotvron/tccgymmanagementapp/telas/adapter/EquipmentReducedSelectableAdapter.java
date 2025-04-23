package br.com.matotvron.tccgymmanagementapp.telas.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;

public class EquipmentReducedSelectableAdapter extends RecyclerView.Adapter<EquipmentReducedViewHolder> {

    private Context context;
    private List<Equipment> equipments;
    private boolean selectable;

    public EquipmentReducedSelectableAdapter(Context context, List<Equipment> equipments, boolean selectable) {
        this.context = context;
        this.equipments = equipments;
        this.selectable = selectable;
    }

    @NonNull
    @Override
    public EquipmentReducedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.item_equipment_reduced, parent, false);
        return new EquipmentReducedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentReducedViewHolder holder, int position) {
        Equipment e = equipments.get(position);
        holder.tvEquipNameReduced.setText(e.getName());
        holder.tvEquipCodeReduced.setText(e.getPropertyNumber());

        if(selectable){
            holder.ivDeleteBtn.setVisibility(View.GONE);

        }else{
            holder.ivDeleteBtn.setVisibility(View.VISIBLE);
            //ADD DELETE LOGIC.
        }
    }

    @Override
    public int getItemCount() {
        return equipments.size();
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void addItems(List<Equipment> itemsToAdd){
        int oldSize = equipments.size();
        for(Equipment equip : itemsToAdd){
            if(!equipments.contains(equip))
                equipments.add(equip);
        }

        notifyItemRangeInserted(oldSize, equipments.size());
    }

    public void removeItem(Equipment equipToRemove){
        int indexOfRemovedItem = equipments.indexOf(equipToRemove);
        equipments.remove(equipToRemove);
        notifyItemRemoved(indexOfRemovedItem);
    }

    public void clearList(){
        int oldIndex = equipments.size();
        equipments.clear();
        notifyItemRangeRemoved(0, oldIndex);
    }
}

class EquipmentReducedViewHolder extends RecyclerView.ViewHolder{
    CardView cvItemEquipReduced;
    TextView tvEquipNameReduced, tvEquipCodeReduced;
    ImageView ivDeleteBtn;

    public EquipmentReducedViewHolder(@NonNull View itemView) {
        super(itemView);

        cvItemEquipReduced = itemView.findViewById(R.id.cv_item_equip_reduced);
        tvEquipNameReduced = itemView.findViewById(R.id.tv_equip_name_reduced);
        tvEquipCodeReduced = itemView.findViewById(R.id.tv_equip_code_reduced);
        ivDeleteBtn = itemView.findViewById(R.id.iv_delete_btn);
    }
}
