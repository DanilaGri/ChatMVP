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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.listener.OnChatAdapterListener;
import od.chat.model.Chat;
import od.chat.ui.adapter.hoder.FooterHolder;

/**
 * Created by danila on 18.09.16.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

//    private boolean isLoading;
//    private int visibleThreshold = 5;
//    private int lastVisibleItem, totalItemCount;

    private LayoutInflater mLayoutInflater;
    private List<Chat> chatList = new ArrayList<>();
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

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_ITEM) {
            view = mLayoutInflater.inflate(R.layout.item_chat, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = mLayoutInflater.inflate(R.layout.progress, parent, false);
            return new FooterHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Chat item = chatList.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvUsername.setText(item.getUserName() != null && item.getUserSurname() != null
                    ? item.getUserName() + " " + item.getUserSurname() : "");
            viewHolder.tvCreateDate.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
            viewHolder.tvSubscription.setText(item.getDescription() != null ? item.getDescription() : "");
            viewHolder.tvTitle.setText(item.getTitle() != null ? item.getTitle() : "");
            viewHolder.tvCountComment.setText(item.getCommentsCount() != null ? item.getCommentsCount() : "");

            Glide.with(context)
                    .load(item.getUserAvatar()).asBitmap().centerCrop()
                    .into(new BitmapImageViewTarget(viewHolder.ivIcon) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            viewHolder.ivIcon.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            Glide.with(context)
                    .load(item.getImage()).asBitmap()
                    .into(viewHolder.ivPostImage);

            if (item.getUserId().equals(userId)) {
                viewHolder.imgEdit.setVisibility(View.VISIBLE);
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imgEdit.setVisibility(View.GONE);
                viewHolder.imgDelete.setVisibility(View.GONE);
            }
        } else if (holder instanceof FooterHolder) {
            FooterHolder loadingViewHolder = (FooterHolder) holder;
            loadingViewHolder.progressLoadMore.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return chatList == null ? 0 : chatList.size();
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

    public void loadMore(){
        chatList.add(null);
        notifyItemInserted(chatList.size() - 1);
    }

    public void deleteLoadMore(){
//        chatList.remove(chatList.size() - 1);
//        notifyItemRemoved(chatList.size());
    }
}
