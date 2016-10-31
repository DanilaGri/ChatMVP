package od.chat.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.model.Chat;
import od.chat.presenter.PostEditPresenter;
import od.chat.ui.view.PostEditView;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link PostEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostEditFragment extends BaseFragment implements PostEditView {
    private static final String ARG_POST = "arg_post";
    public static final String TAG = PostEditFragment.class.getSimpleName();

    private Chat chat;
    @Bind(R.id.title)
    AutoCompleteTextView title;
    @Bind(R.id.subscription)
    AutoCompleteTextView subscription;
    @Bind(R.id.url)
    AutoCompleteTextView imageUrl;

    @Inject
    PostEditPresenter presenter;

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
                    presenter.edit(chat.getId(), title.getText().toString(),
                            subscription.getText().toString(), imageUrl.getText().toString());
                } else {
                    presenter.add(title.getText().toString(),
                            subscription.getText().toString(), imageUrl.getText().toString());
                }
            default:
                break;
        }

        return false;
    }

    @Override
    public void showError() {

    }
}
