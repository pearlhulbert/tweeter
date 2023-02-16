package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Bundle;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.observer.PageTaskObserver;

public abstract class PageTaskHandler<T> extends BackgroundTaskHandler<PageTaskObserver> {

    public PageTaskHandler(PageTaskObserver observer) {
        super(observer);
        this.items = new ArrayList<>();
        this.hasMorePages = false;
    }

    private List<T> items;
    private boolean hasMorePages;

    @Override
    protected void handleSuccess(Message data, PageTaskObserver observer) {
        observer.handleSuccess(items, hasMorePages, data);
    }

}
