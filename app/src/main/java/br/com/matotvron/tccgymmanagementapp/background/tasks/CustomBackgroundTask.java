package br.com.matotvron.tccgymmanagementapp.background.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;

public abstract class CustomBackgroundTask{

    final protected List<String> requiredPermissions = new ArrayList<>();
    final protected Context context;
    protected String locale = "";
    private Thread backgroundThread;

    protected String bodyJson;

    public CustomBackgroundTask(Context context) {
        this.context = context;
    }

    protected abstract void preExecuteBackground();
    protected abstract TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException;

    protected abstract void postExecuteBackground(TaskResults taskResults);

    public void execute(){
        if (!validatePermissions()){
            postExecuteBackground(TaskResults.MISSING_PERMISSIONS);
        }

        preExecuteBackground();
        backgroundThread = new Thread(() -> {
           final TaskResults result = backgroundExecution();
            ((Activity) context).runOnUiThread(() -> postExecuteBackground(result));
        });

        backgroundThread.start();
    }

    private TaskResults backgroundExecution(){
        try{
            return executeBackground();
        }catch (FalhaServidorException e){
            return TaskResults.SERVER_ERROR;
        }catch (FalhaRequestException e){
            return TaskResults.REQUEST_ERROR;
        }catch (FaltaPermissaoException e){
            return TaskResults.MISSING_PERMISSIONS;
        }catch (Exception e){
            return TaskResults.UNKNOWN_ERROR;
        }
    }

    private boolean validatePermissions(){
        for(String permission : requiredPermissions){
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }



}
