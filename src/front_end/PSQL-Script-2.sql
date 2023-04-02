

/* Drop Tables */

DROP TABLE IF EXISTS Address CASCADE
;

DROP TABLE IF EXISTS Category CASCADE
;

DROP TABLE IF EXISTS Comment CASCADE
;

DROP TABLE IF EXISTS Event CASCADE
;

DROP TABLE IF EXISTS Eventcategory CASCADE
;

DROP TABLE IF EXISTS Organization CASCADE
;

DROP TABLE IF EXISTS Organizationuser CASCADE
;

DROP TABLE IF EXISTS Permission CASCADE
;

DROP TABLE IF EXISTS Photo CASCADE
;

DROP TABLE IF EXISTS Ticket CASCADE
;

DROP TABLE IF EXISTS "User" CASCADE
;

DROP TABLE IF EXISTS Userdetails CASCADE
;

DROP TABLE IF EXISTS Userrole CASCADE
;

DROP TABLE IF EXISTS Userrolepermission CASCADE
;

DROP TABLE IF EXISTS Userroleuser CASCADE
;

/* Create Tables */

CREATE TABLE Address
(
	City text NOT NULL,
	Destrict text NOT NULL,
	Region text NOT NULL,
	Street text NOT NULL,
	PK_AddressID serial NOT NULL
)
;

CREATE TABLE Category
(
	Description text NOT NULL,
	Name text NOT NULL,
	PK_CategoryID serial NOT NULL
)
;

CREATE TABLE Comment
(
	Comment text NOT NULL,
	Createdat timestamp without time zone NOT NULL,
	FK_UserID serial NOT NULL,
	FK_EventID serial NOT NULL,
	PK_CommentID serial NOT NULL
)
;

CREATE TABLE Event
(
	Description text NOT NULL,
	Name text NOT NULL,
	Time timestamp without time zone NOT NULL,
	PK_EventID serial NOT NULL,
	Coordinate char(14) NULL,
	FK_AddressID serial NOT NULL,
	FK_OrganizationID serial NOT NULL
)
;

CREATE TABLE Eventcategory
(
	FK_EventID serial NOT NULL,
	FK_CategoryID serial NOT NULL,
	PK_EventcategoryID serial NOT NULL
)
;

CREATE TABLE Organization
(
	Name text NOT NULL,
	PK_OrganizationID serial NOT NULL
)
;

CREATE TABLE Organizationuser
(
	FK_UserID serial NOT NULL,
	FK_OrganizationID serial NOT NULL,
	PK_OrganizationuserID serial NOT NULL
)
;

CREATE TABLE Permission
(
	Description text NOT NULL,
	PK_PermissionID serial NOT NULL
)
;

CREATE TABLE Photo
(
	Modified timestamp without time zone NOT NULL,
	Photo bytea NOT NULL,
	Suffix text NOT NULL,
	Type text NOT NULL,
	Uploadat timestamp without time zone NOT NULL,
	PK_PhotoID serial NOT NULL,
	FK_UserID serial NOT NULL
)
;

CREATE TABLE Ticket
(
	Createdat timestamp without time zone NOT NULL,
	State text NOT NULL,
	PK_TicketID serial NOT NULL,
	FK_UserID serial NOT NULL,
	FK_EventID serial NOT NULL
)
;

CREATE TABLE "User"
(
	Email text NOT NULL,
	Password text NOT NULL,
	PK_UserID serial NOT NULL,
	Username text NOT NULL,
	FK_UserdetailsID serial NOT NULL
)
;

CREATE TABLE Userdetails
(
	Dateofbirth timestamp with time zone NOT NULL,
	Name text NULL,
	Phone text NOT NULL,
	Surname text NULL,
	PK_UserdetailsID serial NOT NULL
)
;

CREATE TABLE Userrole
(
	Type text NOT NULL,
	PK_UserroleID serial NOT NULL
)
;

CREATE TABLE Userrolepermission
(
	PK_UserrolepermissionID serial NOT NULL,
	FK_PermissionID serial NOT NULL,
	FK_UserroleID serial NOT NULL
)
;

CREATE TABLE Userroleuser
(
	PK_UserroleuserID serial NOT NULL,
	FK_UserID serial NOT NULL,
	FK_UserroleID serial NOT NULL
)
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE Address ADD CONSTRAINT PK_addressID
	PRIMARY KEY (PK_AddressID)
;

ALTER TABLE Category ADD CONSTRAINT PK_CategoryID
	PRIMARY KEY (PK_CategoryID)
