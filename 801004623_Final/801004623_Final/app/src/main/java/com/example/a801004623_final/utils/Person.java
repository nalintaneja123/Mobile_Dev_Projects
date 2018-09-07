package com.example.a801004623_final.utils;

import java.io.Serializable;

/**
 * Created by mshehab on 5/6/18.
 */

public class Person  implements Serializable{
    String name;
    int totalBudget;
    int totalBought;
    int giftCount;
    String id;

    public Person() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(int totalBudget) {
        this.totalBudget = totalBudget;
    }

    public int getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(int totalBought) {
        this.totalBought = totalBought;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
