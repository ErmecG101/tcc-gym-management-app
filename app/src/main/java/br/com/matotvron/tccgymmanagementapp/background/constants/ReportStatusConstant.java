package br.com.matotvron.tccgymmanagementapp.background.constants;

import android.content.Context;
import android.graphics.Color;

import br.com.matotvron.tccgymmanagementapp.R;

public enum ReportStatusConstant {
    PENDING, APPROVED;

    public static int colorByStatus(ReportStatusConstant status){
        switch (status){
            case PENDING: return Color.parseColor("#00AABB");
            case APPROVED: return Color.parseColor("#00AA00");
            default: return 0;
        }
    }

    public static String textByStatus(Context context, ReportStatusConstant statusConstant){
        switch (statusConstant){
            case APPROVED: return context.getString(R.string.report_approved_text);
            case PENDING: return context.getString(R.string.report_pending_text);
            default: return "";
        }
    }

}
