package br.com.matotvron.tccgymmanagementapp.background.http;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import br.com.matotvron.tccgymmanagementapp.BuildConfig;
import br.com.matotvron.tccgymmanagementapp.background.RequestCodes;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.background.preferences.DebugPreferences;
import br.com.matotvron.tccgymmanagementapp.background.preferences.PreferencesMap;
import br.com.matotvron.tccgymmanagementapp.telas.settings.ConfiguracoesActivity;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DefaultRequest {
    private final Context context;
    protected final OkHttpClient client = new OkHttpClient();

    public DefaultRequest(Context context) {
        this.context = context;
    }

    private static String releaseUrl = "192.168.0.1";


    public String get(String locale) throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        Log.d("Teste", "Start GET");
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context, new String[]  {Manifest.permission.INTERNET}, RequestCodes.PERMISSION_REQUEST_CODE);
            throw new FaltaPermissaoException("Faltando permissão de acesso a Internet do Dispositivo");
        }
        Log.d("Teste", "Permissão Validada");
        String usedIp = validateServerUrl();
        if(usedIp == null){
            askForDebugServerUrl();
            throw new IOException("URL Inválida");
        }
        Log.d("Teste", "Url Validada: "+usedIp+locale);
        Request request = new Request.Builder()
                .url(usedIp+locale)
                .build();

        return executeRequest(request);
    }

    public String post(String locale,String json) throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException  {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context, new String[]  {Manifest.permission.INTERNET}, RequestCodes.PERMISSION_REQUEST_CODE);
            throw new FaltaPermissaoException("Faltando permissão de acesso a Internet do Dispositivo");
        }
        String usedIp = validateServerUrl();
        if(usedIp == null){
            askForDebugServerUrl();
            throw new IOException("URL Inválida");
        }

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(usedIp + locale)
                .post(body)
                .build();
        return executeRequest(request);
    }

    private String executeRequest(Request request) throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException {
        Log.d("Teste", "Antes do Client.newCall");
        try (Response response = client.newCall(request).execute()){
            Log.d("Teste","Codigo: "+response.code());
            if(response.code() > 499)
                throw new FalhaServidorException("Falha no servidor, Erro: "+response.code(), response.code());
            else if(response.code() > 399){
                throw new FalhaRequestException("Falha na request, Erro: "+response.code(), response.code());
            }else{
                if(response.body() != null)
                    return response.body().string();
                else{
                    return "{}";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    protected String validateServerUrl(){
        if(BuildConfig.DEV){
            DebugPreferences debugPreferences = new DebugPreferences(context);
            String devIp = debugPreferences.obterPreference(PreferencesMap.PREF_DEBUG_IP);
            if(devIp == null ||devIp.isEmpty() || devIp.isBlank()) {
                return null;
            }
            else
                return devIp;
        }else{
            return releaseUrl;
        }
    }

    private void askForDebugServerUrl(){

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage("Ip de servidor DEBUG não encontrado, por favor informe um.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, ConfiguracoesActivity.class));
                    }
                }).create();
        dialog.show();
    }

}
