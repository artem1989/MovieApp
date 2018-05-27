package ua.inovecs.movieapp.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ua.inovecs.movieapp.R;
import ua.inovecs.movieapp.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, GridFragment.Factory.newInstance())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
