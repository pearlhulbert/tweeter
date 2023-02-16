package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.CountObserver;
import edu.byu.cs.tweeter.client.model.service.FollowingService;

public class GetFollowersCountHandler extends CountHandler {

    public GetFollowersCountHandler(CountObserver observer) {
        super(observer);
    }
}
