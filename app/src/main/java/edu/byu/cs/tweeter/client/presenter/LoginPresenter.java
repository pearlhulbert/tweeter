package edu.byu.cs.tweeter.client.presenter;

import android.widget.EditText;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {

    public interface View {

        void loginUnsuccessful(String message);

        void loginToast();

        void setErrorView(Exception e);

        void startActivity(User loggedUser);
    }

    private View view;
    private LoginService loginService;

    public LoginPresenter(View view) {
        this.view = view;
        loginService = new LoginService();
    }

    public void login(EditText alias, EditText password) {
        loginService.login(alias, password, new LoginObserver());
    }

    private class LoginObserver implements LoginService.Observer {

        @Override
        public void loginUnsuccessful(String message) {
            view.loginUnsuccessful(message);
        }

        @Override
        public void setLoginToast() {
            view.loginToast();
        }

        @Override
        public void validateLogin(EditText alias, EditText password) {
            if (alias.getText().length() > 0 && alias.getText().charAt(0) != '@') {
                throw new IllegalArgumentException("Alias must begin with @.");
            }
            if (alias.getText().length() < 2) {
                throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
            }
            if (password.getText().length() == 0) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }
        }

        @Override
        public void setErrorView(Exception e) {
            view.setErrorView(e);
        }

        @Override
        public void startActivity(User loggedUser) {
            view.startActivity(loggedUser);
        }
    }
}