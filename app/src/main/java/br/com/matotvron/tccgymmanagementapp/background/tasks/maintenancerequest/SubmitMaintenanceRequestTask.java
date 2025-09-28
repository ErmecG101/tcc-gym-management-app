package br.com.matotvron.tccgymmanagementapp.background.tasks.maintenancerequest;

import android.Manifest;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.matotvron.tccgymmanagementapp.background.dtos.UserDTO;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.MaintenanceRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class SubmitMaintenanceRequestTask extends CustomBackgroundTask {

    private final MaintenanceRequest maintenanceRequest;
    final DefaultRequest requestClient = new DefaultRequest(this.context);

    public SubmitMaintenanceRequestTask(Context context, MaintenanceRequest maintenanceRequest) {
        super(context);
        this.maintenanceRequest = maintenanceRequest;
    }


    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.locale = "/requests";
    }

    @Override
    protected TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        maintenanceRequest.setCreatedAt(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Resources.getSystem().getConfiguration().getLocales().get(0)).format(new Date()));
        User user = new DefaultPreferences<User>(context).obterPreference(PreferencesMap.PREF_USER_OBJ, User.class);
        UserDTO uDto = new UserDTO(user);
        maintenanceRequest.setUserDTO(uDto);
        String json = new GsonBuilder().setDateFormat(SPRING_BOOT_DATE_FORMAT).create().toJson(maintenanceRequest);;
        Log.d("AAAB", "json: "+json);
        requestClient.post(this.locale, json);
        return TaskResults.SUCCESS;
    }
}
