package app.vini.com.br.fiapapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicius on 15/09/17.
 */

public class Post implements Parcelable {

    public String userUID;
    public String titulo;
    public String genero;
    public String descricao;
    public String dataCriacao;
    public String pushKey;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(String userUID, String titulo, String genero, String descricao, String dataCriacao, String pushKey) {
        this.userUID = userUID;
        this.titulo = titulo;
        this.genero = genero;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.pushKey = pushKey;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userUID);
        dest.writeString(this.titulo);
        dest.writeString(this.genero);
        dest.writeString(this.descricao);
        dest.writeString(this.dataCriacao);
        dest.writeString(this.pushKey);
    }

    protected Post(Parcel in) {
        this.userUID = in.readString();
        this.titulo = in.readString();
        this.genero = in.readString();
        this.descricao = in.readString();
        this.dataCriacao = in.readString();
        this.pushKey = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPushKey() {return pushKey; }

    public void setPushKey(String pushKey) { this.pushKey = pushKey; }
}
