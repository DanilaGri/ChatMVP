package od.chat.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import od.chat.helper.ChatHelper;
import od.chat.model.Chat;
import od.chat.presenter.impl.ChatPresenterImpl;
import od.chat.ui.Navigator;
import od.chat.ui.view.ChatView;
import od.chat.utils.SharedPreferencesUtils;
import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by danila on 30.12.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChatPresenterTest {
    @Mock
    ChatHelper chatHelper;
    @Mock
    SharedPreferencesUtils preferencesUtils;
    @Mock
    Navigator navigator;
    @Mock
    ChatView chatView;

    private ChatPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter =
                new ChatPresenterImpl(preferencesUtils, chatHelper, navigator);
        presenter.attachView(chatView);
    }

    @Test
    public void loadChat() throws Exception {
        List<Chat> chatResponse = new ArrayList<>();
        chatResponse.add(new Chat());

        when(chatHelper.getChat(0)).thenReturn(Observable.just(chatResponse));

        presenter.loadChat(0);
        ArgumentCaptor<ArrayList<Chat>> captor = new ArgumentCaptor<>();
        verify(chatView, never()).showError();
        verify(chatView).showChat(captor.capture());
        assertEquals(chatResponse.size(), captor.getValue().size());
    }

}