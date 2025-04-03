package br.com.matotvron.tccgymmanagementapp.background.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class DebugPreferences extends DefaultPreferences<String>{
    public DebugPreferences(Context context) {
        super(context);
    }

    @Override
    public void salvar(String prefKey, String valor) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(prefKey, valor);
        prefsEditor.apply();
    }

    @Override
    public String obterPreference(String prefKey, Type type) {
        return prefs.getString(prefKey, "");
    }
}
