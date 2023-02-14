package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PageTasks;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends Handler {

    private StatusService.Observer observer;

    public GetStoryHandler(StatusService.Observer observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {

        boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
        if (success) {
            List<Status> statuses = (List<Status>) msg.getData().getSerializable(PageTasks.ITEMS_KEY);
            boolean hasMorePages = msg.getData().getBoolean(PageTasks.MORE_PAGES_KEY);
            observer.addStatuses(statuses, hasMorePages);
        } else if (msg.getData().containsKey(PageTasks.MESSAGE_KEY)) {
            String message = msg.getData().getString(PageTasks.MESSAGE_KEY);
            observer.displayMessage("Failed to get story: " + message);
        } else if (msg.getData().containsKey(PageTasks.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(PageTasks.EXCEPTION_KEY);
            observer.displayMessage("Failed to get story because of exception: " + ex.getMessage());
        }
    }
}
