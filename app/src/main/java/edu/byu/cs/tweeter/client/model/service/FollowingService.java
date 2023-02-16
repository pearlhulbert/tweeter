package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingService {


    public interface Observer {
        void updateSelectedUserFollowingAndFollowers();
        void updateFollowButton(boolean isFollowing);
        void setFollowButton(boolean b);
        void showErrorMessage(String s);
        void showSuccessMessage(String unfollowed_, User selectedUser);
        void updateFollowRelationship(boolean isFollower);
    }



}