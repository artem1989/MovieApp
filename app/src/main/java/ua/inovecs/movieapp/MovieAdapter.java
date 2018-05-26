package ua.inovecs.movieapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class MovieAdapter extends BaseAdapter {

    private final Context context;
    private final List<Movie> movies;

    MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(context);
            view.setScaleType(CENTER_CROP);
            view.setAdjustViewBounds(true);
        }

        // Get the image URL for the current position.
        Movie movie = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.get().load(Data.BASE + movie.getBackDropPath()) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view);

        return view;
    }

    @Override public int getCount() {
        return movies.size();
    }

    @Override public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}
