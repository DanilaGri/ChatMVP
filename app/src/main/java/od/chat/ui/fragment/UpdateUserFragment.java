package od.chat.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.model.User;
import od.chat.presenter.UpdateUserPresenter;
import od.chat.ui.view.UpdateUserView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserFragment extends BaseFragment implements UpdateUserView {

    public static final String TAG = UpdateUserFragment.class.getSimpleName();

    @Inject
    UpdateUserPresenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
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
        View view = inflater.inflate(R.layout.activity_sign_up, container, false);
        ButterKnife.bind(this, view);
        getComponent().inject(this);
        presenter.attachView(this);
        presenter.setupUserInfo();
        toolbar.setVisibility(View.GONE);
        setupTitle("Edit User");
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
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (setupInputValues()) {
                    presenter.updateUser(email.getText().toString(), password.getText().toString(),
                            name.getText().toString(), sureName.getText().toString(),
                            avatar.getText().toString());
                }
        }

        return false;
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
        password.setText(user.getPassword() != null ? user.getPassword() : "");
        passwordRepeat.setText(user.getPassword() != null ? user.getPassword() : "");
        email.setText(user.getEmail() != null ? user.getEmail() : "");
        name.setText(user.getName()!= null ? user.getName() : "");
        sureName.setText(user.getSurname()!= null ? user.getSurname() : "");
        avatar.setText(user.getAvatar()!= null ? user.getAvatar() : "");
    }
}
