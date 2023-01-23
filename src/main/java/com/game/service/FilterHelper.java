package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.*;

public class FilterHelper {
    List<Player> playerList;
    Map<String, String> parametrMap;

    public FilterHelper(List<Player> playerList, Map<String, String> parametrMap) {
        this.playerList = playerList;
        this.parametrMap = parametrMap;
    }

    public List<Player> allFilterAndOrder() {
        List<Player> resultList;
        resultList = filterName(playerList);
        resultList = filterTitle(resultList);
        resultList = filterRace(resultList);
        resultList = filterProfession(resultList);
        resultList = filterAfter(resultList);
        resultList = filterBefore(resultList);
        resultList = filterBanned(resultList);
        resultList = filterMinExperience(resultList);
        resultList = filterMaxExperience(resultList);
        resultList = filterMinLevel(resultList);
        resultList = filterMaxLevel(resultList);
        orderByOrder(resultList);
        return resultList;

    }

    public List<Player> getPage(List<Player> list){
        int pageSize = getPageSize();
        int pageNumber = getPageNumber();
        int lastIndex = (pageNumber+1)*pageSize;
        return list.subList(lastIndex-pageSize, Math.min(lastIndex, list.size()));
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    private List<Player> filterName(List<Player> firstList) {
        if (!parametrMap.containsKey("name")) {
            return firstList;
        }
        String parameter = parametrMap.get("name");
        List<Player> resultList = new ArrayList<>();
        for (Player player : firstList) {
            if (player.getName().contains(parameter)) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterTitle(List<Player> firstList) {
        if (!parametrMap.containsKey("title")) {
            return firstList;
        }
        String parameter = parametrMap.get("title");
        List<Player> resultList = new ArrayList<>();
        for (Player player : firstList) {
            if (player.getTitle().contains(parameter)) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterRace(List<Player> firstList) {
        if (!parametrMap.containsKey("race")) {
            return firstList;
        }
        String parameter = parametrMap.get("race");
        Race race = Race.valueOf(parameter.toUpperCase());
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getRace().equals(race)) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterProfession(List<Player> firstList) {
        if (!parametrMap.containsKey("profession")) {
            return firstList;
        }
        String parameter = parametrMap.get("profession");
        Profession profession = Profession.valueOf(parameter.toUpperCase());
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getProfession().equals(profession)) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterAfter(List<Player> firstList) {
        if (!parametrMap.containsKey("after")) {
            return firstList;
        }
        String parameter = parametrMap.get("after");
        Long after = Long.parseLong(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getBirthday().getTime() >= after) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterBefore(List<Player> firstList) {
        if (!parametrMap.containsKey("before")) {
            return firstList;
        }
        String parameter = parametrMap.get("before");
        Long before = Long.parseLong(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getBirthday().getTime() <= before) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterBanned(List<Player> firstList) {
        if (!parametrMap.containsKey("banned")) {
            return firstList;
        }
        String parameter = parametrMap.get("banned");
        Boolean banned = Boolean.parseBoolean(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getBanned().equals(banned)) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterMinExperience(List<Player> firstList) {
        if (!parametrMap.containsKey("minExperience")) {
            return firstList;
        }
        String parameter = parametrMap.get("minExperience");
        Integer minExperience = Integer.parseInt(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getExperience() >= minExperience) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterMaxExperience(List<Player> firstList) {
        if (!parametrMap.containsKey("maxExperience")) {
            return firstList;
        }
        String parameter = parametrMap.get("maxExperience");
        Integer maxExperience = Integer.parseInt(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getExperience() <= maxExperience) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterMinLevel(List<Player> firstList) {
        if (!parametrMap.containsKey("minLevel")) {
            return firstList;
        }
        String parameter = parametrMap.get("minLevel");
        Integer minLevel = Integer.parseInt(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getLevel() >= minLevel) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private List<Player> filterMaxLevel(List<Player> firstList) {
        if (!parametrMap.containsKey("maxLevel")) {
            return firstList;
        }
        String parameter = parametrMap.get("maxLevel");
        Integer maxLevel = Integer.parseInt(parameter);
        List<Player> resultList = new ArrayList<>();

        for (Player player : firstList) {
            if (player.getLevel() <= maxLevel) {
                resultList.add(player);
            }
        }
        return resultList;
    }

    private void orderByOrder(List<Player> firstList) {

        PlayerOrder order;
        if (parametrMap.containsKey("order")) {
            order = PlayerOrder.valueOf(parametrMap.get("order"));
        } else {
            order = PlayerOrder.ID;
        }
        switch (order) {
            case ID:
                firstList.sort(Comparator.comparingLong(Player::getId));
                break;
            case NAME:
                firstList.sort(Comparator.comparing(Player::getName));
                break;
            case EXPERIENCE:
                firstList.sort(Comparator.comparingInt(Player::getExperience));
                break;
            case BIRTHDAY:
                firstList.sort(Comparator.comparingLong(o -> o.getBirthday().getTime()));
                break;
            case LEVEL:
                firstList.sort(Comparator.comparingInt(Player::getLevel));
                break;
        }

    }

    private int getPageSize(){
        int pageSize = 3;
        if (parametrMap.containsKey("pageSize")) {
            pageSize = Integer.parseInt(parametrMap.get("pageSize"));
        }
        return pageSize;
    }

    private int getPageNumber(){
        int pageNumber = 0;
        if (parametrMap.containsKey("pageNumber")) {
            pageNumber = Integer.parseInt(parametrMap.get("pageNumber"));
        }
        return pageNumber;
    }

}
