package br.com.matotvron.tccgymmanagementapp.background.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import br.com.matotvron.tccgymmanagementapp.background.models.Gym;
import br.com.matotvron.tccgymmanagementapp.background.models.User;

/**
* @deprecated Deprecated. No need for specifics classes.\n Using {@link DefaultPreferences<T>}
* */
public class GymPreferences extends DefaultPreferences<Gym>{
    public GymPreferences(Context context) {
        super(context);
    }

    @Override
    public void salvar(String prefKey, Gym valor) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(prefKey, new Gson().toJson(valor));
        prefsEditor.apply();
    }

    @Override
    public Gym obterPreference(String prefKey, Type type) {
        return new Gson().fromJson(prefs.getString(prefKey, "{}"), Gym.class);
    }

    @Override
    public void apagarPreferences(String prefKey) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.remove(prefKey);
        prefsEditor.apply();
    }
}
