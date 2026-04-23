package com.academic.portal.enums;

public enum Role {
    USER(1 ),
    STUDENT(2),
    ADMIN(3),
    SUPER_ADMIN(4);



    private final int id;

    Role(int id ) {
        this.id = id;

    }

    public int getId() {
        return id;
    }


    public static Role fromId(int id) {
        for (Role role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role id: " + id);
    }



}
