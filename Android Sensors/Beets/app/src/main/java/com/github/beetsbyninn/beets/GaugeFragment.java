package com.github.beetsbyninn.beets;


import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 *  Shows the score and is the main fragment for the game.
 * Author Ludwig Ninn
 * A simple {@link Fragment} subclass.
 */
public class GaugeFragment extends Fragment {

    /**
     * Constant used for setting max bar value.
     */
    private final int maxBarValue=100;
    private int temp;
    private MainActivity mainActivty;
    private TextView tvBar;
    private Button mBtnPlay;
    private boolean isPlaying = false;
    private  ProgressBar progressBar;
    private  boolean firstime=true;
    private boolean screenOn ;

    public GaugeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gauge, container, false);
        tvBar = (TextView)view.findViewById(R.id.tvBar);
        mainActivty = (MainActivity)getActivity();
        mBtnPlay = (Button)view.findViewById(R.id.btnPlay);
        mBtnPlay.setOnClickListener(new ButtonPlayListener());
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"street cred.ttf");
        tvBar.setTypeface(typeFace);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        ImageView img = (ImageView) view.findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivty.unbind();
                Intent i = new Intent(mainActivty,MainActivity.class);
                startActivity(i);
                mainActivty.finish();

            }
        });
        return view;

    }

    public boolean isScreenOn() {
        return screenOn;
    }

    @Override
    public void onStart() {
        super.onStart();
        screenOn = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        screenOn= false;
    }

    /**
     * Sets the bar value.
     * Author Ludvig Ninn
     * @param score
     */
    public void updateScore(double score) {
        ObjectAnimator animation = ObjectAnimator.ofInt (progressBar, "progress", temp, (int)score);
        animation.setDuration (1000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();
        tvBar.setText(""+score+"/"+maxBarValue);
        temp = (int) score;

    }

    /**
     * Button Listener. Have a pause and a start function.
     * Author Ludvig Ninn
     */
    private class ButtonPlayListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            checkStartPause();
        }
    }

    /**
     * Checks what current state is. Changes corresponds to the current state.
     */
    public void checkStartPause(){

        if(!isPlaying) {
            if(!firstime){
                mainActivty.start();
            }else{
                mainActivty.initialise();
            }

            firstime=false;
            mBtnPlay.setBackgroundResource(R.drawable.stopbtn);
            isPlaying = true;
        }else{
            mainActivty.pause();

            mBtnPlay.setBackgroundResource(R.drawable.playbtn);
            isPlaying = false;
        }
    }


}
