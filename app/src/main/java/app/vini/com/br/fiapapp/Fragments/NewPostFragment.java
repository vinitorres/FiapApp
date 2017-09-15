package app.vini.com.br.fiapapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import app.vini.com.br.fiapapp.Helper.DatabaseHelper;
import app.vini.com.br.fiapapp.Model.Post;
import app.vini.com.br.fiapapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {


    @BindView(R.id.etTitulo)
    EditText etTitulo;
    @BindView(R.id.etDescricao)
    EditText etDescricao;
    @BindView(R.id.rgGenero)
    RadioGroup rgGenero;
    private ProgressDialog mProgressDialog;

    private Context context;
    private ArrayList<Bitmap> listaImagens;

    public long idAnuncio;

    public ArrayList<String> listaUrl;
    private DatabaseHelper databaseHelper;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, view);
        context = container.getContext();
        mProgressDialog = new ProgressDialog(context);

        databaseHelper = new DatabaseHelper(context);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
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

    private void carregarImagens() {

        listaImagens = databaseHelper.getImages();
        listaUrl = databaseHelper.getUrls();

    }

    @OnClick(R.id.btn_anunciar)
    public void gerarAnuncio() {

        if (etTitulo.getText().length() > 0) {

            String uidUsuario;

            uidUsuario = mAuth.getCurrentUser().getUid();

            String tituloAnuncio;

            tituloAnuncio = etTitulo.getText().toString();

            String generoAnimal;

            int index = rgGenero.indexOfChild(getView().findViewById(rgGenero.getCheckedRadioButtonId()));

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
            String formattedDate = df.format(c.getTime());

            dataAnuncio = formattedDate;

            Post anuncio = new Post(uidUsuario, tituloAnuncio, generoAnimal, descricao, dataAnuncio);

            mDatabase.child(String.valueOf(idAnuncio)).setValue(anuncio);

        }

    }

    public void limparTela(){

        ArrayList<String> coords = databaseHelper.getLocal();

        for (String url : listaUrl) {
            databaseHelper.removerImage(url);
        }
        etTitulo.setText("");
        etDescricao.setText("");
        rgGenero.clearCheck();
        carregarImagens();
    }


}
