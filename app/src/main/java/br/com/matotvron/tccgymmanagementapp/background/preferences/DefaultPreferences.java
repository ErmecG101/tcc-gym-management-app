package br.com.matotvron.tccgymmanagementapp.background.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class DefaultPreferences<T> {

    final static String preferenceFileName = "gym_pref_file";
    SharedPreferences prefs;
    final Context context;

    public DefaultPreferences(Context context) {
        this.context = context;
        this.prefs = this.context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
    }

    public abstract void salvar(String prefKey ,T valor);

    public abstract T obterPreference(String prefKey);

    public abstract void apagarPreferences(String prefKey);

}
