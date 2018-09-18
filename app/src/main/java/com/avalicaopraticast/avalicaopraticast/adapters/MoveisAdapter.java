package com.avalicaopraticast.avalicaopraticast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avalicaopraticast.avalicaopraticast.R;
import com.avalicaopraticast.avalicaopraticast.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoveisAdapter extends BaseAdapter {
    private Context mContext;
    private final List<Movie> movies;
    private String urlConfig;

    public MoveisAdapter(Context c,List<Movie> movies,String urlConfig ) {
        mContext = c;
        this.movies = movies;
        this.urlConfig = urlConfig;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int p) {
        return null;
    }

    @Override
    public long getItemId(int p) {
        return 0;
    }


    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        final Movie movie = movies.get(p);
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.movie_item_list, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.imageview_poster);

        Picasso.get().load(urlConfig+"/w342"+movie.getPoster_path()).into(imageView);
        // View where image is loaded.
        return convertView;
    }
}
