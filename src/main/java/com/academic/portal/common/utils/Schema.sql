ALTER SESSION SET "_ORACLE_SCRIPT"=true;

CREATE USER ACADEMIC_PORTAL IDENTIFIED BY ACADEMIC_PORTAL;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_ADDRESS START WITH 1 INCREMENT BY 1 NOORDER;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_COURSES START WITH 1 INCREMENT BY 1 NOORDER;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_COURSE_DEPARTMENTS START WITH 1 INCREMENT BY 1 NOORDER;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_COURSE_REGISTRATIONS START WITH 1 INCREMENT BY 1 NOORDER;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_REFRESH_TOKENS START WITH 1 INCREMENT BY 1 NOORDER;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_STUDENTS START WITH 1 INCREMENT BY 1 NOORDER;

CREATE SEQUENCE ACADEMIC_PORTAL.SEQ_USERS START WITH 1 INCREMENT BY 1 NOORDER;

CREATE  TABLE ACADEMIC_PORTAL.ADDRESS (
                                          ADDRESS_ID           NUMBER   NOT NULL,
                                          ADDRESS_NAME         VARCHAR2(255)   NOT NULL,
                                          CONSTRAINT PK_ADDRESS PRIMARY KEY ( ADDRESS_ID )
);

CREATE  TABLE ACADEMIC_PORTAL.COURSE_DEPARTMENTS (
                                                     COURSE_DEPARTMENT_ID NUMBER   NOT NULL,
                                                     COURSE_DEPARTMENT_NAME VARCHAR2(50)   NOT NULL,
                                                     CONSTRAINT PK_COURSE_DEPARTMENTS PRIMARY KEY ( COURSE_DEPARTMENT_ID ) ,
                                                     CONSTRAINT UQ_COURSE_DEPARTMENTS_NAME UNIQUE ( COURSE_DEPARTMENT_NAME )
);

CREATE  TABLE ACADEMIC_PORTAL.USERS (
                                        USER_ID              NUMBER   NOT NULL,
                                        USER_NAME            VARCHAR2(30)   NOT NULL,
                                        PASSWORD             VARCHAR2(255)   NOT NULL,
                                        FIRST_NAME           VARCHAR2(30)   NOT NULL,
                                        LAST_NAME            VARCHAR2(30)   NOT NULL,
                                        MOBILE_NUMBER        VARCHAR2(11)   NOT NULL,
                                        EMAIL                VARCHAR2(50)   NOT NULL,
                                        GENDER               NUMBER   NOT NULL,
                                        ROLE                 NUMBER   NOT NULL,
                                        IS_ACTIVE            NUMBER  DEFAULT 1 NOT NULL,
                                        IS_DELETED           NUMBER  DEFAULT 0 NOT NULL,
                                        CREATED_AT           TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                        UPDATED_AT           TIMESTAMP(6)   ,
                                        CONSTRAINT PK_USERS PRIMARY KEY ( USER_ID ) ,
                                        CONSTRAINT UQ_USERS_EMAIL UNIQUE ( EMAIL ) ,
                                        CONSTRAINT UQ_USERS_MOBILE UNIQUE ( MOBILE_NUMBER ) ,
                                        CONSTRAINT UQ_USERS_USERNAME UNIQUE ( USER_NAME )
);

CREATE  TABLE ACADEMIC_PORTAL.COURSES (
                                          COURSE_ID            NUMBER   NOT NULL,
                                          COURSE_NAME          VARCHAR2(50)   NOT NULL,
                                          COURSE_CODE          VARCHAR2(10)   ,
                                          COURSE_DEPARTMENT_ID NUMBER   NOT NULL,
                                          CAPACITY             NUMBER   NOT NULL,
                                          CREATED_BY           NUMBER   NOT NULL,
                                          CREATED_AT           TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          LAST_UPDATED_BY      NUMBER   ,
                                          LAST_UPDATED_AT      TIMESTAMP(6)   ,
                                          IS_ACTIVE            NUMBER  DEFAULT 1 NOT NULL,
                                          CONSTRAINT PK_COURSES PRIMARY KEY ( COURSE_ID ) ,
                                          CONSTRAINT UQ_COURSES_CODE UNIQUE ( COURSE_CODE ) ,
                                          CONSTRAINT UQ_COURSES_NAME UNIQUE ( COURSE_NAME )
);

ALTER TABLE ACADEMIC_PORTAL.COURSES ADD CONSTRAINT CHK_COURSES_CAPACITY CHECK ( CAPACITY > 0 );

CREATE  TABLE ACADEMIC_PORTAL.REFRESH_TOKENS (
                                                 ID                   NUMBER   NOT NULL,
                                                 USER_ID              NUMBER   NOT NULL,
                                                 TOKEN                VARCHAR2(500)   NOT NULL,
                                                 EXPIRY_DATE          TIMESTAMP WITH TIME ZONE   NOT NULL,
                                                 CONSTRAINT PK_REFRESH_TOKENS PRIMARY KEY ( ID ) ,
                                                 CONSTRAINT UQ_REFRESH_TOKENS_TOKEN UNIQUE ( TOKEN )
);

