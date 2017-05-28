package od.chat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import od.chat.di.HasComponent;
import od.chat.di.component.FragmentComponent;
import od.chat.ui.activity.BaseActivity;

/**
 * Created by danila on 12.08.16.
 */
public class BaseFragment extends Fragment implements HasComponent<FragmentComponent> {

    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
    }

    private void initFragmentComponent() {
        BaseActivity activity = (BaseActivity) getActivity();
        fragmentComponent = activity.getComponent().plusFragmentComponent();
    }

    @Override
    public FragmentComponent getComponent() {
        return fragmentComponent;
    }

    public void setupTitle(String title) {
        getActivity().setTitle(title);
    }
}
