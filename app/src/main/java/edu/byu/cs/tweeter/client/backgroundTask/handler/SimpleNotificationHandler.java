package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.observer.SimpleNotificationObserver;

public class SimpleNotificationHandler extends BackgroundTaskHandler<SimpleNotificationObserver> {

    // used for handlers that don't pass parameters back

    public SimpleNotificationHandler(SimpleNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, SimpleNotificationObserver observer) {
        observer.handleSuccess();
    }
}
