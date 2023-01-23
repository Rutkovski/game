package com.game.service;

import com.game.entity.Player;
import com.game.exeption_handing.BadException;
import com.game.exeption_handing.NoSuchPlayerException;
import com.game.repository.PlayerDAO;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    @Autowired
    private PlayerRepository playerRepository;

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

    @Override
    @Transactional
    public Player newUpdatePlayer(Long id, Player player) {

        if (id <= 0) throw new BadException();

        Player updatedPlayer = getPlayer(id);
        if (updatedPlayer==null) throw new NoSuchPlayerException();

        if (player.getName() != null) {
            updatedPlayer.setName(player.getName());
        }
        if (player.getTitle() != null) {
            updatedPlayer.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            updatedPlayer.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            updatedPlayer.setProfession(player.getProfession());
        }
        if (player.getBanned() != null) {
            updatedPlayer.setBanned(player.getBanned());
        }
        if (player.getBirthday() != null) {
            if (!isValidDate(player.getBirthday())) {
                throw new BadException();
            }
            updatedPlayer.setBirthday(player.getBirthday());
        }
        if (player.getExperience() != null) {
            if (!isValidExperience(player.getExperience()))
                throw new BadException();
            updatedPlayer.setExperience(player.getExperience());
            updateLevelAndUntilNextLevel(updatedPlayer);
        }

        return playerRepository.save(updatedPlayer);
    }

    @Override
    public Player findPlayerByID(Long id) {
//        @Override
//        public Player findPlayerByID(Long id) {
//            ("Incorrect id. Please try again.");
//            return playerRepository.findById(id).orElseThrow(() -> throw new No("Player not found. Please try again."));
//        }
        return new Player();
    }

    private boolean isValidDate(Date date) {
        return date != null && date.getTime() >= 0
                && date.getTime() >= 946674000000L          //"2000/01/01 00:00:00"
                && date.getTime() <= 32535205169000L;       //"3000/12/31 23:59:29"
    }
    private boolean isValidExperience(Integer experience) {
        return experience != null && experience > 0 && experience <= 10000000;
    }
    //Вычисление уровня и опыта персонажа по формуле из ТЗ
    private void updateLevelAndUntilNextLevel(Player player) {
        int level = (int) (Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100;
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
    }





}


