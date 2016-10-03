package od.chat.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import od.chat.R;
import od.chat.presenter.LoginActivityPresenter;
import od.chat.ui.view.LoginActivityView;
import od.chat.utils.AndroidUtils;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginActivityView {

    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    @Bind(R.id.email)
    AutoCompleteTextView emailView;
    @Bind(R.id.password)
    EditText passwordView;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.email_login_form)
    LinearLayout loginFormView;
    @Bind(R.id.login_form)
    ScrollView loginForm;

    @Inject
    LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_login);
        ButterKnife.bind(this);
        getComponent().inject(this);
        presenter.attachView(this);
        presenter.start();
        passwordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.email || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(view -> attemptLogin());
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.|| !isPasswordValid(password)
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            presenter.login(email, password);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick(R.id.login_button)
    public void onClick() {
        attemptLogin();
        AndroidUtils.hideKeyboard(getCurrentFocus());
    }

    @Override
    public void showError() {
        showProgress(false);
    }

    @OnClick(R.id.tv_sign_up)
    public void onSignClick() {
        presenter.signUp();
    }
}

