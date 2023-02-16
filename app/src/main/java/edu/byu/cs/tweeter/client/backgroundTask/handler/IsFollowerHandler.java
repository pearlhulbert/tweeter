package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.SingleObserver;
import edu.byu.cs.tweeter.client.model.service.FollowingService;

public class IsFollowerHandler extends BackgroundTaskHandler<SingleObserver<Boolean>> {

    public IsFollowerHandler(SingleObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Message data, SingleObserver<Boolean> observer) {
        boolean isFollower = data.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.handleSuccess(isFollower);
    }
}
