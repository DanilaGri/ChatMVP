package od.chat.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.event.UpdateUser;
import od.chat.presenter.MainActivityPresenter;
import od.chat.ui.view.MainActivityView;

public class MainActivity extends BaseActivity implements MainActivityView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    MainActivityPresenter presenter;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getComponent().inject(this);
        setupToolbar("News feed", toolbar);
        setupDrawer(navView, drawerLayout, toolbar);
        presenter.attachView(this);
        presenter.openChatScreen();

    }

    @Subscribe(sticky = true)
    public void updateUser(UpdateUser user) {
        EventBus.getDefault().removeStickyEvent(user);
        setupUserInfo(user.getUser());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
