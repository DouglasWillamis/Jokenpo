package com.example.jokenpo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView player;
    private ImageView adversario;
    private MediaPlayer musica;
    private ImageButton pedra;
    private ImageButton papel;
    private ImageButton tesoura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        this.player = findViewById(R.id.player);
        this.adversario = findViewById(R.id.advesario);
        this.pedra = findViewById(R.id.pedra);
        this.papel = findViewById(R.id.papel);
        this.tesoura = findViewById(R.id.tesoura);

        this.musica = MediaPlayer.create(this, R.raw.alex_play);
        this.musica.setLooping(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.musica.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG",this.musica.isPlaying()?"true":"false");
    }

    public void clicar(View view){
        switch (view.getId()){
            case R.id.pedra :
                this.player.setImageResource(R.drawable.pedra);
                this.player.setScaleX(-1);
                this.player.setTag(R.id.pedra);
                animacao();
                break;
            case R.id.papel :
                this.player.setImageResource(R.drawable.papel);
                this.player.setScaleX(-1);
                this.player.setTag(R.id.papel);
                animacao();
                break;
            case R.id.tesoura :
                this.player.setImageResource(R.drawable.tesoura);
                this.player.setScaleX(-1);
                this.player.setTag(R.id.tesoura);
                animacao();
                break;
        }
        desativarBtn();
    }

    public void animacao(){

        final int TEMPO = 3950;
        this.musica.start();
        this.adversario.setImageResource(R.drawable.animacao);
        AnimationDrawable animation = (AnimationDrawable) this.adversario.getDrawable();
        animation.start();

        Animation deslocamento = new TranslateAnimation(0, 0, 0, 0);
        deslocamento.setDuration(3000);
        this.adversario.startAnimation(deslocamento);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                sorteio();
            }
        }, TEMPO);
    }

    public void sorteio() {

        final int TEMPO = 1250;
        int indice = (new Random()).nextInt(3);
        switch (indice) {
            case 0 :
                this.adversario.setImageResource(R.drawable.pedra);
                this.adversario.setTag(R.id.pedra);
                this.musica.pause();
                this.musica.seekTo(0);
                break;
            case 1 :
                this.adversario.setImageResource(R.drawable.papel);
                this.adversario.setTag(R.id.papel);
                this.musica.pause();
                this.musica.seekTo(0);
                break;
            case 2 :
                this.adversario.setImageResource(R.drawable.tesoura);
                this.adversario.setTag(R.id.tesoura);
                this.musica.pause();
                this.musica.seekTo(0);
                break;
        }

        if(this.adversario.getTag().equals(this.player.getTag())){
            Toast.makeText(getApplicationContext(), "Empate!", Toast.LENGTH_LONG).show();
        }
        else if(this.adversario.getTag().equals(R.id.pedra) && this.player.getTag().equals(R.id.tesoura)){
            Toast.makeText(getApplicationContext(), "Você perdeu!", Toast.LENGTH_LONG).show();
        }
        else if (this.adversario.getTag().equals(R.id.tesoura) && this.player.getTag().equals(R.id.papel)){
            Toast.makeText(getApplicationContext(), "Você perdeu!", Toast.LENGTH_LONG).show();
        }
        else if (this.adversario.getTag().equals(R.id.papel) && this.player.getTag().equals(R.id.pedra)){
            Toast.makeText(getApplicationContext(), "Você perdeu!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Você ganhou!", Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                reiniciar();
            }
        }, TEMPO);

    }

    public void reiniciar() {
        this.player.setImageResource(R.drawable.interrogacao);
        this.player.setScaleX(1);
        this.adversario.setImageResource(R.drawable.interrogacao);
        ativarBtn();
    }

    public void ativarBtn(){
        this.pedra.setEnabled(true);
        this.papel.setEnabled(true);
        this.tesoura.setEnabled(true);
    }

    public void desativarBtn(){
        this.pedra.setEnabled(false);
        this.papel.setEnabled(false);
        this.tesoura.setEnabled(false);
    }
}
