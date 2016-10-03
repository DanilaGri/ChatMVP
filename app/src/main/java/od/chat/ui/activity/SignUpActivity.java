package od.chat.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.presenter.SignUpPresenter;
import od.chat.ui.view.SignUpView;

public class SignUpActivity extends BaseActivity implements SignUpView {

    @Inject
    SignUpPresenter presenter;
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
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Inflate the layout for this fragment
        ButterKnife.bind(this);
        setupToolbar("Регистрация", toolbar);
        getComponent().inject(this);
        presenter.attachView(this);
    }


    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
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

            new AlertDialog.Builder(this)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok,
                            (dialog, which) -> {
                            }).show();
        }

        return flag;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_save:
                if (setupInputValues()) {
                    presenter.signUp(email.getText().toString(), password.getText().toString(),
                            name.getText().toString(), sureName.getText().toString(),
                            avatar.getText().toString());
                }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
