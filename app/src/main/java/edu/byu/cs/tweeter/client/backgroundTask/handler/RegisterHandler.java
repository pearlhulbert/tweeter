package edu.byu.cs.tweeter.client.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.backgroundTask.observer.AuthenticateTaskObserver;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.RegisterService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterHandler extends AuthenticateTaskHandler {

    public RegisterHandler(AuthenticateTaskObserver observer) {
        super(observer);
    }
}
