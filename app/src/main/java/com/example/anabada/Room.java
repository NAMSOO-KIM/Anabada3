package com.example.anabada;

public class Room {
    String roomTitle;
   // private String roomPassword;

    public Room(String roomTitle) {
        this.roomTitle = roomTitle;
        //this.roomPassword=roomPassword;
    }

    public String getRoom() {
        return roomTitle;
    }

    public void setRoom(String roomTitle) {
        this.roomTitle = roomTitle;
    }


}
