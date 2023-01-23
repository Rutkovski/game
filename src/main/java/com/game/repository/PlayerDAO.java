package com.game.repository;

import com.game.entity.Player;


import java.util.List;
import java.util.Map;

public interface PlayerDAO {
    public List<Player> getAllPlayers();

    public void savePlayer(Player player);

    public Player getPlayer(long id);

    void deletePlayer(long id);

    public Player update(Player player);


}
