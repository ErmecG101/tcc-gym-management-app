package br.com.matotvron.tccgymmanagementapp.background.tasks;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import br.com.matotvron.tccgymmanagementapp.R;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaRequestException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FalhaServidorException;
import br.com.matotvron.tccgymmanagementapp.background.exceptions.FaltaPermissaoException;
import br.com.matotvron.tccgymmanagementapp.dialogs.ExceptionDialog;

public abstract class CustomBackgroundTask{

    final protected List<String> requiredPermissions = new ArrayList<>();
    final protected Context context;
    protected String locale = "";
    private Thread backgroundThread;
    private Exception exception;
    protected String bodyJson;

    public CustomBackgroundTask(Context context) {
        this.context = context;
    }

    protected abstract void preExecuteBackground();
    protected abstract TaskResults executeBackground() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException;

    protected void postExecuteBackground(TaskResults taskResults){
        if(exception != null)
            ((Activity) context).runOnUiThread(() -> handleException(taskResults, exception));
    }

    public void execute(){
        if (!validatePermissions()){
            postExecuteBackground(TaskResults.MISSING_PERMISSIONS);
        }

        preExecuteBackground();
        backgroundThread = new Thread(() -> {

            TaskResults result;
            try {
                result = backgroundExecution();
            } catch (ConnectException e){
                result = TaskResults.FAILED_TO_CONNECT;
                exception = e;
            } catch (IOException e) {
                result = TaskResults.IOEXCEPTION;
                exception = e;
            }catch (FalhaServidorException e){
                result = TaskResults.SERVER_ERROR;
                exception = e;
            }catch (FalhaRequestException e){
                result = TaskResults.REQUEST_ERROR;
                exception = e;
            }catch (FaltaPermissaoException e){
                result = TaskResults.MISSING_PERMISSIONS;
                exception = e;
            } catch (Exception e){
                result = TaskResults.UNKNOWN_ERROR;
                exception = e;
            }
            TaskResults finalResult = result;
            ((Activity) context).runOnUiThread(() -> postExecuteBackground(finalResult));
        });

        backgroundThread.start();
    }

    private TaskResults backgroundExecution() throws FaltaPermissaoException, FalhaServidorException, FalhaRequestException, IOException{
            return executeBackground();
    }

    private boolean validatePermissions(){
        for(String permission : requiredPermissions){
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }

    /**
     * METHOD THAT TRANSLATES FAIL RESULTS INTO ERROR MESSAGES.
     * @param taskResults RESULT TO BE TRANSLATED
     * @param e EXCEPTION THROWN
     */
    protected void handleException(TaskResults taskResults, Exception e){
        String title = "";
        String description = "";

        switch (taskResults){
            case UNKNOWN_ERROR:
                title = context.getString(R.string.default_exception_title);
                description = context.getString(R.string.default_exception_description);
                break;
            case FAILED_TO_CONNECT:
                title = "Falha ao conectar ao Servidor";
                description = "Ocorreu um problema ao tentar conectar com o servidor interno.";
                break;
            case MISSING_PERMISSIONS:
                title = "Falta permissões!";
                description = "Aparentemente você não concedeu as permissões necessárias para a realização dessa operação";
                break;
            case REQUEST_ERROR:
                title = "Erro na Request";
                description = "Ocorreu um erro programático que causou uma falha na comunicação entre o App e o Servidor, por favor, contate o suporte.";
                break;
            case SERVER_ERROR:
                title = "Erro no Servidor";
                description = "Ocorreu um erro no lado de servidor, isso pode ser causado por diversos fatores e por isso recomendamos que entre em contato com o suporte.";
                break;
            case IOEXCEPTION:
                title = "Erro no App";
                description = "Ocorreu um erro em um funcionamento interno do app. Por favor, tente novamente, se o erro persistir entre em contato com o suporte.";
                break;
        }

        ExceptionDialog dialog = new ExceptionDialog(context, title, description, e, this);
        dialog.show();
    }

    /**
     * METHOD THAT SHOWS CUSTOM TITLES AND DESCRIPTIONS AS ERROR MESSAGE DIALOGS
     * @param title TITLE TO BE SHOWN
     * @param description DESCRIPTION TO BE SHOWN
     * @param e EXCEPTION THROWN
     */
    protected void handleException(String title, String description, Exception e){
        ExceptionDialog dialog = new ExceptionDialog(context, title, description, e, this);
        dialog.show();
    }

}
