package edu.byu.cs.tweeter.client.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.backgroundTask.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusService {

    public interface Observer {
        void postToast();
        void cancelPostToast();
        void displayExceptionAndLog(String message, Exception ex);
        void displayMessage(String s);
    }

}
