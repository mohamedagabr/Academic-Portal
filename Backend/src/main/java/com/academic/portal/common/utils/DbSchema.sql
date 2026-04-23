IF DB_ID('academic_portal') IS NULL EXECUTE('CREATE DATABASE [academic_portal];');
GO

USE [academic_portal];
GO

IF SCHEMA_ID('dbo') IS NULL EXECUTE('CREATE SCHEMA [dbo];');
GO

CREATE  TABLE academic_portal.dbo.address (
                                              address_id           int    IDENTITY  NOT NULL,
                                              address_name         varchar(255)      NOT NULL,
                                              CONSTRAINT pk_address PRIMARY KEY CLUSTERED ( address_id  asc )
);
GO

CREATE  TABLE academic_portal.dbo.course_departments (
                                                         course_department_id int    IDENTITY  NOT NULL,
                                                         course_department_name varchar(50)      NOT NULL,
                                                         CONSTRAINT pk_course_departments PRIMARY KEY CLUSTERED ( course_department_id  asc ) ,
                                                         CONSTRAINT uq_course_departments_name UNIQUE ( course_department_name  asc )
);
GO

CREATE  TABLE academic_portal.dbo.users (
                                            user_id              int    IDENTITY  NOT NULL,
                                            user_name            varchar(30)      NOT NULL,
                                            password             varchar(255)      NOT NULL,
                                            first_name           varchar(30)      NOT NULL,
                                            last_name            varchar(30)      NOT NULL,
                                            mobile_number        varchar(11)      NOT NULL,
                                            email                varchar(50)      NOT NULL,
                                            gender               int      NOT NULL,
                                            role                 int      NOT NULL,
                                            is_active            int  CONSTRAINT DF_users_is_active DEFAULT 1    NOT NULL,
                                            is_deleted           int  CONSTRAINT DF_users_is_deleted DEFAULT 0    NULL,
                                            created_at           datetime2  CONSTRAINT DF_users_created_at DEFAULT getdate()    NOT NULL,
                                            updated_at           datetime2      NULL,
                                            CONSTRAINT pk_users PRIMARY KEY CLUSTERED ( user_id  asc ) ,
                                            CONSTRAINT uq_users_mobile UNIQUE ( mobile_number  asc ) ,
                                            CONSTRAINT uq_users_email UNIQUE ( email  asc ) ,
                                            CONSTRAINT uq_users_username UNIQUE ( user_name  asc )
);
GO

CREATE  TABLE academic_portal.dbo.courses (
                                              course_id            int    IDENTITY  NOT NULL,
                                              course_name          varchar(50)      NOT NULL,
                                              course_code          varchar(10)      NULL,
                                              course_department_id int      NOT NULL,
                                              capacity             int      NOT NULL,
                                              created_by           int      NOT NULL,
                                              created_at           datetime2  CONSTRAINT DF_courses_created_at DEFAULT getdate()    NOT NULL,
                                              last_updated_by      int      NULL,
                                              last_updated_at      datetime2      NULL,
                                              is_active            int  CONSTRAINT DF_courses_is_active DEFAULT 1    NOT NULL,
                                              CONSTRAINT pk_courses PRIMARY KEY CLUSTERED ( course_id  asc ) ,
                                              CONSTRAINT uq_courses_code UNIQUE ( course_code  asc ) ,
                                              CONSTRAINT uq_courses_name UNIQUE ( course_name  asc ) ,
                                              CONSTRAINT chk_courses_capacity CHECK ( [capacity]>(0) )
    );
GO

CREATE  TABLE academic_portal.dbo.refresh_tokens (
                                                     id                   bigint    IDENTITY  NOT NULL,
                                                     user_id              int      NOT NULL,
                                                     token                varchar(500)      NOT NULL,
                                                     expiry_date          datetimeoffset      NOT NULL,
                                                     CONSTRAINT pk_refresh_tokens PRIMARY KEY CLUSTERED ( id  asc ) ,
                                                     CONSTRAINT uq_refresh_tokens_token UNIQUE ( token  asc )
);
GO

CREATE NONCLUSTERED INDEX idx_refresh_tokens_user ON academic_portal.dbo.refresh_tokens ( user_id  asc );
GO

