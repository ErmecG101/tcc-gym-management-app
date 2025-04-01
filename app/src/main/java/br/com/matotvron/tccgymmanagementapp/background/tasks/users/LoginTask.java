package br.com.matotvron.tccgymmanagementapp.background.tasks.users;

import static br.com.matotvron.tccgymmanagementapp.background.RequestCodes.PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.matotvron.tccgymmanagementapp.background.Utils;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.models.Gym;
import br.com.matotvron.tccgymmanagementapp.background.models.User;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DefaultPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.background.preferences.UserPreferences;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class LoginTask extends CustomBackgroundTask {

    final DefaultRequest requestClient = new DefaultRequest(this.context);
    final String userName, password;

    public LoginTask(Context context, String userName, String password) {
        super(context);
        this.userName = userName;
        this.password = password;
    }



    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.requiredPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        this.locale = "/users/login";
    }

    @Override
    protected TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        try{
            if(requiredPermissions.contains(Manifest.permission.INTERNET) && requiredPermissions.contains(Manifest.permission.ACCESS_NETWORK_STATE)){
                ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null){
                    return TaskResults.NO_INTERNET;
                }
            }

            Map<String, String> json = new HashMap<>();

            json.put("userName", userName);
            json.put("password", Utils.encryptPassword(password));
            String bodyJson = requestClient.post(locale, new Gson().toJson(json));

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

        }catch (FalhaRequestException e){
            if(e.getResponseCode() == 401){
                exception = e;
                return TaskResults.WRONG_CREDENTIALS;
            }
        }catch (Exception e){
            exception = e;
            return TaskResults.UNKNOWN_ERROR;
        }
        return TaskResults.SUCCESS;
    }

    @Override
    protected void postExecuteBackground(TaskResults taskResults) {
        super.postExecuteBackground(taskResults);
        if(taskResults == TaskResults.MISSING_PERMISSIONS){
            ((Activity) context).requestPermissions(requiredPermissions.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }
}
