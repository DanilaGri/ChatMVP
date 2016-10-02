package od.chat.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.listener.OnAdapterListener;
import od.chat.model.Chat;
import od.chat.presenter.ChatPresenter;
import od.chat.ui.adapter.ChatAdapter;
import od.chat.ui.view.ChatView;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends BaseFragment implements ChatView, OnAdapterListener {
    private static final String ARG_PARAM1 = "param1";
    public static final String TAG = ChatFragment.class.getSimpleName();
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.swipe_chat)
    SwipeRefreshLayout swipeChat;
    private String mParam1;

    @Inject
    ChatPresenter presenter;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ChatFragment.
     */
    public static ChatFragment newInstance(String param1) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        presenter.loadChat();
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeChat.setOnRefreshListener(() -> {
            presenter.loadChat();
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showChat(List<Chat> chatList) {
        ChatAdapter chatAdapter = new ChatAdapter(getActivity(), chatList, this);
        rvChat.setAdapter(chatAdapter);
        swipeChat.setRefreshing(false);
    }

    @Override
    public <T> void onClick(T t) {
        presenter.openComments(((Chat) t).getId());
    }

    @Override
    public void showError() {
        swipeChat.setRefreshing(false);
    }
}
