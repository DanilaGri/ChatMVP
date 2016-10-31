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
    AutoCompleteTextView sureName;
    @Bind(R.id.avatar)
    AutoCompleteTextView avatar;
    @Bind(R.id.ll_user)
    LinearLayout llUser;
    private boolean isSign;
    @Inject
    SignUpPresenter presenter;

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
        setupTitle(isSign ? "Регистрация" : "Личный кабинет");
        return view;
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
                if (setupInputValues()) {
                    presenter.signUp(email.getText().toString(), password.getText().toString(),
                            name.getText().toString(), sureName.getText().toString(),
                            avatar.getText().toString(), isSign);
                }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean setupInputValues() {
        boolean flag;
        String field = "";
        if ("".equals(email.getText().toString())) {
            field = "Email";
            flag = false;
        } else if ("".equals(password.getText().toString())) {
            field = "Пароль";
            flag = false;
        } else if ("".equals(passwordRepeat.getText().toString())) {
            field = "Пароль повторно";
            flag = false;
        } else if ("".equals(name.getText().toString())) {
            field = "имя";
            flag = false;
        } else if ("".equals(sureName.getText().toString())) {
            field = "фамилия";
            flag = false;
        } else if ("".equals(avatar.getText().toString())) {
            field = "аватар";
            flag = false;
        } else {

            flag = true;
        }

        if (!flag) {
            String msg;
            if (password.length() < 4) {
                msg = "Пароль слишком короткий. Введите минимум 4 символа";
            } else if (!(password.getText().toString().equals(passwordRepeat.getText().toString()))) {
                msg = "Пароли не совпадают";
            } else {
                msg = "Заполните поле: " + field;
            }

            new AlertDialog.Builder(getActivity())
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                            }).show();
        }

        return flag;
    }

    @Override
    public void setupUser(User user) {
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        passwordRepeat.setText(user.getPassword());
        name.setText(user.getName());
        sureName.setText(user.getSurname());
        avatar.setText(user.getAvatar());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
