package edu.byu.cs.tweeter.client.model.service;

import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.LoginHandler;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginService {
    public interface Observer {
        void loginUnsuccessful(String message);
        void validateLogin(EditText alias, EditText password);
        void setLoginToast();
        void setErrorView(Exception e);
        void startActivity(User loggedUser);
    }

    public void login(EditText alias, EditText password, Observer observer) {
        try {
            observer.validateLogin(alias, password);
            observer.setLoginToast();
            // Send the login request.
            LoginTask loginTask = new LoginTask(alias.getText().toString(),
                    password.getText().toString(),
                    new LoginHandler(observer));
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(loginTask);
        } catch (Exception e) {
            observer.setErrorView(e);
        }
    }

}
