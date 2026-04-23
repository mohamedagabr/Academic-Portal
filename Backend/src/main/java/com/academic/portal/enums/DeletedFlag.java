package com.academic.portal.enums;

public enum DeletedFlag {



    NO(0),
    YES(1);

    private final int id;


    DeletedFlag(int id ) {
        this.id = id;

    }

    public int getId() {
        return id;
    }


    public static DeletedFlag fromId(int id) {
        for (DeletedFlag deletedFlag : values()) {
            if (deletedFlag.id == id) {
                return deletedFlag;
            }
        }
        throw new IllegalArgumentException("Unknown DeletedFlag  id: " + id);
    }


}
