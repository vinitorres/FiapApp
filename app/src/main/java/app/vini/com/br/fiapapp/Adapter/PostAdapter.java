package app.vini.com.br.fiapapp.Adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.vini.com.br.fiapapp.Model.Post;
import app.vini.com.br.fiapapp.R;


/**
 * Created by Vini on 24/07/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> listaPost;

    public PostAdapter(List<Post> listaAnuncios) {
        this.listaPost = listaAnuncios;
    }

    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_anuncio, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int position) {

        String titulo = listaPost.get(position).titulo;
        String genero = listaPost.get(position).genero;
        String descricao = listaPost.get(position).descricao;
        String data = listaPost.get(position).dataCriacao;

        holder.tvTituloAnuncio.setText(titulo);
        holder.tvGeneroAnuncio.setText(genero);
        holder.tvDataAnuncio.setText(data);
        holder.tvDescricaoAnuncio.setText(descricao);


    }

    @Override
    public int getItemCount() {
        return listaPost.size();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvTituloAnuncio;
        TextView tvGeneroAnuncio;
        TextView tvDataAnuncio;
        TextView tvDescricaoAnuncio;

        public PostViewHolder(final View itemView) {
            super(itemView);

            tvTituloAnuncio = (TextView) itemView.findViewById(R.id.tvTituloAnuncio);
            tvGeneroAnuncio = (TextView) itemView.findViewById(R.id.tvGeneroAnuncio);
            tvDataAnuncio = (TextView) itemView.findViewById(R.id.tvDataAnuncio);
            tvDescricaoAnuncio = (TextView) itemView.findViewById(R.id.tvDescricaoAnuncio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("Atenção")
                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:11592342399"));
                                    itemView.getContext().startActivity(intent);
                                }
                            })
                            .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                    whatsappIntent.setType("text/plain");
                                    whatsappIntent.setPackage("com.whatsapp");
                                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Venha conhecer o Posts");
                                    try {
                                        itemView.getContext().startActivity(whatsappIntent);
                                    } catch (android.content.ActivityNotFoundException ex) {
                                        Toast.makeText(itemView.getContext(),"Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    // Create the AlertDialog object and return it
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }

    }

    public void update(List<Post> listaAnuncios) {
        this.listaPost = listaAnuncios;
        notifyDataSetChanged();
    }

}
