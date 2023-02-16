package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.model.service.FollowingService;

public class GetFollowingCountHandler extends CountHandler {

    public GetFollowingCountHandler(CountObserver observer) {
        super(observer);
    }
}
