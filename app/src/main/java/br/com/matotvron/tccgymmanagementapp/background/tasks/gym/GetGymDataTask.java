package br.com.matotvron.tccgymmanagementapp.background.tasks.gym;

import android.Manifest;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.Gym;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class GetGymDataTask extends CustomBackgroundTask {

    final DefaultRequest requestClient = new DefaultRequest(this.context);

    private final String gymId;

    public GetGymDataTask(Context context, String gymId) {
        super(context);
        this.gymId = gymId;
    }

    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.requiredPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        this.locale = "/gyms/"+gymId;
    }

    @Override
    protected TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        try{
            bodyJson = requestClient.get(locale);
            Gym gym = new Gson().fromJson(bodyJson, Gym.class);

            new DefaultPreferences<Gym>(context).salvar(PreferencesMap.PREF_GYM_OBJ, gym);
            return TaskResults.SUCCESS;
        }catch (FalhaServidorException e ){
            if(e.getResponseCode() == 404){
                return TaskResults.GYM_NOT_FOUND;
            }
            throw e;
        }
    }
}
