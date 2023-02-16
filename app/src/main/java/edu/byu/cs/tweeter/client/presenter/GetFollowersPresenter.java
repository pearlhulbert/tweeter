package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.PageTasks;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter {
    private static final int PAGE_SIZE = 10;

    public interface View {
        void setLoadingFooter(boolean value);
        void displayErrorMessage(String message);
        void addMoreItems(List<User> followees);
        void startActivity(User user);
    }

    private FollowService followService;
    private UserService userService;

    private View view;

    private User lastFollowee;

    private boolean hasMorePages;
    private boolean isLoading = false;

    public GetFollowersPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        userService = new UserService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(isLoading);
            followService.loadMoreFollowersItems(user, PAGE_SIZE, lastFollowee, new GetFollowingObserver());
        }
    }

    public void loadUser(TextView userAlias) {
        userService.loadUser(userAlias, new GetUserObserver());
    }


    private class GetFollowingObserver implements FollowService.PageObserver {

        @Override
        public void displayMessage(String message) {
            isLoading = false;
            view.setLoadingFooter(isLoading);
            view.displayErrorMessage(message);
        }

        @Override
        public void handleSuccess(List<User> followees, boolean hasMorePages, Message msg) {
            followees = (List<User>) msg.getData().getSerializable(PageTasks.ITEMS_KEY);
            hasMorePages = msg.getData().getBoolean(GetFollowingTask.MORE_PAGES_KEY);
            isLoading = false;
            view.setLoadingFooter(isLoading);
            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.addMoreItems(followees);
        }

    }

    private class GetUserObserver implements UserService.AuthObserver {
        @Override
        public void displaySuccessMessage(String message) {
            view.displayErrorMessage(message);
        }

        @Override
        public void validateLogin(EditText alias, EditText password) {

        }

        @Override
        public void setLoginToast() {

        }

        @Override
        public void setErrorView(Exception e) {

        }

        @Override
        public void loginUnsuccessful(String message) {

        }

        @Override
        public void startActivity(User currUser) {
            view.startActivity(currUser);
        }

        @Override
        public void setRegisterToast() {

        }

        @Override
        public void validateRegistration(EditText firstName, EditText lastName, EditText alias, EditText password, ImageView imageToUpload) {

        }

        @Override
        public void registerUnsuccessful(String message) {

        }

        @Override
        public void handleSuccess(Message msg) {

        }

        @Override
        public void displayMessage(String message) {

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
