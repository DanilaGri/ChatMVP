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

/**
 * Created by danila on 01.10.16.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Comment> commentList;
    private Context context;
    private OnCommentAdapterListener listener;
    private String userId;

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
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment item = commentList.get(position);
        holder.tvUsername.setText(item.getUserName() != null && item.getUserSurname() != null
                ? item.getUserName() + " " + item.getUserSurname() : "");
        holder.tvCreateDate.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
        holder.tvSubscriptionComment.setText(item.getText() != null ? item.getText() : "");
        Glide.with(context)
                .load(item.getUserAvatar()).asBitmap()
                .into(holder.ivIcon);

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
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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

        public ViewHolder(View view) {
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
}
