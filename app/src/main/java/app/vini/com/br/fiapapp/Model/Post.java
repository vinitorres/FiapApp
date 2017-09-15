package app.vini.com.br.fiapapp.Model;

/**
 * Created by Vinicius on 15/09/17.
 */

public class Post {

    public String userUID;
    public String titulo;
    public String genero;
    public String descricao;
    public String dataCriacao;
    //public String latitude;
    //public String longitude;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(String userUID, String titulo, String genero,String descricao,String dataCriacao, String latitude, String longitude) {
        this.userUID = userUID;
        this.titulo = titulo;
        this.genero = genero;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
//        this.latitude = latitude;
//        this.longitude = longitude;
    }

}
