package com.avalicaopraticast.avalicaopraticast.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avalicaopraticast.avalicaopraticast.R;
import com.avalicaopraticast.avalicaopraticast.model.Movie;
import com.avalicaopraticast.avalicaopraticast.model.Trailer;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
public class TrailersAdpter extends RecyclerView.Adapter<TrailersAdpter.ViewHolder> {

    private List<Trailer> listaTrailer;
    private Context context;
    private TrailerAdapterListener listener;
    private static String[] status = {"Check in pendente", "Check in pendente", "Check in realizado", "Audiência em andamento", "Check out realizado", "", "Pendente de aceite"};

    public TrailersAdpter(List<Trailer> listaTrailer, Context context, TrailerAdapterListener listener) {
        this.listaTrailer = listaTrailer;
        this.context = context;
        this.listener = listener;
    }

    //Metodo para setar a view em uma viewcard
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Infla uma view com meu layout list_denuncia_item
        final View view = LayoutInflater.from(context).inflate(R.layout.trailer_item_list, parent, false);

        //Seta a view em uma célula
        ViewHolder viewHolder = new ViewHolder(view);

        //ao clicar em um item
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Trailer trailer = listaTrailer.get(position);

                if (listener != null) {
                    listener.onItemSelected(view, trailer);
                }
            }
        });

        return viewHolder;
    }

    //Metodo para atualizar os dados da cardview(celula)
    @Override
    public void onBindViewHolder(TrailersAdpter.ViewHolder holder, int position) {

        Trailer trailer = listaTrailer.get(position);
        holder.textDescriptionTrailer.setText(trailer.getName());
        Picasso.get().load("https://img.youtube.com/vi/"+
                trailer.getKey()+"/0.jpg").into(holder.imgThumbnals);
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listaTrailer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_trailer)
        CardView cardView;
        @BindView(R.id.imageview_thumbnails_trailer)
        ImageView imgThumbnals;
        @BindView(R.id.text_description_trailer)
        TextView textDescriptionTrailer;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface TrailerAdapterListener {
        void onItemSelected(View view, Trailer trailer);
    }
}