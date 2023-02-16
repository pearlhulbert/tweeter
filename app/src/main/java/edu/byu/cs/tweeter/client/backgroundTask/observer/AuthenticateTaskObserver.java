package edu.byu.cs.tweeter.client.backgroundTask.observer;

import android.os.Message;

public interface AuthenticateTaskObserver extends ServiceObserver {
    void handleSuccess(Message msg);
    void displayMessage(String message);
}
