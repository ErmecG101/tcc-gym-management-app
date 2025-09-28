package br.com.matotvron.tccgymmanagementapp.dialogs;

import static android.view.View.INVISIBLE;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;

public class ExceptionDialog {

    final private Context context;
    final private String exceptionTitle;
    final private String exceptionDescription;
    final private Exception exception;
    final private CustomBackgroundTask customBackgroundTask;

    public ExceptionDialog(Context context, String exceptionTitle, String exceptionDescription, Exception exception, CustomBackgroundTask customBackgroundTask) {
        this.context = context;
        this.exceptionTitle = exceptionTitle;
        this.exceptionDescription = exceptionDescription;
        this.exception = exception;
        this.customBackgroundTask = customBackgroundTask;
    }

    public void show() {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_exception, null);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(dialogView).create();

        TextView tvTitle = dialogView.findViewById(R.id.tvExceptionTitle);
        if(tvTitle != null)
            tvTitle.setText(exceptionTitle.isEmpty() ? context.getString(R.string.default_exception_title) : this.exceptionTitle);

        ImageButton btnClose = dialogView.findViewById(R.id.btnClose);
        Button btnCancelar = dialogView.findViewById(R.id.btnCancelar);
        Button btnTentarNovamente = dialogView.findViewById(R.id.btnTryAgain);

        if(btnClose != null)
            btnClose.setOnClickListener((v) -> alertDialog.dismiss());
        if(btnCancelar != null)
            btnCancelar.setOnClickListener((v) -> alertDialog.dismiss());
        if(btnTentarNovamente != null){
            btnTentarNovamente.setOnClickListener((v) -> {
                this.customBackgroundTask.execute();
                alertDialog.dismiss();
            });

        }

        TextView tvExceptionDescription = dialogView.findViewById(R.id.tvExceptionDescription);
        TextView tvExceptionType = dialogView.findViewById(R.id.tvExceptionType);

        if(tvExceptionDescription != null)
            tvExceptionDescription.setText(this.exceptionDescription.isEmpty() ? context.getString(R.string.default_exception_description) : this.exceptionDescription);
        if(tvExceptionType != null)
            if(exception != null)
                tvExceptionType.setText(String.format("%s%s", context.getString(R.string.exception_type_prefix), exception.getClass().getName().toUpperCase()));
            else
                tvExceptionType.setVisibility(INVISIBLE);

        alertDialog.show();

    }
}
