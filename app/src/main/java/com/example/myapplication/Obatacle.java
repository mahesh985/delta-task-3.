package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obatacle implements GameObject {
    private Rect rectangle;

    private Rect rectangle2;
    private int color ;
    private  int startX;
    private  int playerGap;

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top +=y;
        rectangle.bottom+=y;
        rectangle.top+=y;
        rectangle2.bottom +=y;
    }
    public  Obatacle(int rectHeight , int color, int startX, int startY, int playerGap) {

        this.color = color;

        rectangle = new Rect(0,startY,startX,startX+rectHeight);
        rectangle2 =new Rect(startX+ playerGap,startY,Constants.SCREEN_WIDTH,startY +rectHeight);



    }
    public boolean playerCollide(RectPlayer player  ){
        return Rect.intersects(rectangle, player.getRectangle()) ;
        //return  Rect.intersects(rectangle2, player.getRectangle());
    }

        @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);



        }
        @Override
    public void update(){

        }
}
