package br.com.matotvron.tccgymmanagementapp.background.tasks.users;

import android.Manifest;
import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.Gym;
import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class GetUserDataTask extends CustomBackgroundTask {
    final DefaultRequest requestClient = new DefaultRequest(this.context);
    final String userId;
    public GetUserDataTask(Context context, String userId) {
        super(context);
        this.userId = userId;
    }

    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.requiredPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        this.locale = "/users/"+userId;
    }

    @Override
    protected TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        try{
            bodyJson = requestClient.get(locale);
            User user = new Gson().fromJson(bodyJson, User.class);

            DefaultPreferences<User> uPrefs = new DefaultPreferences<>(context);
            uPrefs.salvar(PreferencesMap.PREF_USER_OBJ, user);

            Gym gym = new Gym();
            gym.setId(user.getGymDTO().getId());
            gym.setName(user.getGymDTO().getName());
            gym.setDocument(user.getGymDTO().getDocument());
            gym.setPhoneNumber(user.getGymDTO().getPhoneNumber());

            DefaultPreferences<Gym> gymPrefs = new DefaultPreferences<>(context);
            gymPrefs.salvar(PreferencesMap.PREF_GYM_OBJ, gym);
            return TaskResults.SUCCESS;
        }catch (FalhaServidorException e){
            if(e.getResponseCode() == 404){
                new DefaultPreferences<User>(context).apagarPreferences(PreferencesMap.PREF_USER_OBJ);
                new DefaultPreferences<Gym>(context).apagarPreferences(PreferencesMap.PREF_GYM_OBJ);
                return TaskResults.USER_NOT_FOUND;
            }else
                throw e;
        }
    }
}
