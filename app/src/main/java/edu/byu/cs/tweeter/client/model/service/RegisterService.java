package edu.byu.cs.tweeter.client.model.service;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.client.view.login.RegisterFragment;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterService {

    public interface Observer {
        void registerUnsuccessful(String message);
        void setRegisterToast();
        void validateRegistration(EditText firstName, EditText lastName, EditText alias, EditText password, ImageView imageToUpload);
        void setErrorView(Exception e);
        void startActivity(User registeredUser);
    }

    public void register(EditText firstName, EditText lastName, EditText alias, EditText password, ImageView imageToUpload, Observer observer) {
        // Register and move to MainActivity.
        try {
            observer.validateRegistration(firstName, lastName, alias, password, imageToUpload);
            observer.setRegisterToast();

            // Convert image to byte array.
            Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] imageBytes = bos.toByteArray();

            // Intentionally, Use the java Base64 encoder so it is compatible with M4.
            String imageBytesBase64 = Base64.getEncoder().encodeToString(imageBytes);

            // Send register request.
            RegisterTask registerTask = new RegisterTask(firstName.getText().toString(), lastName.getText().toString(),
                    alias.getText().toString(), password.getText().toString(), imageBytesBase64, new RegisterHandler(observer));

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(registerTask);
        } catch (Exception e) {
            observer.setErrorView(e);
        }
    }

    private class RegisterHandler extends Handler {

        private Observer observer;
        public RegisterHandler(Observer observer) {
            super(Looper.getMainLooper());
            this.observer = observer;
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
}
