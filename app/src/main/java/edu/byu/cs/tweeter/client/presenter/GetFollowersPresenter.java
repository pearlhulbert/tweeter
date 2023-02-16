package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter extends UserPresenter {


    @Override
    protected void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        followService.loadMoreFollowersItems(targetUser, PAGE_SIZE, lastItem, new GetFollowsObserver());
    }

    @Override
    protected String getDescription() {
        return null;
    }

    public interface FollowView extends UserView {

    }

    private FollowService followService;

    private FollowView view;


    public GetFollowersPresenter(FollowView view) {
        this.view = view;
        followService = new FollowService();
    }


}
