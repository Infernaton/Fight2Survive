package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.handler.ChatHandler;

public class CountDown implements Runnable {

    private long time;
    private int id;
    private String msg;

    public CountDown(long departTime){
        this.time = departTime;
        this.msg = "Finish !";
    }
    public CountDown(long departTime, String msg){
        this.time = departTime;
        this.msg = msg;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public void run() {

        if (time == 0){
            ChatHandler.broadcast(msg);
            CountDownBuilder.stopCountdown(id);
        }
        else if(time % 10 == 0 || time <= 5){
            ChatHandler.toAllPlayer(time + " seconds left !");
        }
        time--;
    }
}
