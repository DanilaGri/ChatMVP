package od.chat.ui.adapter.hoder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;

/**
 * Created by danila on 18.03.17.
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.progressLoadMore)
    public ProgressBar progressLoadMore;

    public FooterHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
