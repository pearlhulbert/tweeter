package edu.byu.cs.tweeter.client.model.service;

import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {

    public interface Observer {

        void displaySuccessMessage(String message);

        void displayFailureMessage(String message);

        void displayException(Exception ex);

        void startNewAcivity(User user);
    }

    public void loadUser(TextView userAlias, Observer observer) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias.getText().toString(), new GetUserHandler(observer));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
        observer.displaySuccessMessage("Getting user's profile...");
    }

}