;

ALTER TABLE Comment ADD CONSTRAINT PK_CommentID
	PRIMARY KEY (PK_CommentID)
;

ALTER TABLE Event ADD CONSTRAINT PK_EventID
	PRIMARY KEY (PK_EventID)
;

ALTER TABLE Eventcategory ADD CONSTRAINT PK_EventcategoryID
	PRIMARY KEY (PK_EventcategoryID)
;

ALTER TABLE Organization ADD CONSTRAINT PK_OrganizationID
	PRIMARY KEY (PK_OrganizationID)
;

ALTER TABLE Organizationuser ADD CONSTRAINT PK_OrganizationuserID
	PRIMARY KEY (PK_OrganizationuserID)
;

ALTER TABLE Permission ADD CONSTRAINT PK_PermissionID
	PRIMARY KEY (PK_PermissionID)
;

ALTER TABLE Photo ADD CONSTRAINT PK_PhotoID
	PRIMARY KEY (PK_PhotoID)
;

ALTER TABLE Ticket ADD CONSTRAINT PK_TicketID
	PRIMARY KEY (PK_TicketID)
;

ALTER TABLE "User" ADD CONSTRAINT PK_UserID
	PRIMARY KEY (PK_UserID)
;

ALTER TABLE Userdetails ADD CONSTRAINT PK_UserdetailsID
	PRIMARY KEY (PK_UserdetailsID)
;

ALTER TABLE Userrole ADD CONSTRAINT PK_UserroleID
	PRIMARY KEY (PK_UserroleID)
;

ALTER TABLE Userrolepermission ADD CONSTRAINT PK_UserrolepermissionID
	PRIMARY KEY (PK_UserrolepermissionID)
;

ALTER TABLE Userroleuser ADD CONSTRAINT PK_UserroleuserID
	PRIMARY KEY (PK_UserroleuserID)
;

/* Create Foreign Key Constraints */

ALTER TABLE Comment ADD CONSTRAINT FK_Comment_create
	FOREIGN KEY (FK_UserID) REFERENCES "User" (PK_UserID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Comment ADD CONSTRAINT FK_Comment_has
	FOREIGN KEY (FK_EventID) REFERENCES Event (PK_EventID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Event ADD CONSTRAINT FK_Event_has
	FOREIGN KEY (FK_AddressID) REFERENCES Address (PK_AddressID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Event ADD CONSTRAINT FK_Event_organizes
	FOREIGN KEY (FK_OrganizationID) REFERENCES Organization (PK_OrganizationID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Eventcategory ADD CONSTRAINT FK_Eventcategory_Category
	FOREIGN KEY (FK_CategoryID) REFERENCES Category (PK_CategoryID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Eventcategory ADD CONSTRAINT FK_Eventcategory_Event
	FOREIGN KEY (FK_EventID) REFERENCES Event (PK_EventID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Organizationuser ADD CONSTRAINT FK_OrganizationUser_has
	FOREIGN KEY (FK_OrganizationID) REFERENCES Organization (PK_OrganizationID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Photo ADD CONSTRAINT FK_Photo_has
	FOREIGN KEY (FK_UserID) REFERENCES "User" (PK_UserID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Ticket ADD CONSTRAINT FK_Ticket_User
	FOREIGN KEY (FK_UserID) REFERENCES "User" (PK_UserID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Ticket ADD CONSTRAINT FK_Ticket_Event
	FOREIGN KEY (FK_EventID) REFERENCES Event (PK_EventID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "User" ADD CONSTRAINT FK_User_Userdetails
	FOREIGN KEY (FK_UserdetailsID) REFERENCES Userdetails (PK_UserdetailsID) ON DELETE No Action ON UPDATE No Action
;


ALTER TABLE Userrolepermission ADD CONSTRAINT FK_UserRolePermission_Permission
	FOREIGN KEY (FK_PermissionID) REFERENCES Permission (PK_PermissionID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Userrolepermission ADD CONSTRAINT FK_UserRolePermission_UserRole
	FOREIGN KEY (FK_UserroleID) REFERENCES Userrole (PK_UserroleID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Userroleuser ADD CONSTRAINT FK_UserRoleUser_User
	FOREIGN KEY (FK_UserID) REFERENCES "User" (PK_UserID) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Userroleuser ADD CONSTRAINT FK_UserRoleUser_UserRole
	FOREIGN KEY (FK_UserroleID) REFERENCES Userrole (PK_UserroleID) ON DELETE No Action ON UPDATE No Action
;


 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 
