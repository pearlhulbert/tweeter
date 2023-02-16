package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.observer.CountObserver;

public abstract class CountHandler extends BackgroundTaskHandler<CountObserver> {

    public CountHandler(CountObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message data, CountObserver observer) {
        observer.updateCount(data.getData().getInt("count"));
    }
}
