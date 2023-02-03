package edu.byu.cs.tweeter.client.presenter;

import android.widget.TextView;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStoryPresenter {

    private static final int PAGE_SIZE = 10;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayErrorMessage(String message);
        void addMoreItems(List<Status> statuses);
        void startActivity(User user);
    }

    private StatusService statusService;
    private UserService userService;

    private GetStoryPresenter.View view;

    private Status lastStatus;

    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetStoryPresenter(GetStoryPresenter.View view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            statusService.loadMoreStoryItems(user, PAGE_SIZE, lastStatus, new GetStoryObserver());
        }
    }

    public void loadUser(TextView userAlias) {
        userService.loadUser(userAlias, new GetUserObserver());
    }


    private class GetStoryObserver implements StatusService.Observer {
        @Override
        public void displayMessage(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayErrorMessage(message);
        }

        @Override
        public void addStatuses(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addMoreItems(statuses);
        }
    }

    private class GetUserObserver implements UserService.Observer {
        @Override
        public void displaySuccessMessage(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void displayFailureMessage(String message) {
            view.displayErrorMessage("Failed to get user's profile..." + message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayErrorMessage("Failed to get following because of exception: " + ex.getMessage());
        }

        @Override
        public void startNewAcivity(User user) {
            view.startActivity(user);
        }

    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
