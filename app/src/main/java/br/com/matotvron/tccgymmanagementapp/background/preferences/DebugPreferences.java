package br.com.matotvron.tccgymmanagementapp.background.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Type;

import kotlin.NotImplementedError;


/**
 * @deprecated Deprecated. No Need to use Specific Classes. Use {@link DefaultPreferences}
 */
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

    @Override
    public void apagarPreferences(String prefKey) {
        throw new NotImplementedError("Função APAGAR PREFERENCES ainda a ser considerada");
    }
}
