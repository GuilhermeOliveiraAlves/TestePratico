package com.avalicaopraticast.avalicaopraticast.activitys;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avalicaopraticast.avalicaopraticast.R;
import com.avalicaopraticast.avalicaopraticast.Utils.Constantes;
import com.avalicaopraticast.avalicaopraticast.adapters.MoveisAdapter;
import com.avalicaopraticast.avalicaopraticast.adapters.TrailersAdpter;
import com.avalicaopraticast.avalicaopraticast.apis.ThemoviedbApi;
import com.avalicaopraticast.avalicaopraticast.model.Movie;
import com.avalicaopraticast.avalicaopraticast.model.MovieJson;
import com.avalicaopraticast.avalicaopraticast.model.Trailer;
import com.avalicaopraticast.avalicaopraticast.model.TrailerJson;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends Activity implements TrailersAdpter.TrailerAdapterListener  {
    @BindView(R.id.imageview_details_backdrop)
    ImageView detailsBackdrop;
    @BindView(R.id.imageview_details_poster)
    ImageView detailsPoster;
    @BindView(R.id.textTitleMovie)
    TextView textTitleMovie;
    List<Trailer> listaTrailers;
    @BindView(R.id.recyclerViewTrailers)
        RecyclerView recyclerViewTrialers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        Movie movieSelected = gson.fromJson(getIntent().getStringExtra("movie"), Movie.class);
        Picasso.get().load(getIntent().getStringExtra("urlconfig")+"/w780"+movieSelected.getBackdrop_path()).into(detailsBackdrop);
        Picasso.get().load(getIntent().getStringExtra("urlconfig")+"/w300"+movieSelected.getPoster_path()).into(detailsPoster);
        textTitleMovie.setText(movieSelected.getTitle());
        GetTrailes(""+movieSelected.getId());
    }
    public void  GetTrailes(String idMovie){
        Call<TrailerJson> call = ThemoviedbApi.getApi("http://api.themoviedb.org").getTrailers(idMovie, Constantes.mykey);
        call.enqueue(new Callback<TrailerJson>() {
            @Override
            public void onResponse(Call<TrailerJson> call, Response<TrailerJson> response) {
                if (response.code() == 200) {
                    listaTrailers = response.body().results;
                    TrailersAdpter prazoAdapter = new TrailersAdpter(listaTrailers, MovieDetailsActivity.this, MovieDetailsActivity.this);
                    RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewTrialers.setLayoutManager(mLayoutManager);
                    recyclerViewTrialers.setAdapter(prazoAdapter);

                } else {

                }
            }
            @Override
            public void onFailure(Call<TrailerJson> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });
    }

    @Override
    public void onItemSelected(View view, Trailer trailer) {

        watchYoutubeVideo(trailer.getKey());
    }
    public  void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" +id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
