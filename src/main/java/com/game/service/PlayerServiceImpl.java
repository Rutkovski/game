package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    @Override
    @Transactional
    public List<Player> getPlayers(Map<String, String> allQueryParams) {
        FilterHelper filterHelper = new FilterHelper(playerDAO.getAllPlayers(),allQueryParams);

        return filterHelper.getPage(filterHelper.allFilterAndOrder());
    }

    @Override
    @Transactional
    public Integer  getCountPlayers(Map<String, String> allQueryParams) {
        FilterHelper filterHelper = new FilterHelper(playerDAO.getAllPlayers(),allQueryParams);

        return filterHelper.allFilterAndOrder().size();
    }

    @Override
    @Transactional
    public void savePlayer(Player player) {
        playerDAO.savePlayer(player);
    }

    @Override
    @Transactional
    public Player getPlayer(long id) {
        return playerDAO.getPlayer(id);
    }

    @Override
    @Transactional
    public void deletePlayer(long id) {
        playerDAO.deletePlayer(id);
    }

    @Override
    @Transactional
    public Player updatePlayer(Player player) {
        return playerDAO.update(player);
    }
}


