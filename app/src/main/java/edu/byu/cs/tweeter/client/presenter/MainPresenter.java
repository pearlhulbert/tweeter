package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.FollowingService;
import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.client.model.service.LogoutService;
import edu.byu.cs.tweeter.client.model.service.PostStatusService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter {
    private final View view;
    private FollowService followService;
    private UserService logoutService;
    private StatusService postStatusService;

    public MainPresenter(View view) {
        this.view = view;
        followService = new FollowService();
        logoutService = new UserService();
        postStatusService = new StatusService();
    }

//    public void unfollowUser(User selectedUser) {
//        followingService.unfollowUser(selectedUser, new FollowingObserver());
//    }
//
//    public void followUser(User selectedUser) {
//        followingService.followUser(selectedUser, new FollowingObserver());
//    }

    public void startLogout() {
        logoutService.startLogout(new LogoutObserver());
    }

    public void isFollower(User selectedUser) {
        followService.isFollower(selectedUser, new FollowingObserver());
    }

    public void postStatus(String post) {
        postStatusService.postStatus(post, new PostStatusObserver());
    }

    public void getCounts(User selectedUser) {
        followService.getCounts(selectedUser, new CountObserver());
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
        public void setFollowButton(boolean b) {
            view.setButton(b);
        }

        @Override
        public void updateFollowRelationship(boolean isFollowing) {
            view.updateRelationship(isFollowing);
        }
    }

    private class LogoutObserver implements UserService.LogOutObserver {

        @Override
        public void logoutToast() {
            view.setLogoutToast();
        }

        @Override
        public void logoutUser() {
            view.logoutUser();
        }

        @Override
        public void handleSuccess() {

        }

        @Override
        public void postToast() {
            view.postToast();
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }

    }

    private class PostStatusObserver implements StatusService.SimpleObserver {
        @Override
        public void handleSuccess() {

        }

        @Override
        public void postToast() {
            view.postToast();
        }


        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }

    private class CountObserver implements FollowService.CountingObserver {
       @Override
        public void updateFollowersCount(int count) {
            view.updateFollowerCount(count);
        }

        @Override
        public void updateFolloweeCount(int count) {
            view.updateFolloweeCount(count);
        }

        @Override
        public void handleSuccess(Message data) {

        }

        @Override
        public void updateCount(int count) {

        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }
}
