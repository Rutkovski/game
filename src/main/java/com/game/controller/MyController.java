package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exeption_handing.NoSuchPlayerException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyController {

    @Autowired
    PlayerService playerService;


    @PostMapping("/players")
    public Player addNewPlayer(@RequestBody Player player){
        validationAddAndUpgrade(player);
        int level = (int) (Math.sqrt(2500+200*player.getExperience()-50)/100);
        int untilNextLevel = 50*(level+1)*(level+2)-player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);

        playerService.savePlayer(player);
        return player;
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable long id, @RequestBody Player player){

        Player old = playerService.getPlayer(id);

        if (old==null & id!=0){
            throw new NoSuchPlayerException();
        }
        if (player.getName()!=null){
            old.setName(player.getName());
        }
        if (player.getTitle()!=null){
            old.setTitle(player.getTitle());
        }
        if (player.getRace()!=null){
            old.setRace(player.getRace());
        }
        if (player.getProfession()!=null){
            old.setProfession(player.getProfession());
        }
        if (player.getBirthday()!=null){
            old.setBirthday(player.getBirthday());
        }
        if (player.getBanned()!=false){
            old.setBanned(player.getBanned());
        }
        if (player.getExperience()!=0){
            old.setExperience(player.getExperience());
        }
        int level = (int) (Math.sqrt(2500+200*old.getExperience()-50)/100);
        int untilNextLevel = 50*(level+1)*(level+2)-old.getExperience();
        old.setLevel(level);
        old.setUntilNextLevel(untilNextLevel);

        validationAddAndUpgrade(old);


        return playerService.updatePlayer(old);
    }

    private void validationAddAndUpgrade(Player player) {
        boolean validName = !player.getName().isEmpty()&&player.getName().length()<=12;
        boolean validTitle =player.getTitle()!=null&&player.getTitle().length()<=30;
        boolean validRace = player.getRace()!=null;
        boolean validProfession = player.getProfession()!=null;
        boolean ValidBirthday = player.getBirthday().getTime()>=0;
        boolean validExperience = player.getExperience()!=null&&player.getExperience()>=0&&player.getExperience()<=10000000;
        boolean validAllParameters = validName&&validTitle&&validRace&&validProfession&&ValidBirthday&&validExperience;
        if(!validAllParameters){
            throw new RuntimeException();
        }
    }

    @GetMapping("/players")
    public List<Player> showAllPlayers(){
        List<Player> allPlayers = playerService.getAllPlayers();
        return allPlayers;
    }

    //проходит все тесты, готов
    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable long id){
        Player player = playerService.getPlayer(id);
        if (player==null & id!=0){
            throw new NoSuchPlayerException();
        } if (id ==0){
            throw new RuntimeException();
        }
        return player;
    }

    // Проходит все тесты, готов
    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable int id){
        Player player = playerService.getPlayer(id);
        if (player==null & id!=0){
            throw new NoSuchPlayerException();
        }

        playerService.deletePlayer(id);


    }



}
