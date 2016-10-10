package od.chat.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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
import od.chat.presenter.PrivateCabinetPresenter;
import od.chat.ui.view.PrivateCabinetView;

/**
 * A simple {@link BaseFragment} subclass.
 * create an instance of this fragment.
 */
public class PrivateCabinetFragment extends BaseFragment implements PrivateCabinetView {

    public static final String TAG = PrivateCabinetFragment.class.getSimpleName();

    @Inject
    PrivateCabinetPresenter presenter;
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

    public PrivateCabinetFragment() {
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
        presenter.loadUser();
        setupTitle("Личный кабинет");
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
        inflater.inflate(R.menu.private_cabinet_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                presenter.editUser();
            case R.id.menu_delete:
                presenter.deleteUser();
            default:
                break;
        }

        return false;
    }

    @Override
    public void showUser(User user) {
        password.setVisibility(View.GONE);
        passwordRepeat.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        setupUserParam(user);
        email.setEnabled(false);
        name.setEnabled(false);
        sureName.setEnabled(false);
        avatar.setEnabled(false);

    }

    private void setupUserParam(User user) {
        email.setText(user.getEmail() != null ? user.getEmail() : "");
        name.setText(user.getName()!= null ? user.getName() : "");
        sureName.setText(user.getSurname()!= null ? user.getSurname() : "");
        avatar.setText(user.getAvatar()!= null ? user.getAvatar() : "");
    }

    @Override
    public void updateUser(User user) {
        email.setEnabled(true);
        name.setEnabled(true);
        sureName.setEnabled(true);
        avatar.setEnabled(true);
        setupUserParam(user);
    }
}
