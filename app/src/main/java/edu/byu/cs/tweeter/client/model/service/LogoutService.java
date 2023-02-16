package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.cache.Cache;

public class LogoutService {

    public interface Observer {
        void logoutToast();
        void cancelLogoutToast();
        void logoutUser();
        void displayFailureMessage(String message);
    }



}
