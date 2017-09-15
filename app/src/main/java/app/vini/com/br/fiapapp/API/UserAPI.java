package app.vini.com.br.fiapapp.API;


import java.util.List;

import app.vini.com.br.fiapapp.Model.User;
import retrofit2.Call;
import retrofit2.http.GET;


public interface UserAPI {

    @GET("/58b9b1740f0000b614f09d2f")
    Call<User> getUser();

}