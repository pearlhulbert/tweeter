package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends AuthenticateTaskHandler {


    public LoginHandler(AuthenticateTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message data, AuthenticateTaskObserver observer) {
        observer.handleSuccess(data);
    }

    @Override
    protected User getCurrentUser(Message data, AuthenticateTaskObserver observer) {
        User loggedInUser = (User) data.getData().getSerializable(LoginTask.USER_KEY);
        Cache.getInstance().setCurrUser(loggedInUser);
        return loggedInUser;
    }

    @Override
    protected AuthToken getAuthToken(Message data, AuthenticateTaskObserver observer) {
        AuthToken authToken = (AuthToken) data.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        return authToken;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);
        if (success) {


            // Cache user session information



            observer.startActivity(loggedInUser);

        } else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(LoginTask.MESSAGE_KEY);
            observer.loginUnsuccessful("Failed to login: " + message);
        } else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
            observer.loginUnsuccessful("Failed to login because of exception: " + ex.getMessage());
        }
    }
}