CREATE  TABLE ACADEMIC_PORTAL.STUDENTS (
                                           STUDENT_ID           NUMBER   NOT NULL,
                                           USER_ID              NUMBER   NOT NULL,
                                           NATIONAL_ID          VARCHAR2(14)   ,
                                           PASSPORT_NUMBER      VARCHAR2(20)   ,
                                           IDENTITY_TYPE        NUMBER   NOT NULL,
                                           ADDRESS_ID           NUMBER   ,
                                           IS_ACTIVE            NUMBER  DEFAULT 1 NOT NULL,
                                           CONSTRAINT PK_STUDENTS PRIMARY KEY ( STUDENT_ID ) ,
                                           CONSTRAINT UQ_STUDENTS_USER UNIQUE ( USER_ID )
);

ALTER TABLE ACADEMIC_PORTAL.STUDENTS ADD CONSTRAINT CHK_STUDENTS_NATIONAL_ID CHECK (
    NATIONAL_ID IS NULL OR
    (LENGTH(NATIONAL_ID) = 14 AND REGEXP_LIKE(NATIONAL_ID, '^[0-9]+$'))
    );

ALTER TABLE ACADEMIC_PORTAL.STUDENTS ADD CONSTRAINT CHK_STUDENTS_IDENTITY CHECK (
    (IDENTITY_TYPE = 1 AND NATIONAL_ID IS NOT NULL AND PASSPORT_NUMBER IS NULL) OR
    (IDENTITY_TYPE = 2 AND PASSPORT_NUMBER IS NOT NULL AND NATIONAL_ID IS NULL)
    );

CREATE  TABLE ACADEMIC_PORTAL.COURSE_REGISTRATIONS (
                                                       ID                   NUMBER   NOT NULL,
                                                       STUDENT_ID           NUMBER   NOT NULL,
                                                       COURSE_ID            NUMBER   NOT NULL,
                                                       REGISTRATION_STATUS  NUMBER  DEFAULT 1 NOT NULL,
                                                       CREATED_AT           TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                                       LAST_UPDATED_AT      TIMESTAMP(6)  DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                                       CONSTRAINT PK_COURSE_REGISTRATIONS PRIMARY KEY ( ID ) ,
                                                       CONSTRAINT UQ_STUDENT_COURSE UNIQUE ( STUDENT_ID, COURSE_ID )
);

ALTER TABLE ACADEMIC_PORTAL.COURSE_REGISTRATIONS ADD CONSTRAINT CHK_COURSE_REGISTRATIONS_STATUS CHECK (
    REGISTRATION_STATUS IN (0, 1, 2)
    );

ALTER TABLE ACADEMIC_PORTAL.COURSES ADD CONSTRAINT FK_COURSES_DEPARTMENT FOREIGN KEY ( COURSE_DEPARTMENT_ID ) REFERENCES ACADEMIC_PORTAL.COURSE_DEPARTMENTS( COURSE_DEPARTMENT_ID );

ALTER TABLE ACADEMIC_PORTAL.COURSES ADD CONSTRAINT FK_COURSES_CREATED_BY FOREIGN KEY ( CREATED_BY ) REFERENCES ACADEMIC_PORTAL.USERS( USER_ID );

ALTER TABLE ACADEMIC_PORTAL.COURSES ADD CONSTRAINT FK_COURSES_UPDATED_BY FOREIGN KEY ( LAST_UPDATED_BY ) REFERENCES ACADEMIC_PORTAL.USERS( USER_ID );

ALTER TABLE ACADEMIC_PORTAL.COURSE_REGISTRATIONS ADD CONSTRAINT FK_REGISTRATIONS_COURSE FOREIGN KEY ( COURSE_ID ) REFERENCES ACADEMIC_PORTAL.COURSES( COURSE_ID );

ALTER TABLE ACADEMIC_PORTAL.COURSE_REGISTRATIONS ADD CONSTRAINT FK_REGISTRATIONS_STUDENT FOREIGN KEY ( STUDENT_ID ) REFERENCES ACADEMIC_PORTAL.STUDENTS( STUDENT_ID );

ALTER TABLE ACADEMIC_PORTAL.REFRESH_TOKENS ADD CONSTRAINT FK_REFRESH_TOKENS_USER FOREIGN KEY ( USER_ID ) REFERENCES ACADEMIC_PORTAL.USERS( USER_ID );

ALTER TABLE ACADEMIC_PORTAL.STUDENTS ADD CONSTRAINT FK_STUDENTS_ADDRESS FOREIGN KEY ( ADDRESS_ID ) REFERENCES ACADEMIC_PORTAL.ADDRESS( ADDRESS_ID );

ALTER TABLE ACADEMIC_PORTAL.STUDENTS ADD CONSTRAINT FK_STUDENTS_USER FOREIGN KEY ( USER_ID ) REFERENCES ACADEMIC_PORTAL.USERS( USER_ID );

