package od.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

import od.chat.listener.OnAdapterListener;

/**
 * Created by danila on 26.08.16.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected LayoutInflater mLayoutInflater;
    protected List<T> arrayList;
    protected Context context;
    protected OnAdapterListener listener;

    public BaseAdapter(Context context, List<T> stringList, OnAdapterListener listener) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
        arrayList = stringList;
    }

    public void setStringList(List<T> stringList) {
        this.arrayList = stringList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
