package com.example.paul.client;

public class Achievement {

    public Integer id;
    public Integer current;
    public Integer max;
    public Boolean done;
    public Integer repeated;
    public Boolean unlocked;

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", current=" + current +
                ", max=" + max +
                ", done=" + done +
                ", repeated=" + repeated +
                ", unlocked=" + unlocked +
                '}';
    }
}
