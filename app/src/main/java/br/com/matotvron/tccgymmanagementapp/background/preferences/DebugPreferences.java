package br.com.matotvron.tccgymmanagementapp.background.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import kotlin.NotImplementedError;

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
    public String obterPreference(String prefKey) {
        return prefs.getString(prefKey, "");
    }

    @Override
    public void apagarPreferences(String prefKey) {
        throw new NotImplementedError("Função APAGAR PREFERENCES ainda a ser considerada");
    }
}
