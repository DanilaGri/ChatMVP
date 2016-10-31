package od.chat.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.presenter.StartActivityPresenter;
import od.chat.ui.view.StartActivityView;


/**
 * A login screen that offers login via email/password.
 */
public class StartActivity extends BaseActivity implements StartActivityView {


    @Inject
    StartActivityPresenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        ButterKnife.bind(this);
        getComponent().inject(this);
        presenter.attachView(this);
        presenter.start();
        setupToolbar("", toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}

