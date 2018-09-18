package com.avalicaopraticast.avalicaopraticast.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import com.avalicaopraticast.avalicaopraticast.R;
import com.avalicaopraticast.avalicaopraticast.Utils.Constantes;
import com.avalicaopraticast.avalicaopraticast.Utils.EndlessScrollListener;
import com.avalicaopraticast.avalicaopraticast.adapters.MoveisAdapter;
import com.avalicaopraticast.avalicaopraticast.apis.ThemoviedbApi;
import com.avalicaopraticast.avalicaopraticast.model.Config;
import com.avalicaopraticast.avalicaopraticast.model.Movie;
import com.avalicaopraticast.avalicaopraticast.model.MovieJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
private Config configuracoes;
private MovieJson listaFilmes;
private List<Movie> listaFilmesFiltrados;
private MoveisAdapter adapter;
private EndlessScrollListener scrollListener;
private String sort = "popularity.desc";
private  String urlConfig ="";
    @BindView(R.id.grid)
    GridView gridFilmes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        listaFilmesFiltrados = new ArrayList<Movie>();
        GetConfig();
        scrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                GetMovies(page,sort);
                return true;
            }
        };
        gridFilmes.setOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sort_popularity) {
            scrollListener.resetState();
            sort = "popularity.desc";
            GetMovies(1,sort);
        }else if(id == R.id.action_sort_vote_average){
            scrollListener.resetState();
            sort = "vote_average.desc";
            GetMovies(1,sort);
        }

        return super.onOptionsItemSelected(item);
    }

    public void  GetConfig(){
        Call<Config> call = ThemoviedbApi.getApi("http://api.themoviedb.org").getConfiguracoes(Constantes.mykey);
        call.enqueue(new Callback<Config>() {
            @Override
            public void onResponse(Call<Config> call, Response<Config> response) {
                if (response.code() == 200) {
                    configuracoes = response.body();
                    urlConfig = configuracoes.images.base_url;
                    GetMovies(1,sort);
                } else {

                }
            }
            @Override
            public void onFailure(Call<Config> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });
    }

    public void  GetMovies(final int page,String sort){
        Call<MovieJson> call = ThemoviedbApi.getApi("http://api.themoviedb.org").getMovies(page+"",sort, Constantes.mykey);
        call.enqueue(new Callback<MovieJson>() {
            @Override
            public void onResponse(Call<MovieJson> call, Response<MovieJson> response) {
                if (response.code() == 200) {
                    if(page == 1){
                        listaFilmesFiltrados= response.body().results;
                        adapter = new MoveisAdapter(getApplicationContext(),listaFilmesFiltrados,urlConfig);
                        gridFilmes.setAdapter(adapter);
                    }else{
                        for (Movie item:response.body().results
                             ) {
                            listaFilmesFiltrados.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<MovieJson> call, Throwable t) {
                System.out.println(t.getCause());
            }
        });
    }

    @OnItemClick(R.id.grid)
    public void onItemClick(AdapterView<?> parent, int position) {
        Movie movieSelected  = listaFilmesFiltrados.get(position);
        Intent i = new Intent (this, MovieDetailsActivity.class);
        Gson gson = new Gson();
        String myJson = gson.toJson(movieSelected);
        i.putExtra ("movie", myJson);
        i.putExtra ("urlconfig", urlConfig);
        startActivity (i);
    }

}

