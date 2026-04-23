package com.academic.portal.enums;
public enum ActiveStatus {


    ACTIVE(1 ),
    INACTIVE(0 );

    private final int id;


    ActiveStatus(int id) {
        this.id = id;
    }


    public int getId() {return id;}


    public static ActiveStatus fromId(int id) {
        for (ActiveStatus activeStatus : values()) {
            if (activeStatus.id == id) {
                return activeStatus;
            }
        }
        throw new IllegalArgumentException("Unknown ActiveStatus  id: " + id);
    }




}
