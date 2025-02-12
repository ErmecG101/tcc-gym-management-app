package br.com.matotvron.tccgymmanagementapp.background.tasks.users;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

import br.com.matotvron.tccgymmanagementapp.background.http.DefaultRequest;
import br.com.matotvron.tccgymmanagementapp.background.tasks.CustomBackgroundTask;
import br.com.matotvron.tccgymmanagementapp.background.tasks.TaskResults;

public class GetAllUsersTask extends CustomBackgroundTask {
    public GetAllUsersTask(Context context) {
        super(context);
    }

    final DefaultRequest requestClient = new DefaultRequest(this.context);

    @Override
    protected void preExecuteBackground() {
        this.requiredPermissions.add(Manifest.permission.INTERNET);
        this.locale = "/users";
    }

    @Override
    protected TaskResults executeBackground() throws IOException {
        bodyJson = requestClient.get(locale);

        return TaskResults.SUCCESS;
    }

    @Override
    protected void postExecuteBackground(TaskResults taskResults) {
    }
}
