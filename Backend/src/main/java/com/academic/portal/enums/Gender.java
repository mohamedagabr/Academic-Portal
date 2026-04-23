package com.academic.portal.enums;

public enum Gender {

    MALE(1),
    FEMALE(2);


    private final int id;


      Gender(int id) {
        this.id = id;

       }

    public int getId() {
        return id;
    }


    // Used when loading user from DB to convert DB id to Gender enum
    public static Gender fromId(int id) {
        for (Gender gender : values()) {
            if (gender.id == id) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown Gender id: " + id);
    }



}
