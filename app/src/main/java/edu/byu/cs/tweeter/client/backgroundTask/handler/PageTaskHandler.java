package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.backgroundTask.observer.PageTaskObserver;

public class PageTaskHandler extends BackgroundTaskHandler<PageTaskObserver> {

    public PageTaskHandler(PageTaskObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, PageTaskObserver observer) {
        //observer.handleSuccess();
    }

}
