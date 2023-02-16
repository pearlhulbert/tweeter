package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends AuthenticateTaskHandler {

    public RegisterHandler(AuthenticateTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message data, AuthenticateTaskObserver observer) {
        observer.handleSuccess(data);
    }

    @Override
    protected User getCurrentUser(Message data, AuthenticateTaskObserver observer) {
        return null;
    }

    @Override
    protected AuthToken getAuthToken(Message data, AuthenticateTaskObserver observer) {
        return null;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(RegisterTask.SUCCESS_KEY);
        if (success) {
            User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
            AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);
            Cache.getInstance().setCurrUser(registeredUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            observer.startActivity(registeredUser);
        } else if (msg.getData().containsKey(RegisterTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(RegisterTask.MESSAGE_KEY);
            observer.registerUnsuccessful("Failed to register: " + message);
        } else if (msg.getData().containsKey(RegisterTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(RegisterTask.EXCEPTION_KEY);
            observer.registerUnsuccessful("Failed to register because of exception: " + ex.getMessage());
        }
    }
}
