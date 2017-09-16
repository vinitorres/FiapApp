package app.vini.com.br.fiapapp.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.vini.com.br.fiapapp.R;


public class SplashScreenActivity extends AppCompatActivity {

    ImageView splashIcon;
    Animation fadeAnimation;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashIcon = (ImageView) findViewById(R.id.ivSplashIcon);

        auth = FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().subscribeToTopic("android");

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.facebook.samples.loginhowto",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

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
}
