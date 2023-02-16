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

    public void unfollowUser(User selectedUser) {
       followService.unfollowUser(selectedUser, new FollowObserver());
    }

    public void followUser(User selectedUser) {
      followService.followUser(selectedUser, new FollowObserver());
    }

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

        void logoutUser();

        void postToast();

        void updateRelationship(boolean isFollowing);
    }

    private class FollowingObserver implements FollowService.RelObserver {

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

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleSuccess(Boolean param) {
            view.updateRelationship(param);
        }
    }

    private class LogoutObserver implements UserService.LogOutObserver {

        @Override
        public void logoutToast() {
            view.setLogoutToast();
        }

        @Override
        public void handleSuccess() {
            view.logoutUser();
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
        public void handleSuccess(int count) {
            view.updateFolloweeCount(count);
            view.updateFollowerCount(count);
        }

        @Override
        public void displayMessage(String message) {
            view.displayMessage(message);
        }
    }

    private class FollowObserver implements FollowService.SimpleObserver {
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
}
