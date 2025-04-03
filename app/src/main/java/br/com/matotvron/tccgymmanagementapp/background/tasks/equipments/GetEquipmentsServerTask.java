package br.com.matotvron.tccgymmanagementapp.background.tasks.equipments;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.Equipments;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class GetEquipmentsServerTask extends CustomBackgroundTask {
    public GetEquipmentsServerTask(Context context) {
        super(context);
    }

    private List<Equipments> result;

    final DefaultRequest requestClient = new DefaultRequest(this.context);

    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.locale = "/equipments";
    }

    @Override
    protected TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        bodyJson = requestClient.get(this.locale);
        result = new Gson().fromJson(bodyJson, new TypeToken<List<Equipments>>(){}.getType());
        return TaskResults.SUCCESS;

    }

    public List<Equipments> getResult() {
        return result;
    }
}
