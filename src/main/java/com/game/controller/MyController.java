package com.game.controller;

import com.game.entity.Player;
import com.game.exeption_handing.BadException;
import com.game.exeption_handing.NoSuchPlayerException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class MyController {

    @Autowired
    PlayerService playerService;


    @PostMapping("/players")
    public Player addNewPlayer(@RequestBody Player player){
        if (player==null){
            throw new BadException();
        }
        validationAddAndUpgrade(player);
        int level = (int) (Math.sqrt(2500+200*player.getExperience()-50)/100);
        int untilNextLevel = 50*(level+1)*(level+2)-player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        playerService.savePlayer(player);
        return player;
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable long id,@Validated @RequestBody Player player) {

        return playerService.newUpdatePlayer(id,player);
    }
//        if (id<=0){
//            throw new BadException();
//        }
//
//
//        Player old = playerService.getPlayer(id);
//        if (old==null & id!=0){
//            throw new NoSuchPlayerException();
//        }
//        boolean izm = false;
//        if (player.getName()!=null){
//            old.setName(player.getName());
//            izm = true;
//        }
//        if (player.getTitle()!=null){
//            old.setTitle(player.getTitle());
//            izm = true;
//
//        }
//        if (player.getRace()!=null){
//            old.setRace(player.getRace());
//            izm = true;
//        }
//        if (player.getProfession()!=null){
//            old.setProfession(player.getProfession());
//            izm = true;
//        }
//        if (player.getBirthday()!=null){
//            old.setBirthday(player.getBirthday());
//            izm = true;
//        }
//        if (player.getBanned()!=null && player.getBanned()){
//            old.setBanned(player.getBanned());
//            izm = true;
//        }
//        if (player.getExperience()!=null){
//            old.setExperience(player.getExperience());
//            izm = true;
//        }
//        if (izm) {
//            int level = (int) (Math.sqrt(2500+200*old.getExperience()-50)/100);
//            int untilNextLevel = 50*(level+1)*(level+2)-old.getExperience();
//            old.setLevel(level);
//            old.setUntilNextLevel(untilNextLevel);
//            validationAddAndUpgrade(old);
//            return playerService.updatePlayer(old);
//        } else {
//            return old;
//        }
//    }
//
    private void validationAddAndUpgrade(Player player) {
        boolean validName = player.getName()!=null&&player.getName().length()<=12;
        boolean validTitle =player.getTitle()!=null&&player.getTitle().length()<=30;
        boolean validRace = player.getRace()!=null;
        boolean validProfession = player.getProfession()!=null;
        boolean ValidBirthday = player.getBirthday()!=null && player.getBirthday().getTime()>=0;
        boolean validExperience = player.getExperience()!=null&&player.getExperience()>=0&&player.getExperience()<=10000000;
        boolean validAllParameters = validName&&validTitle&&validRace&&validProfession&&ValidBirthday&&validExperience;
        if(!validAllParameters){
            throw new BadException();
        }
    }



    //проходит все тесты, готов
    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable long id){
        Player player = playerService.getPlayer(id);
        if (player==null & id!=0){
            throw new NoSuchPlayerException();
        } if (id ==0){
            throw new BadException();
        }
        return player;
    }
    // Проходит все тесты, готов
    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable int id){
        Player player = playerService.getPlayer(id);
        if (id == 0){
            throw new BadException();
        }
        if (player==null){
            throw new NoSuchPlayerException();
        }
        playerService.deletePlayer(id);

    }
    //Проходит все тесты готов
    @GetMapping("/players/count")
    public Integer getCount(@RequestParam Map<String, String> allQueryParams){

        return playerService.getCountPlayers(allQueryParams);
    }
    //Проходит все тесты готов
    @GetMapping("/players")
    public  List<Player> showPlayers(@RequestParam Map<String, String> allQueryParams){
        return playerService.getPlayers(allQueryParams);
    }



}
