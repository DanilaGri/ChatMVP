package od.chat.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import od.chat.R;
import od.chat.event.UpdateEvent;
import od.chat.listener.OnCommentAdapterListener;
import od.chat.model.Comment;
import od.chat.presenter.CommentPresenter;
import od.chat.ui.adapter.CommentAdapter;
import od.chat.ui.view.CommentView;
import od.chat.utils.AndroidUtils;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends BaseFragment implements CommentView, OnCommentAdapterListener {
    private static final String ARGUMENT_ID = "ARGUMENT_ID";
    public static final String TAG = CommentFragment.class.getSimpleName();
    private boolean isLoading = false;
    public static final int PAGE_SIZE = 2;
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.swipe_chat)
    SwipeRefreshLayout swipeChat;
    @Bind(R.id.et_add_comment)
    EditText etAddComment;
    @Bind(R.id.iv_send)
    ImageView ivSend;
    @Bind(R.id.ll_progress_bar)
    LinearLayout llProgressBar;
    @Bind(R.id.tv_no_comments)
    TextView tvNoComments;
    private String id;
    private LinearLayoutManager mLayoutManager;
    private CommentAdapter commentAdapter;
    @Inject
    CommentPresenter presenter;

    @Inject
    AndroidUtils androidUtils;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(String id) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARGUMENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        swipeChat.post(() -> {
            swipeChat.setRefreshing(true);
            presenter.loadComments(id, 0);
        });
        commentAdapter =
                new CommentAdapter(getActivity(), new ArrayList<>(), this, presenter.getUserId());
        rvChat.setAdapter(commentAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvChat.setLayoutManager(mLayoutManager);
        swipeChat.setOnRefreshListener(() -> {
            presenter.loadComments(id,0);
        });
        rvChat.addOnScrollListener(onScrollListener);
        setupTitle(getString(R.string.title_comments));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showComments(List<Comment> commentList) {
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        isLoading = !(commentList.size() <= 20);
        commentAdapter.setCommentList(commentList);
        swipeChat.setRefreshing(false);
        if(commentList.isEmpty()){
            tvNoComments.setVisibility(View.VISIBLE);
        } else {
            tvNoComments.setVisibility(View.GONE);
        }
        swipeChat.setRefreshing(false);
        llProgressBar.setVisibility(View.GONE);
        androidUtils.hideKeyboard(getView());
        etAddComment.setText("");
        etAddComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAddComment.length() > 0) {
                    ivSend.setEnabled(true);
                } else {
                    ivSend.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void showLoad() {
        llProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void update() {
        EventBus.getDefault().postSticky(new UpdateEvent());
    }

    @Override
    public void showError() {
        swipeChat.setRefreshing(false);
        llProgressBar.setVisibility(View.GONE);
        androidUtils.hideKeyboard(getView());
    }

    @OnClick({R.id.et_add_comment, R.id.iv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send:
                presenter.sendComment(etAddComment.getText().toString());
                break;
        }
    }

    @Override
    public void deleteComment(String id) {
        presenter.deleteComment(id);
    }

    @Override
    public void editComment(String id, String text) {
        presenter.editComment(id, text);
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
                    commentAdapter.loadMore();
                    presenter.loadComments(id, totalItemCount);
                    isLoading = true;
                }
            }
        }



    };
}
