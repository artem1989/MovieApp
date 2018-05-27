package ua.inovecs.movieapp.ui;

import android.content.Context;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

public class ScrollViewListener implements AbsListView.OnScrollListener {

    private final Context context;

    public ScrollViewListener(Context context) {
        this.context = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Picasso picasso = Picasso.get();
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
    }
}
