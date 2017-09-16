package app.vini.com.br.fiapapp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.vini.com.br.fiapapp.Activities.MainActivity;
import app.vini.com.br.fiapapp.Adapter.MyPostAdapter;
import app.vini.com.br.fiapapp.Model.Post;
import app.vini.com.br.fiapapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostsFragment extends Fragment {

    @BindView(R.id.rvAnuncios)
    RecyclerView rvAnuncios;

    private DatabaseReference mDatabase;

    private Context context;
    private ProgressDialog mProgressDialog;
    private MyPostAdapter anunciosAdapter;
    private ArrayList<Post> listaAnuncios;

    private String currentUid;
    private FirebaseAuth mAuth;

    public MyPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_posts,container,false);
        ButterKnife.bind(this,view);

        context = container.getContext();
        mProgressDialog = new ProgressDialog(context);

        getActivity().setTitle("My Posts");

        listaAnuncios = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();

        if( mAuth.getCurrentUser() != null ) {
            currentUid = mAuth.getCurrentUser().getUid();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("Posts");

        anunciosAdapter = new MyPostAdapter(listaAnuncios, (MainActivity) view.getContext() );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvAnuncios.setLayoutManager(layoutManager);
        rvAnuncios.setAdapter(anunciosAdapter);
        rvAnuncios.setHasFixedSize(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    listaAnuncios.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Post anuncio = child.getValue(Post.class);
                        if (anuncio.getUserUID().equals(currentUid)) {
                            listaAnuncios.add(anuncio);
                            anunciosAdapter.update(listaAnuncios);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}
