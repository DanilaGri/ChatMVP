package od.chat.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.model.User;
import od.chat.presenter.UpdateUserPresenter;
import od.chat.ui.view.UpdateUserView;
import od.chat.utils.AndroidUtils;
import od.chat.utils.ValidUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserFragment extends BaseFragment implements UpdateUserView {

    public static final String TAG = UpdateUserFragment.class.getSimpleName();

    @Inject
    UpdateUserPresenter presenter;
    @Inject
    AndroidUtils androidUtils;
    @Bind(R.id.email)
    AutoCompleteTextView email;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.password_repeat)
    EditText passwordRepeat;
    @Bind(R.id.name)
    AutoCompleteTextView name;
    @Bind(R.id.sure_name)
    AutoCompleteTextView surname;
    @Bind(R.id.avatar)
    AutoCompleteTextView avatar;
    @Bind(R.id.ll_progress_bar)
    LinearLayout llProgressBar;

    public UpdateUserFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        presenter.setupUserInfo();
        setupTitle(getString(R.string.title_edit_user));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        androidUtils.hideKeyboard(getView());
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
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (ValidUser.setupInputValues(getActivity(), email, password, passwordRepeat, name,
                        surname, avatar)) {
                    llProgressBar.setVisibility(View.VISIBLE);
                    androidUtils.hideKeyboard(getView());
                    presenter.updateUser(email.getText().toString(), password.getText().toString(),
                            name.getText().toString(), surname.getText().toString(),
                            avatar.getText().toString());
                }
        }

        return false;
    }

    @Override
    public void setupUser(User user) {
        password.setText(user.getPassword() != null ? user.getPassword() : "");
        passwordRepeat.setText(user.getPassword() != null ? user.getPassword() : "");
        email.setText(user.getEmail() != null ? user.getEmail() : "");
        name.setText(user.getName() != null ? user.getName() : "");
        surname.setText(user.getSurname() != null ? user.getSurname() : "");
        avatar.setText(user.getAvatar() != null ? user.getAvatar() : "");
    }

    @Override
    public void onSuccessUpdate(User user) {
        EventBus.getDefault().postSticky(user);
        getActivity().onBackPressed();
    }

    @Override
    public void showError() {
        llProgressBar.setVisibility(View.GONE);
    }
}
