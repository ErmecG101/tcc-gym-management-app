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
import br.com.matotvron.tccgymmanagementapp.background.constants.ReportStatusConstant;
import br.com.matotvron.tccgymmanagementapp.background.models.MaintenanceRequest;

public class MaintanenceReportAdapter extends RecyclerView.Adapter<MaintenanceReportViewHolder> {

    private final List<MaintenanceRequest> items;
    private final Context context;
    private final ReportStatusConstant itemsStatus;

    public MaintanenceReportAdapter(Context context, List<MaintenanceRequest> items, ReportStatusConstant itemsStatus) {
        this.items = items;
        this.context = context;
        this.itemsStatus = itemsStatus;
    }

    @NonNull
    @Override
    public MaintenanceReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.item_report, parent, false);
        return new MaintenanceReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceReportViewHolder holder, int position) {
        MaintenanceRequest item = items.get(position);
        holder.tvReportUser.setText(String.format(context.getString(R.string.report_user), item.getUserDTO().getName()));
        holder.tvReportDate.setText(String.format(context.getString(R.string.report_date), item.getCreatedAt().split(" ")[0]));
        holder.tvReportDetails.setText(String.format(context.getString(R.string.report_details), item.getEquipments().size()));
        holder.tvReportStatus.setText(ReportStatusConstant.textByStatus(context, itemsStatus));
        holder.tvReportStatus.setBackgroundColor(ReportStatusConstant.colorByStatus(itemsStatus));
        //todo fix Title
        holder.tvReportTitle.setText("Report #"+position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<MaintenanceRequest> getItems() {
        return items;
    }
}

class MaintenanceReportViewHolder extends RecyclerView.ViewHolder{
    TextView tvReportStatus, tvReportTitle, tvReportDetails, tvReportDate, tvReportUser;

    public MaintenanceReportViewHolder(@NonNull View itemView) {
        super(itemView);
        tvReportDate = itemView.findViewById(R.id.tv_report_date);
        tvReportStatus = itemView.findViewById(R.id.tv_report_status);
        tvReportTitle = itemView.findViewById(R.id.tv_report_title);
        tvReportDetails = itemView.findViewById(R.id.tv_report_details);
        tvReportUser = itemView.findViewById(R.id.tv_report_user);
    }
}
