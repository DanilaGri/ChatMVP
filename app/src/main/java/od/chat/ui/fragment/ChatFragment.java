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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
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
    private static final String ARG_PARAM1 = "param1";
    public static final String TAG = ChatFragment.class.getSimpleName();
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.swipe_chat)
    SwipeRefreshLayout swipeChat;
    private String mParam1;

    @Inject
    ChatPresenter presenter;
    private LinearLayoutManager mLayoutManager;

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
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvChat.setLayoutManager(mLayoutManager);
        swipeChat.setOnRefreshListener(() -> {
            presenter.loadChat(0);
        });
        swipeChat.post(() -> {
            swipeChat.setRefreshing(true);
            presenter.loadChat(0);

        });

        rvChat.addOnScrollListener(onScrollListener);
        setupTitle("Посты");
        return view;
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
        if (chatList == null) chatList = new ArrayList<>();
        ChatAdapter chatAdapter = new ChatAdapter(getActivity(), chatList, this);
        rvChat.setAdapter(chatAdapter);
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
    }

    @Override
    public void onStop() {
        swipeChat.setRefreshing(false);
        super.onStop();
    }


    int totalItemCount = 0;

    final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            int position = mLayoutManager.findLastVisibleItemPosition();
//            Log.e("totalItemCountDOooo", String.valueOf(totalItemCount));
//            if (totalItemCount < position) {
//                Log.e("totalItemCount", String.valueOf(totalItemCount));
//                totalItemCount = position;
//                int updatePosition = recyclerView.getAdapter().getItemCount() - 1;
//                Log.e("RecyclerView", String.valueOf(dy));
//                if (position >= updatePosition && dy > 0) {
//                    presenter.loadChat(updatePosition + 6);
//                    swipeChat.setRefreshing(true);
//                }
//            }
            /**      int updatePosition = recyclerView.getAdapter().getItemCount() - 1;
             int visibleItemCount = mLayoutManager.getChildCount();
             int totalItemCount = mLayoutManager.getItemCount();
             int pastVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

             if (pastVisibleItemPosition + visibleItemCount > totalItemCount) {
             presenter.loadChat(updatePosition + 6);
             swipeChat.setRefreshing(true);
             }*/

        }

    };
}
