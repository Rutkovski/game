package com.game;

import com.game.db.DBConnect;

public class Main {
    public static void main(String[] args) {
        DBConnect dbConnect = DBConnect.getInstance();
        dbConnect.connect();
    }
}
