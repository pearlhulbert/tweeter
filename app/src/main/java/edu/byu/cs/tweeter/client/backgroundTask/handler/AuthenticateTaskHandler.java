package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateTaskObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticateTaskHandler extends BackgroundTaskHandler<AuthenticateTaskObserver> {

        public AuthenticateTaskHandler(AuthenticateTaskObserver observer) {
            super(observer);
        }

        protected abstract void handleSuccess(Message data, AuthenticateTaskObserver observer);

        protected abstract User getCurrentUser(Message data, AuthenticateTaskObserver observer);

        protected abstract AuthToken getAuthToken(Message data, AuthenticateTaskObserver observer);
}
