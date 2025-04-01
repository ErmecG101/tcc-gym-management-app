package br.com.matotvron.tccgymmanagementapp.background.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.models.Gym;

public class DefaultPreferences<T> implements DefaultPreferencesSave<T> {

    final static String preferenceFileName = "gym_pref_file";
    SharedPreferences prefs;
    final Context context;

    public DefaultPreferences(Context context) {
        this.context = context;
        this.prefs = this.context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
    }

    public void salvar(String prefKey ,T valor){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(prefKey, new Gson().toJson(valor));
        prefsEditor.apply();
    }

    public void apagarPreferences(String prefKey){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.remove(prefKey);
        prefsEditor.apply();
    }

    @Override
    public T obterPreference(String prefKey, Type type) {
        return new Gson().fromJson(prefs.getString(prefKey, "{}"), type);
    }
}
