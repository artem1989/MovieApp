package ua.inovecs.movieapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ua.inovecs.movieapp.R;
import ua.inovecs.movieapp.model.Video;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private List<Video> videos;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        ViewHolder(View viewGroup) {
            super(viewGroup);
            this.title = viewGroup.findViewById(R.id.title);
            this.description = viewGroup.findViewById(R.id.text);
        }

        void bindData(final Video video) {
            title.setText(video.getName());
            description.setText(video.getType());
        }
    }

    TrailersAdapter(List<Video> videoList) {
        videos = videoList;
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        ViewHolder vh = new ViewHolder(viewGroup);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
