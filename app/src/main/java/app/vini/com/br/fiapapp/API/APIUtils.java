package app.vini.com.br.fiapapp.API;

/**
 * Created by Vinicius on 15/09/17.
 */

public class APIUtils {

    public static final String BASE_URL = "http://www.mocky.io/v2/";

    public static UserAPI getUserAPI() {
        return RetrofitClient.getClient(BASE_URL).create(UserAPI.class);
    }
}