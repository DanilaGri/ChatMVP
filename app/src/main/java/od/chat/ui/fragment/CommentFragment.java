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

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import od.chat.R;
import od.chat.listener.OnAdapterListener;
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
public class CommentFragment extends BaseFragment implements CommentView, OnAdapterListener {
    private static final String ARG_ID = "ARG_ID";
    public static final String TAG = CommentFragment.class.getSimpleName();
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.swipe_chat)
    SwipeRefreshLayout swipeChat;
    @Bind(R.id.et_add_comment)
    EditText etAddComment;
    @Bind(R.id.iv_send)
    ImageView ivSend;
    private String id;

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
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
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
        presenter.loadComments(id);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeChat.setOnRefreshListener(() -> {
            presenter.loadComments(id);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showComments(List<Comment> commentList) {
        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), commentList, this);
        rvChat.setAdapter(commentAdapter);
        swipeChat.setRefreshing(false);
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
        swipeChat.setRefreshing(true);
    }

    @Override
    public void showError() {
        swipeChat.setRefreshing(false);
        androidUtils.hideKeyboard(getView());
    }

    @Override
    public <T> void onClick(T t) {

    }

    @OnClick({R.id.et_add_comment, R.id.iv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send:
                presenter.sendComment(etAddComment.getText().toString());
                break;
        }
    }
}
