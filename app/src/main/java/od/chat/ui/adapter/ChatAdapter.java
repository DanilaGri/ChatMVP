package od.chat.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.listener.OnAdapterListener;
import od.chat.model.Chat;

/**
 * Created by danila on 18.09.16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Chat> chatList;
    private Context context;
    private OnAdapterListener listener;

    public ChatAdapter(Context context, List<Chat> chatList, OnAdapterListener listener) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.chatList = chatList;
        this.listener = listener;
    }

    public void setDoctorList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat item = chatList.get(position);
        holder.tvUsername.setText(item.getUserSurname() != null ? item.getUserSurname() : "");
        holder.tvCreateDate.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
        holder.tvSubscription.setText(item.getDescription() != null ? item.getDescription() : "");
//        holder.tvUsername.setText(item.getUserSurname() != null ? item.getUserSurname() : "");

        Glide.with(context)
                .load("https://cfcdnpull-creativefreedoml.netdna-ssl.com/wp-content/uploads/2013/03/00-android-4-0_icons.png").asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(holder.ivIcon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivIcon.setImageDrawable(circularBitmapDrawable);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_username)
        TextView tvUsername;
        @Bind(R.id.tv_create_date)
        TextView tvCreateDate;
        @Bind(R.id.tv_subscription)
        TextView tvSubscription;
        @Bind(R.id.view)
        View view;
        @Bind(R.id.imageButton)
        ImageView imageButton;
        @Bind(R.id.tv_count_comment)
        TextView tvCountComment;
        @Bind(R.id.comment)
        LinearLayout comment;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            comment.setOnClickListener((View v) -> {
//                listener.onClick(doctorList.get(getAdapterPosition()));
            });
        }
    }
}
