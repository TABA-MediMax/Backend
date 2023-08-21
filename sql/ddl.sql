CREATE TABLE "image" (
	"id"	INTEGER NOT NULL UNIQUE,
	"path"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "member" (
	"id"	INTEGER NOT NULL UNIQUE,
	"email"	TEXT NOT NULL,
	"agreed_to_all"	boolean NOT NULL,
	"agreed_to_optional_third_party"	boolean,
	"agreed_to_service_access"	boolean,
	PRIMARY KEY("id" AUTOINCREMENT)
);