package app.vini.com.br.fiapapp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import app.vini.com.br.fiapapp.Model.Post;
import app.vini.com.br.fiapapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPostFragment extends Fragment {


    @BindView(R.id.etTitulo)
    EditText etTitulo;
    @BindView(R.id.etDescricao)
    EditText etDescricao;
    @BindView(R.id.rgGenero)
    RadioGroup rgGenero;

    private Context context;

    public long idAnuncio;


    public Post post;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public EditPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, view);
        context = container.getContext();

        getActivity().setTitle("Edit Post");

        Bundle bundle = getArguments();

        post = bundle.getParcelable("Post");

        loadOldValues();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Posts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        idAnuncio = Long.parseLong(child.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    public void loadOldValues() {
        etTitulo.setText(post.getTitulo());
        etDescricao.setText(post.getDescricao());
        rgGenero.getCheckedRadioButtonId();

        if (post.getGenero().equals("M")) {
            rgGenero.check(R.id.rb_macho);
        } else {
            rgGenero.check(R.id.rb_femea);
        }
    }

    @OnClick(R.id.btn_anunciar)
    public void updatePost() {

        if (etTitulo.getText().length() > 0) {

            String uidUsuario;

            uidUsuario = "";
            if( mAuth.getCurrentUser() != null ) {
                uidUsuario = mAuth.getCurrentUser().getUid();
            }

            String tituloAnuncio;

            tituloAnuncio = etTitulo.getText().toString();

            String generoAnimal;

            int index = 0;
            if( getView() != null ) {
                index = rgGenero.indexOfChild(getView().findViewById(rgGenero.getCheckedRadioButtonId()));
            }

            if (index == 0) {
                generoAnimal = "M";
            } else if (index == 1) {
                generoAnimal = "F";
            } else {
                generoAnimal = "";
            }

            String descricao;

            descricao = etDescricao.getText().toString();

            String dataAnuncio;

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            dataAnuncio = df.format(c.getTime());

            Post anuncio = new Post(uidUsuario, tituloAnuncio, generoAnimal, descricao, dataAnuncio, post.pushKey);

            mDatabase.child(post.pushKey).setValue(anuncio);

            back();

        }

    }

    public void back(){

        getFragmentManager().popBackStack();

    }

}

