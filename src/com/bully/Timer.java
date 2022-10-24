package com.bully;
import java.util.concurrent.*;
import java.time.Duration;
import java.sql.Time;

public class Timer {
    private static Timer timeOut  ;
    private Timer(){} ;

    public static  Timer getInstance(){
        if (timeOut == null)
         return  new Timer() ;
        return timeOut ;
    }

    public void Wait () throws InterruptedException {
        Thread.sleep(1000);
    }
}
