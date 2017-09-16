package app.vini.com.br.fiapapp.Adapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import app.vini.com.br.fiapapp.Activities.MainActivity;
import app.vini.com.br.fiapapp.Fragments.EditPostFragment;
import app.vini.com.br.fiapapp.Model.Post;
import app.vini.com.br.fiapapp.R;

/**
 * Created by Vinicius on 16/09/17.
 */

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder> {

    private List<Post> listaPost;
    MainActivity main;


    public MyPostAdapter(List<Post> listaAnuncios, MainActivity activity) {

        this.listaPost = listaAnuncios;
        this.main = activity;
    }

    public MyPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_anuncio, parent, false);

        return new MyPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPostViewHolder holder, final int position) {

        Post post = listaPost.get(position);

        String titulo = post.titulo;
        String genero = post.genero;
        String descricao = post.descricao;
        String data = post.dataCriacao;

        String pushKey = post.pushKey;

        holder.tvTituloAnuncio.setText(titulo);
        holder.tvGeneroAnuncio.setText(genero);
        holder.tvDataAnuncio.setText(data);
        holder.tvDescricaoAnuncio.setText(descricao);
        holder.pushKey = pushKey;
        holder.post = post;


    }

    @Override
    public int getItemCount() {
        return listaPost.size();
    }


    public class MyPostViewHolder extends RecyclerView.ViewHolder {

        //ImageView imageView;
        TextView tvTituloAnuncio;
        TextView tvGeneroAnuncio;
        TextView tvDataAnuncio;
        TextView tvDescricaoAnuncio;

        String pushKey;

        Post post;

        public MyPostViewHolder(final View itemView) {
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
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Posts");

                                    database.child(pushKey).removeValue();

                                }
                            })
                            .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    EditPostFragment editPostFragment = new EditPostFragment();

                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("Post", post);

                                    editPostFragment.setArguments(bundle);

                                    main.getSupportFragmentManager().beginTransaction().replace(
                                            R.id.content_main, editPostFragment).addToBackStack(null).commit();

                                }
                            });

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