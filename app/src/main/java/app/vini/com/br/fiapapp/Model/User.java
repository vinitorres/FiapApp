package app.vini.com.br.fiapapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vinicius on 15/09/17.
 */

public class User implements Parcelable {

    String usuario;
    String senha;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.usuario);
        dest.writeString(this.senha);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.usuario = in.readString();
        this.senha = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
