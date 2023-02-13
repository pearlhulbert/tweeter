package edu.byu.cs.tweeter.client.model.service;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.RegisterHandler;
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

}
