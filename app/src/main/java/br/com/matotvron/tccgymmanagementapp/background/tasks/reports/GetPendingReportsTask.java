package br.com.matotvron.tccgymmanagementapp.background.tasks.reports;

import android.Manifest;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipment;
import br.com.matotvron.tccgymmanagementapp.background.models.MaintenanceRequest;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class GetPendingReportsTask extends CustomBackgroundTask {

    final DefaultRequest requestClient = new DefaultRequest(this.context);

    private List<MaintenanceRequest> result;

    public GetPendingReportsTask(Context context) {
        super(context);
    }

    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.requiredPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        this.locale = "/requests";
    }

    @Override
    protected TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        bodyJson = requestClient.get(locale);
        List<MaintenanceRequest> mRequests = new GsonBuilder().setDateFormat(SPRING_BOOT_DATE_FORMAT).create().fromJson(bodyJson, new TypeToken<List<MaintenanceRequest>>() {
        }.getType());

        result = mRequests;
        return TaskResults.SUCCESS;
    }

    public List<MaintenanceRequest> getResult() {
        return result;
    }
}
