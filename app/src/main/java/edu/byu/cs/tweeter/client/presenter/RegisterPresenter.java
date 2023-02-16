package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;

import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {

    public interface View {
        void registerUnsuccessful(String message);
        void registerToast();
        void setErrorView(Exception e);
        void startActivity(User registeredUser);
    }

    private View view;
    private UserService registerService;

    public RegisterPresenter(View view) {
        this.view = view;
        registerService = new UserService();
    }

    public void register(EditText firstName, EditText lastName, EditText alias, EditText password, ImageView imageToUpload) {
        registerService.register(firstName, lastName, alias, password, imageToUpload, new RegisterObserver());
    }

    private class RegisterObserver implements UserService.AuthObserver {

        @Override
        public void registerUnsuccessful(String message) {
            view.registerUnsuccessful(message);
        }

        @Override
        public void setRegisterToast() {
            view.registerToast();
        }

        @Override
        public void validateRegistration(EditText firstName, EditText lastName, EditText alias, EditText password, ImageView imageToUpload) {
            if (firstName.getText().length() == 0) {
                throw new IllegalArgumentException("First Name cannot be empty.");
            }
            if (lastName.getText().length() == 0) {
                throw new IllegalArgumentException("Last Name cannot be empty.");
            }
            if (alias.getText().length() == 0) {
                throw new IllegalArgumentException("Alias cannot be empty.");
            }
            if (alias.getText().charAt(0) != '@') {
                throw new IllegalArgumentException("Alias must begin with @.");
            }
            if (alias.getText().length() < 2) {
                throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
            }
            if (password.getText().length() == 0) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }

            if (imageToUpload.getDrawable() == null) {
                throw new IllegalArgumentException("Profile image must be uploaded.");
            }
        }

        @Override
        public void displaySuccessMessage(String message) {

        }

        @Override
        public void validateLogin(EditText alias, EditText password) {

        }

        @Override
        public void setLoginToast() {

        }

        @Override
        public void setErrorView(Exception e) {
            view.setErrorView(e);
        }

        @Override
        public void loginUnsuccessful(String message) {

        }

        @Override
        public void startActivity(User registeredUser) {
            view.startActivity(registeredUser);
        }

        @Override
        public void handleSuccess(Message msg) {

        }

        @Override
        public void displayMessage(String message) {
        }
    }
}
