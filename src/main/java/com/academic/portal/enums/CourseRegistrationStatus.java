package com.academic.portal.enums;

public enum CourseRegistrationStatus {


    REGISTERED(1),
    CANCELED(0);

    private final int id ;


    public int getId() {return id;}

    CourseRegistrationStatus(int id ) {
        this.id = id;

    }

    public static CourseRegistrationStatus fromId(int id) {
        for (CourseRegistrationStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown CourseRegistrationStatus id: " + id);
    }



}
