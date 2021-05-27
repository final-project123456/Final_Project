package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class GameView extends View {
    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS = 30;
    Bitmap background;
    Bitmap toptube,bottomtube;
    Display display;
    Point point;
    int dWidth, dHeight;
    Rect rect;
    Bitmap[] birds;
    int BirdFrame = 0;
    int velocity = 0, gravity = 3;
    int birdX,birdY;
    boolean gameState = false;
    int gap = 400;
    int mintubeOffSet, maxtubeOffSet;
    int numbersoftubes = 4;
    int distancebetweentubes;
    int[] tubeX = new int[numbersoftubes];
    int[] TopTubeY = new int[numbersoftubes];
    Random random;
    int tubeVelocity = 8;
    TextView tv;
    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        toptube = BitmapFactory.decodeResource(getResources(), R.drawable.pipe2);
        bottomtube  =BitmapFactory.decodeResource(getResources(), R.drawable.pipe1);
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0,0,dWidth,dHeight);
        birds = new Bitmap[2];
        birds[0] =  BitmapFactory.decodeResource(getResources(),R.drawable.bird1);
        birds[0] =  BitmapFactory.decodeResource(getResources(),R.drawable.bird2);
        birdX = dWidth/2-birds[0].getWidth()/2;
        birdY = dHeight/2-birds[0].getHeight()/2;
        distancebetweentubes = dWidth*3/4;
        mintubeOffSet = gap/2;
        maxtubeOffSet = dHeight-mintubeOffSet - gap;
        random = new Random();
        for (int i = 0; i < numbersoftubes; i++) {
            tubeX[i] = dWidth+i*distancebetweentubes;
            TopTubeY[i] = mintubeOffSet + random.nextInt(maxtubeOffSet-mintubeOffSet+1);
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rect,null);
        //if(BirdFrame == 0){
           // BirdFrame = 1;
        //}else{
         //   BirdFrame = 0;
        //}

        if(gameState){
            if (birdX<dHeight-birds[0].getHeight()||velocity<0){
                velocity += gravity;
                birdY += velocity;
            }
            for (int i = 0; i < numbersoftubes; i++) {
                tubeX[i] -= tubeVelocity;
                if(tubeX[i] < -toptube.getWidth()){
                    tubeX[i] += numbersoftubes * distancebetweentubes;
                    TopTubeY[i] = mintubeOffSet + random.nextInt(maxtubeOffSet-mintubeOffSet+1);

                }
                canvas.drawBitmap(toptube,tubeX[i],TopTubeY[i]-toptube.getHeight(),null);
                canvas.drawBitmap(bottomtube,tubeX[i],TopTubeY[i]+gap,null);
            }

        }
        canvas.drawBitmap(birds[BirdFrame],birdX ,birdY,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            velocity = -30;
            gameState = true;
        }
        return true;
    }
}

