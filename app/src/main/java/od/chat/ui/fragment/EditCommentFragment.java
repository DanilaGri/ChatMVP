package od.chat.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.presenter.EditPresenter;
import od.chat.ui.view.EditView;
import od.chat.utils.AndroidUtils;

/**
 * A simple {@link BaseFragment} subclass.
 */
public class EditCommentFragment extends BaseFragment implements EditView {
    private static final String ARGUMENT_COMMENT_TEXT = "text";
    private static final String ARGUMENT_COMMENT_ID = "id";
    public static final String TAG = EditCommentFragment.class.getSimpleName();
    @Bind(R.id.edit_comment)
    EditText editComment;
    @Bind(R.id.ll_progress_bar)
    LinearLayout llProgressBar;

    private String text;
    private String id;

    @Inject
    EditPresenter presenter;
    @Inject
    AndroidUtils androidUtils;

    public EditCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(ARGUMENT_COMMENT_TEXT);
            id = getArguments().getString(ARGUMENT_COMMENT_ID);
        }
        setHasOptionsMenu(true);
    }

    public static EditCommentFragment newInstance(String id, String text) {
        EditCommentFragment fragment = new EditCommentFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_COMMENT_TEXT, text);
        args.putString(ARGUMENT_COMMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_comment, container, false);
        getComponent().inject(this);
        setupTitle(getString(R.string.title_edit_comment));
        presenter.attachView(this);
        ButterKnife.bind(this, view);
        if (text != null) {
            editComment.setText(text);
            editComment.setSelection(editComment.length());
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        androidUtils.hideKeyboard(getView());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ok:
                editComment();
                break;
            default:
                break;
        }

        return false;
    }

    private void editComment() {
        String txtEdit = editComment.getText().toString();
        if(txtEdit.length() == 0) {
            Toast.makeText(getActivity(), R.string.error_empty_text, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if(txtEdit.equals(text)){
            getActivity().onBackPressed();
        } else {
            llProgressBar.setVisibility(View.VISIBLE);
            presenter.editComment(id, txtEdit);
        }
        androidUtils.hideKeyboard(getView());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess() {
        llProgressBar.setVisibility(View.GONE);
        getActivity().onBackPressed();
    }

    @Override
    public void showError() {
        llProgressBar.setVisibility(View.GONE);
    }
}
