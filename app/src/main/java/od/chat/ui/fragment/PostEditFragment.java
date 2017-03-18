package od.chat.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.event.UpdateEvent;
import od.chat.helper.AlertDialogsHelper;
import od.chat.model.Chat;
import od.chat.presenter.PostEditPresenter;
import od.chat.ui.view.PostEditView;
import od.chat.utils.AndroidUtils;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link PostEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostEditFragment extends BaseFragment implements PostEditView {
    private static final String ARG_POST = "arg_post";
    public static final String TAG = PostEditFragment.class.getSimpleName();

    @Bind(R.id.ll_post)
    LinearLayout llPost;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.btn_update)
    Button btnUpdate;
    @Bind(R.id.ll_progress_bar)
    LinearLayout llProgressBar;

    private Chat chat;
    @Bind(R.id.title)
    AutoCompleteTextView title;
    @Bind(R.id.subscription)
    AutoCompleteTextView subscription;
    @Bind(R.id.url)
    AutoCompleteTextView imageUrl;

    @Inject
    AndroidUtils androidUtils;
    @Inject
    PostEditPresenter presenter;
    @Inject
    AlertDialogsHelper dialogsHelper;

    public PostEditFragment() {
        // Required empty public constructor
    }

    public static PostEditFragment newInstance(Chat chat) {
        PostEditFragment fragment = new PostEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_POST, chat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chat = getArguments().getParcelable(ARG_POST);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_view, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        llProgressBar.setVisibility(View.GONE);
        if (chat != null) {
            setupTitle("Редактирование поста");
            subscription.setText(chat.getDescription() != null ? chat.getDescription() : "");
            title.setText(chat.getTitle() != null ? chat.getTitle() : "");
            imageUrl.setText(chat.getImage() != null ? chat.getImage() : "");
        } else {
            setupTitle("Добавление поста");
        }

        return view;
    }

    @Override
    public void onDetach() {
        presenter.detachView();
        super.onDetach();
    }

    @Override
    public void onPause() {
        androidUtils.hideKeyboard(getView());
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (chat != null) {
                    if (chekInputText()) {
                        presenter.edit(chat.getId(), title.getText().toString(),
                                subscription.getText().toString(), imageUrl.getText().toString());
                    }
                } else {
                    if (chekInputText()) {
                        presenter.add(title.getText().toString(),
                                subscription.getText().toString(), imageUrl.getText().toString());
                    }
                }
            default:
                break;
        }

        return false;
    }

    private boolean chekInputText() {
        if (TextUtils.isEmpty(title.getText())) {
            dialogsHelper.errorTxtMsg(getString(R.string.error_msg_post_name));
            return false;
        } else if (TextUtils.isEmpty(subscription.getText())) {
            dialogsHelper.errorTxtMsg(getString(R.string.error_msg_post_subscription));
            return false;
        }
        return true;
    }

    @Override
    public void showError() {
        llPost.setVisibility(View.VISIBLE);
        llProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoad() {
        llPost.setVisibility(View.GONE);
        llProgressBar.setVisibility(View.VISIBLE);
        androidUtils.hideKeyboard(getView());
    }

    @Override
    public void update() {
        EventBus.getDefault().postSticky(new UpdateEvent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
