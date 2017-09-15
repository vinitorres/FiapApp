package app.vini.com.br.fiapapp.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import app.vini.com.br.fiapapp.API.APIUtils;
import app.vini.com.br.fiapapp.API.UserAPI;
import app.vini.com.br.fiapapp.Model.User;
import app.vini.com.br.fiapapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreenActivity extends AppCompatActivity {

    ImageView splashIcon;
    Animation fadeAnimation;

    FirebaseAuth auth;

    private UserAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashIcon = (ImageView) findViewById(R.id.ivSplashIcon);

        auth = FirebaseAuth.getInstance();

        mService = APIUtils.getUserAPI();

        loadUser();

        loadAnimation();

    }

    private void loadAnimation(){

        fadeAnimation = AnimationUtils.loadAnimation(this,R.anim.splash_screen_animation);
        fadeAnimation.reset();

        if(splashIcon != null){
            splashIcon.clearAnimation();
            splashIcon.startAnimation(fadeAnimation);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (auth.getCurrentUser() != null) {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(mainIntent);
                    SplashScreenActivity.this.finish();
                } else {
                    Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(loginIntent);
                    SplashScreenActivity.this.finish();
                }
            }
        },3000);

    }

    private void loadUser(){

        mService.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.e("response:", response.body().toString());
                } else {
                    System.out.print(response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

}
