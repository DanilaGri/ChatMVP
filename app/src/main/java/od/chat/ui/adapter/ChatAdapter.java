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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.listener.OnChatAdapterListener;
import od.chat.model.Chat;

/**
 * Created by danila on 18.09.16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

     private LayoutInflater mLayoutInflater;
    private List<Chat> chatList;
    private Context context;
    private OnChatAdapterListener listener;
    private String userId;

    public ChatAdapter(Context context, List<Chat> chatList, OnChatAdapterListener listener,
                       String userId) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.chatList = chatList;
        this.listener = listener;
        this.userId = userId;
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
        holder.tvUsername.setText(item.getUserName() != null && item.getUserSurname() != null
                ? item.getUserName() + " " + item.getUserSurname() : "");
        holder.tvCreateDate.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
        holder.tvSubscription.setText(item.getDescription() != null ? item.getDescription() : "");
        holder.tvTitle.setText(item.getTitle() != null ? item.getTitle() : "");
        holder.tvCountComment.setText(item.getCommentsCount() != null ? item.getCommentsCount() : "");

        Glide.with(context)
                .load(item.getUserAvatar()).asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(holder.ivIcon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.ivIcon.setImageDrawable(circularBitmapDrawable);
                    }
                });

        Glide.with(context)
                .load(item.getImage()).asBitmap()
                .into(holder.ivPostImage);

        if (item.getUserId().equals(userId)) {
            holder.imgEdit.setVisibility(View.VISIBLE);
            holder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imgEdit.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
        }

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
        @Bind(R.id.iv_post_image)
        ImageView ivPostImage;
        @Bind(R.id.img_delete)
        ImageView imgDelete;
        @Bind(R.id.img_edit)
        ImageView imgEdit;
        @Bind(R.id.rl_comment)
        RelativeLayout rlComment;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_subscription)
        TextView tvSubscription;
        @Bind(R.id.view)
        View view;
        @Bind(R.id.imageButton)
        ImageView imageButton;
        @Bind(R.id.tv_count_comment)
        TextView tvCountComment;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            imageButton.setOnClickListener((View v) -> {
                listener.onClick(chatList.get(getAdapterPosition()).getId());
            });

            imgEdit.setOnClickListener((View v) -> {
                listener.onClick(chatList.get(getAdapterPosition()));
            });

            imgDelete.setOnClickListener((View v) -> {
                listener.deletePost(chatList.get(getAdapterPosition()).getId());
            });

            ivIcon.setOnClickListener((View v) -> {
                listener.viewUser(chatList.get(getAdapterPosition()).getUserId());
            });


        }
    }
}
