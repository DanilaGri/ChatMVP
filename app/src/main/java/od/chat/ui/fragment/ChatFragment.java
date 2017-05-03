package od.chat.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import od.chat.R;
import od.chat.event.UpdateEvent;
import od.chat.helper.AlertDialogsHelper;
import od.chat.listener.OnAdapterListener;
import od.chat.listener.OnChatAdapterListener;
import od.chat.model.Chat;
import od.chat.presenter.ChatPresenter;
import od.chat.ui.adapter.ChatAdapter;
import od.chat.ui.view.ChatView;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends BaseFragment implements ChatView, OnAdapterListener, OnChatAdapterListener {
    public static final String TAG = ChatFragment.class.getSimpleName();
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.swipe_chat)
    SwipeRefreshLayout swipeChat;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.ll_progress_bar)
    LinearLayout llProgressBar;
    private boolean isFromCache = false;
    private boolean isLoading = false;
    public static final int PAGE_SIZE = 2;
    private ChatAdapter chatAdapter;
    @Inject
    ChatPresenter presenter;
    private LinearLayoutManager mLayoutManager;

    @Inject
    AlertDialogsHelper alertDialogsHelper;

    public ChatFragment() {
        // Required empty public constructor
    }
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        progress.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(getActivity());
        chatAdapter
                = new ChatAdapter(getActivity(), new ArrayList<>(), this, presenter.getUserId());
        rvChat.setAdapter(chatAdapter);
        rvChat.setLayoutManager(mLayoutManager);
        swipeChat.setOnRefreshListener(() -> {
            presenter.loadChat(0, false, true);
        });
        swipeChat.post(() -> {
            swipeChat.setRefreshing(true);
            presenter.loadChat(0, isFromCache, true);
            isFromCache = true;
        });

        rvChat.addOnScrollListener(onScrollListener);
        setupTitle(getString(R.string.title_chat));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onStop() {
        swipeChat.setRefreshing(false);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                presenter.addPost();
            default:
                break;
        }

        return false;
    }

    @Override
    public void showChat(List<Chat> chatList) {
        if (chatList == null) {
            chatList = new ArrayList<>();
        }
        isLoading = !(chatList.size() <= 20);
        chatAdapter.setChatList(chatList);
        swipeChat.setRefreshing(false);
    }

    @Override
    public <T> void onClick(T t) {
        if (t instanceof String) {
            presenter.openComments((String) t);
        } else if (t instanceof Chat) {
            presenter.viewPost((Chat) t);
        }
    }

    @Override
    public void deletePost(String id) {
        swipeChat.setRefreshing(true);
        presenter.deletePost(id);
    }

    @Override
    public void viewUser(String id) {
        presenter.readUser(id);
    }

    @Override
    public void showError() {
        swipeChat.setRefreshing(false);
        tvNoData.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
        llProgressBar.setVisibility(View.VISIBLE);
    }

    @Subscribe(sticky = true)
    public void onUpdateEvent(UpdateEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        presenter.loadChat(0, false,true);
    }


    final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading ) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    chatAdapter.loadMore();
                    presenter.loadChat(totalItemCount, false, false);
                    isLoading = true;
                }
            }
        }



    };

    @OnClick(R.id.btn_update)
    public void onClick() {
        presenter.loadChat(0, false, true);
        llProgressBar.setVisibility(View.GONE);
        swipeChat.setRefreshing(true);
    }
}
