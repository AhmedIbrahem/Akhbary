package ahmed.fciibrahem.helwan.edu.eg.akhbary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.akhbary.Model.Article;


public class Adpter extends RecyclerView.Adapter<Adpter.MyViewHolder> {
    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public Adpter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);

        return new MyViewHolder(view, onItemClickListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        final MyViewHolder holder = viewHolder;
        Article model = articles.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(context).load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);

                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);

        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText(Utils.DateToTimeFormat(model.getPublishedAt()) + "\u2022");
        holder.publish_at.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void SetOnClickItemListner(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }


    public interface OnItemClickListener {
        void onItemClick(View view, int posation);


    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description, author, publish_at, time, source;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            publish_at = itemView.findViewById(R.id.publishedAt);
            time = itemView.findViewById(R.id.time);
            source = itemView.findViewById(R.id.source);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());

        }
    }
}
