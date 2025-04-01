package br.com.matotvron.tccgymmanagementapp.background.preferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public interface DefaultPreferencesSave<T> {

    T obterPreference(String prefKey, Type type);
}
