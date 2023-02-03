package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowingService;
import edu.byu.cs.tweeter.client.model.service.LogoutService;
import edu.byu.cs.tweeter.client.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {
    private final View view;
    private FollowingService followingService;
    private LogoutService logoutService;
    private PostStatusService postStatusService;

    public MainPresenter(View view) {
        this.view = view;
        followingService = new FollowingService();
        logoutService = new LogoutService();
        postStatusService = new PostStatusService();
    }

    public void unfollowUser(User selectedUser) {
        followingService.unfollowUser(selectedUser, new FollowingObserver());
    }

    public void followUser(User selectedUser) {
        followingService.followUser(selectedUser, new FollowingObserver());
    }

    public void startLogout() {
        logoutService.startLogout(new LogoutObserver());
    }

    public void isFollower(User selectedUser) {
        followingService.isFollower(selectedUser, new FollowingObserver());
    }

    public void postStatus(String post) {
        postStatusService.postStatus(post, new PostStatusObserver());
    }

    public void getCounts(User selectedUser) {
        followingService.getCounts(selectedUser, new FollowingObserver());
    }

    public interface View {
        void displayMessage(String message);

        void updateSelectedUserFollowingAndFollowers();

        void updateButton(boolean isFollowing);

        void updateFollowerCount(int count);

        void updateFolloweeCount(int count);

        void setButton(boolean b);

        void setLogoutToast();

        void cancelLogoutToast();

        void logoutUser();

        void postToast();

        void cancelPostToast();

        void displayExceptionAndLog(String message, Exception ex);

        void updateRelationship(boolean isFollowing);
    }

    private class FollowingObserver implements FollowingService.Observer {

        @Override
        public void showSuccessMessage(String message, User selectedUser) {
            view.displayMessage(message + selectedUser.getAlias());
        }

        @Override
        public void showErrorMessage(String message) {
            view.displayMessage(message);
        }

        @Override
        public void updateSelectedUserFollowingAndFollowers() {
            view.updateSelectedUserFollowingAndFollowers();
        }

        @Override
        public void updateFollowButton(boolean isFollowing) {
            view.updateButton(isFollowing);
        }

        @Override
        public void updateFollowerCount(int count) {
            view.updateFollowerCount(count);
        }

        @Override
        public void displayCountMessage(String s) {
            view.displayMessage(s);
        }

        @Override
        public void updateFolloweeCount(int count) {
            view.updateFolloweeCount(count);
        }

        @Override
        public void setFollowButton(boolean b) {
            view.setButton(b);
        }

        @Override
        public void updateFollowRelationship(boolean isFollowing) {
            view.updateRelationship(isFollowing);
        }
    }

    private class LogoutObserver implements LogoutService.Observer {

        @Override
        public void logoutToast() {
            view.setLogoutToast();
        }

        @Override
        public void cancelLogoutToast() {
            view.cancelLogoutToast();
        }

        @Override
        public void logoutUser() {
            view.logoutUser();
        }

        @Override
        public void displayFailureMessage(String message) {
            view.displayMessage(message);
        }

    }

    private class PostStatusObserver implements PostStatusService.Observer {
        @Override
        public void postToast() {
            view.postToast();
        }

        @Override
        public void cancelPostToast() {
            view.cancelPostToast();
        }

        @Override
        public void displayExceptionAndLog(String message, Exception ex) {
            view.displayExceptionAndLog(message, ex);
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }
}
