package od.chat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import od.chat.R;
import od.chat.listener.OnAdapterListener;
import od.chat.model.Comment;

/**
 * Created by danila on 01.10.16.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Comment> commentList;
    private Context context;
    private OnAdapterListener listener;

    public CommentAdapter(Context context, List<Comment> commentList, OnAdapterListener listener) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.commentList = commentList;
        this.listener = listener;
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
        holder.tvCreateDateComment.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
        holder.tvTxtComment.setText(item.getText() != null ? item.getText() : "");
        holder.tvCreateDateComment.setText(item.getTimestamp() != null ? item.getTimestamp() : "");
        Glide.with(context)
                .load(item.getUserAvatar()).asBitmap()
                .into(holder.ivComment);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_comment)
        ImageView ivComment;
        @Bind(R.id.tv_username)
        TextView tvUsername;
        @Bind(R.id.tv_create_date_comment)
        TextView tvCreateDateComment;
        @Bind(R.id.tv_txt_comment)
        TextView tvTxtComment;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            comment.setOnClickListener((View v) -> {
////                listener.onClick(doctorList.get(getAdapterPosition()));
//            });
        }
    }
}
