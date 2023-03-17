package com.snackman.datnud11.consts;

public enum Gender {

    NAM(0, "Nam"),
    NU(1, "Nữ");

    Gender(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int id;
    public String name;

    public static Gender findGender(int id){
        for(Gender v : values()){
            if( v.id == id){
                return v;
            }
        }
        return null;
    }
}
