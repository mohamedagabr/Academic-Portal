package com.academic.portal.enums;

public enum IdentityType {

    NATIONAL_ID(1),
    PASSPORT(2);

    private final int id;


    IdentityType(int id ) {
        this.id = id;

    }

    public int getId() {
        return id;
    }


    public static IdentityType fromId(int id) {
        for (IdentityType activeStatus : values()) {
            if (activeStatus.id == id) {
                return activeStatus;
            }
        }
        throw new IllegalArgumentException("Unknown IdentityType  id: " + id);
    }


}
