package com.game.repository;

import com.game.entity.Player;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    @PersistenceContext
    private EntityManager entityManager;


    //Работает
    @Override
    public List<Player> getAllPlayers() {
        Query query = entityManager.createQuery("from Player");
        List<Player> players = query.getResultList();
        return players;
    }


    @Override
    public void savePlayer(Player player) {
            entityManager.persist(player);
    }

    //Работает
    @Override
    public Player getPlayer(long id) {
        Player player = entityManager.find(Player.class,id);
        return player;

    }

    //Работает
    @Override
    public void deletePlayer(long id) {
        Player player = entityManager.find(Player.class,id);
        entityManager.remove(player);
    }

    @Override
    public Player update(Player player) {
        System.out.println("in Dao1");
        try {
            return entityManager.merge(player);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