CREATE  TABLE academic_portal.dbo.students (
                                               student_id           int    IDENTITY  NOT NULL,
                                               user_id              int      NOT NULL,
                                               national_id          varchar(14)      NULL,
                                               passport_number      varchar(20)      NULL,
                                               identity_type        int      NOT NULL,
                                               address_id           int      NULL,
                                               is_active            int  CONSTRAINT DF_students_is_active DEFAULT 1    NOT NULL,
                                               CONSTRAINT pk_students PRIMARY KEY CLUSTERED ( student_id  asc ) ,
                                               CONSTRAINT uq_students_user UNIQUE ( user_id  asc ) ,
                                               CONSTRAINT chk_national_id_length CHECK ( [national_id] IS NULL OR len([national_id])=(14) AND NOT [national_id] like '%[^0-9]%' ),
	CONSTRAINT chk_students_identity CHECK ( [identity_type]=(1) AND [national_id] IS NOT NULL AND [passport_number] IS NULL OR [identity_type]=(2) AND [passport_number] IS NOT NULL AND [national_id] IS NULL )
 );
GO

CREATE UNIQUE NONCLUSTERED INDEX ux_students_national_id ON academic_portal.dbo.students ( national_id  asc ) WHERE ([national_id] IS NOT NULL);
GO

CREATE UNIQUE NONCLUSTERED INDEX ux_students_passport ON academic_portal.dbo.students ( passport_number  asc ) WHERE ([passport_number] IS NOT NULL);
GO

CREATE  TABLE academic_portal.dbo.course_registrations (
                                                           id                   int    IDENTITY  NOT NULL,
                                                           student_id           int      NOT NULL,
                                                           course_id            int      NOT NULL,
                                                           registration_status  int  CONSTRAINT DF_course_registrations_registration_status DEFAULT 1    NOT NULL,
                                                           created_at           datetime2  CONSTRAINT DF_course_registrations_created_at DEFAULT getdate()    NOT NULL,
                                                           last_updated_at      datetime2  CONSTRAINT DF_course_registrations_last_updated_at DEFAULT getdate()    NOT NULL,
                                                           CONSTRAINT pk_course_registrations PRIMARY KEY CLUSTERED ( id  asc ) ,
                                                           CONSTRAINT uq_student_course UNIQUE ( student_id  asc, course_id  asc ) ,
                                                           CONSTRAINT chk_course_registrations_status CHECK ( [registration_status]=(2) OR [registration_status]=(1) OR [registration_status]=(0) )
    );
GO

CREATE NONCLUSTERED INDEX idx_reg_student ON academic_portal.dbo.course_registrations ( student_id  asc );
GO

CREATE NONCLUSTERED INDEX idx_reg_course ON academic_portal.dbo.course_registrations ( course_id  asc );
GO

ALTER TABLE academic_portal.dbo.course_registrations ADD CONSTRAINT fk_registrations_course FOREIGN KEY ( course_id ) REFERENCES academic_portal.dbo.courses( course_id );
GO

ALTER TABLE academic_portal.dbo.course_registrations ADD CONSTRAINT fk_registrations_student FOREIGN KEY ( student_id ) REFERENCES academic_portal.dbo.students( student_id );
GO

ALTER TABLE academic_portal.dbo.courses ADD CONSTRAINT fk_courses_created_by FOREIGN KEY ( created_by ) REFERENCES academic_portal.dbo.users( user_id );
GO

ALTER TABLE academic_portal.dbo.courses ADD CONSTRAINT fk_courses_department FOREIGN KEY ( course_department_id ) REFERENCES academic_portal.dbo.course_departments( course_department_id );
GO

ALTER TABLE academic_portal.dbo.courses ADD CONSTRAINT fk_courses_updated_by FOREIGN KEY ( last_updated_by ) REFERENCES academic_portal.dbo.users( user_id );
GO

ALTER TABLE academic_portal.dbo.refresh_tokens ADD CONSTRAINT fk_refresh_tokens_user FOREIGN KEY ( user_id ) REFERENCES academic_portal.dbo.users( user_id );
GO

ALTER TABLE academic_portal.dbo.students ADD CONSTRAINT fk_students_address FOREIGN KEY ( address_id ) REFERENCES academic_portal.dbo.address( address_id );
GO

ALTER TABLE academic_portal.dbo.students ADD CONSTRAINT fk_students_user FOREIGN KEY ( user_id ) REFERENCES academic_portal.dbo.users( user_id );
GO

