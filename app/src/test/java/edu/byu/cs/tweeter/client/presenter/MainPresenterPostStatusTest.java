package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;

public class MainPresenterPostStatusTest {

    private MainPresenter.MainView mockView;
    private StatusService mockStatusService;

    private MainPresenter mainPresenterSpy;

    private void callPostStatus(Answer answer) {
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("test");
    }

    @BeforeEach
    public void setup() {
        mockView = Mockito.mock(MainPresenter.MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));

        // will always work, other won't mock void methods
        // Mockito.doReturn(mockUserService).when(mainPresenterSpy).getUserService();
        // safer, type checking
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);

    }

    @Test
    public void testPostStatus_successful() {

        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.SimpleObserver observer = invocation.getArgument(1);
                observer.handleSuccess();
                return null;
            }
        };

       callPostStatus(answer);
        Mockito.verify(mockView).postToast();
    }

    @Test
    public void testPostStatus_unsuccessful() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.SimpleObserver observer = invocation.getArgument(1);
                observer.displayMessage("error");
                return null;
            }
        };

        callPostStatus(answer);

        Mockito.verify(mockView).displayMessage("error");
    }

    @Test
    public void testLogout_withException() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.SimpleObserver observer = invocation.getArgument(1);
                Exception exception = new Exception("exception");
                observer.displayMessage("exception: " + exception.getMessage());
                return null;
            }
        };

        callPostStatus(answer);

        Mockito.verify(mockView).displayMessage("exception: exception");

    }


}
