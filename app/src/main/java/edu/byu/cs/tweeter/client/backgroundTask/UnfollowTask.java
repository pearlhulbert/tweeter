package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that removes a following relationship between two users.
 */
public class UnfollowTask extends BackgroundTask {
    private static final String LOG_TAG = "UnfollowTask";


    /**
     * Auth token for logged-in user.
     * This user is the "follower" in the relationship.
     */
    private AuthToken authToken;
    /**
     * The user that is being followed.
     */
    private User followee;
    /**
     * Message handler that will receive task results.
     */

    public UnfollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler);
        this.authToken = authToken;
        this.followee = followee;
    }

    @Override
    protected void processTask() {
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
    }
}
