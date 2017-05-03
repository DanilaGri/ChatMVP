package od.chat.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.model.User;
import od.chat.presenter.SignUpPresenter;
import od.chat.ui.view.SignUpView;
import od.chat.utils.AndroidUtils;
import od.chat.utils.ValidUser;

/**
 * Created by danila on 22.10.16.
 */

public class SignUpFragment extends BaseFragment implements SignUpView {
    public static final String TAG = SignUpFragment.class.getSimpleName();
    private static final String IS_SIGN = "isSign";
    @Bind(R.id.progress)
    ProgressBar progress;
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
    @Bind(R.id.ll_user)
    LinearLayout llUser;
    @Bind(R.id.ll_progress_bar)
    LinearLayout llProgressBar;
    private boolean isSign;
    @Inject
    SignUpPresenter presenter;
    @Inject
    AndroidUtils androidUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isSign = getArguments().getBoolean(IS_SIGN);
        }
        setHasOptionsMenu(true);
    }

    public static SignUpFragment newInstance(boolean isSign) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_SIGN, isSign);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        if (!isSign) {
            presenter.setupUserInfo();
        }
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        setupTitle(isSign ? getString(R.string.title_sign_up) :
                getString(R.string.title_private_cabinet));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        androidUtils.hideKeyboard(getView());
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
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
                    androidUtils.hideKeyboard(getView());
                    llProgressBar.setVisibility(View.VISIBLE);
                    presenter.signUp(email.getText().toString(), password.getText().toString(),
                            name.getText().toString(), surname.getText().toString(),
                            avatar.getText().toString(), isSign);
                }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setupUser(User user) {
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        passwordRepeat.setText(user.getPassword());
        name.setText(user.getName());
        surname.setText(user.getSurname());
        avatar.setText(user.getAvatar());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showError() {
        llProgressBar.setVisibility(View.GONE);
    }
}
