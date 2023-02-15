package edu.byu.cs.tweeter.client.backgroundTask.observer;

public interface PageTaskObserver extends ServiceObserver {
     void addItems();
     void handleSuccess();
     void handleFailure(String message);
     void handleException(Exception ex);

}
