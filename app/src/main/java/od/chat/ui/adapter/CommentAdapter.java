package od.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.listener.OnCommentAdapterListener;
import od.chat.model.Comment;
import od.chat.ui.adapter.hoder.FooterHolder;

/**
 * Created by danila on 01.10.16.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Comment> commentList;
    private Context context;
    private OnCommentAdapterListener listener;
    private String userId;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public CommentAdapter(Context context, List<Comment> commentList,
                          OnCommentAdapterListener listener, String userId) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.commentList = commentList;
        this.listener = listener;
        this.userId = userId;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_ITEM) {
            view = mLayoutInflater.inflate(R.layout.item_comment, parent, false);
            return new ItemHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = mLayoutInflater.inflate(R.layout.progress, parent, false);
            return new FooterHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            Comment item = commentList.get(position);
            itemHolder.tvUsername.setText(item.getUserName() != null && item.getUserSurname() != null
                    ? item.getUserName() + " " + item.getUserSurname() : "");
            itemHolder.tvCreateDate.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
            itemHolder.tvSubscriptionComment.setText(item.getText() != null ? item.getText() : "");
            Glide.with(context)
                    .load(item.getUserAvatar()).asBitmap()
                    .into(itemHolder.ivIcon);

            if (item.getUserId().equals(userId)) {
                itemHolder.imgEdit.setVisibility(View.VISIBLE);
                itemHolder.imgDelete.setVisibility(View.VISIBLE);
            } else {
                itemHolder.imgEdit.setVisibility(View.GONE);
                itemHolder.imgDelete.setVisibility(View.GONE);
            }
        } else if (holder instanceof FooterHolder) {
            FooterHolder loadingViewHolder = (FooterHolder) holder;
            loadingViewHolder.progressLoadMore.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return commentList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_username)
        TextView tvUsername;
        @Bind(R.id.tv_create_date)
        TextView tvCreateDate;
        @Bind(R.id.img_delete)
        ImageView imgDelete;
        @Bind(R.id.img_edit)
        ImageView imgEdit;
        @Bind(R.id.rl_comment)
        RelativeLayout rlComment;
        @Bind(R.id.tv_subscription_comment)
        TextView tvSubscriptionComment;
        @Bind(R.id.ll_comment)
        LinearLayout llComment;

        public ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            imgDelete.setOnClickListener((View v) -> {
                listener.deleteComment(commentList.get(getAdapterPosition()).getId());
            });

            imgEdit.setOnClickListener((View v) -> {
                listener.editComment(commentList.get(getAdapterPosition()).getId(),
                        commentList.get(getAdapterPosition()).getText());
            });
        }
    }

    public void loadMore(){
        commentList.add(null);
        notifyItemInserted(commentList.size() - 1);
    }

}
