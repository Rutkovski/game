package com.game.service;

import com.game.entity.Player;


import java.util.List;

public interface PlayerService {
    public List<Player> getAllPlayers();
    public void savePlayer(Player player);
    public Player getPlayer(long id);
    public void deletePlayer(long id);
    public Player updatePlayer(Player player);



}
