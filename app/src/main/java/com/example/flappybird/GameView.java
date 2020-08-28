package com.example.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    private final long DELAY_SECONDS = 30;
    private Handler handler;
    private Runnable runGame;
    private int width, height;

    private Bitmap background, pipeUp, pipeDown;
    private Bitmap[] birds;
    private int birdFrame = 0;
    private int birdY;

    private int gap, numberOfPipes = 4, distance, minOffsetPipe, maxOffsetPipe;
    private int[] pipeX, upPipeY;
    Random generatorUpPipeY;

    private int velocityBird = 0, gravityBird = 1;
    private int velocityPipe = 10;



    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runGame = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        width = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        height = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();

        birds = new Bitmap[2];
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);
        birdY = height/2 - birds[0].getHeight()/2;

        birds[0] = Bitmap.createScaledBitmap(birds[0], (int)(birds[0].getWidth()*0.7), (int)(birds[0].getHeight()*0.7), true);
        birds[1] = Bitmap.createScaledBitmap(birds[1], (int)(birds[1].getWidth()*0.7), (int)(birds[1].getHeight()*0.7), true);



        pipeUp = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        pipeDown = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);

        pipeUp = Bitmap.createScaledBitmap(pipeUp,width/4,height, true);
        pipeDown = Bitmap.createScaledBitmap(pipeDown,width/4,height, true);

        gap = height /4;
        distance = width * 1/2 + width/4;
        minOffsetPipe = height / 4;
        maxOffsetPipe = height - minOffsetPipe - gap;

        generatorUpPipeY = new Random();
        pipeX = new int[numberOfPipes];
        upPipeY = new int[numberOfPipes];

        for (int i= 0; i< numberOfPipes; i++){
            pipeX[i] = width + i*distance;
            upPipeY[i] = minOffsetPipe + (generatorUpPipeY.nextInt()%(maxOffsetPipe - minOffsetPipe + 1));
        }



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, new Rect(0,0, this.width, this.height), null);
        if (birdFrame == 0)
            birdFrame = 1;
        else
            birdFrame = 0;

        velocityBird += gravityBird;

        birdY += velocityBird;
        canvas.drawBitmap(birds[birdFrame], width /2 - birds[0].getWidth()/2, birdY, null);
        for (int i = 0; i< numberOfPipes; i++){

            pipeX[i] -= velocityPipe;

            if (pipeX[i] <= - pipeUp.getWidth()){
                pipeX[i] += numberOfPipes*distance;
                upPipeY[i] = minOffsetPipe + (generatorUpPipeY.nextInt()%(maxOffsetPipe - minOffsetPipe + 1));
            }
            canvas.drawBitmap(pipeUp,pipeX[i]- pipeUp.getWidth()/2, upPipeY[i] - pipeUp.getHeight(), null);
            canvas.drawBitmap(pipeDown, pipeX[i] - pipeDown.getWidth()/2, upPipeY[i] + gap, null);
        }

        handler.postDelayed(runGame, DELAY_SECONDS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            velocityBird -= 20;
        }
        return super.onTouchEvent(event);
    }
}
