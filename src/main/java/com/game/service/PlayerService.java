package com.game.service;

import com.game.entity.Player;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface PlayerService {
    public List<Player> getPlayers(Map<String, String> allQueryParams);


    Integer  getCountPlayers(Map<String, String> allQueryParams);

    public void savePlayer(Player player);
    public Player getPlayer(long id);
    public void deletePlayer(long id);
    public Player updatePlayer(Player player);

    Player newUpdatePlayer(Long id, Player player);


    Player findPlayerByID(Long id);
}
