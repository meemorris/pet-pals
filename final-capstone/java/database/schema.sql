BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');


DROP TABLE IF EXISTS pets;

CREATE TABLE pets (
        pet_id SERIAL,
        name varchar(64) NOT NULL,
        user_id int NOT NULL,
        species varchar(64) NOT NULL,
        breed varchar(64),
        weight decimal(5,2) NOT NULL,
        birth_year integer NOT NULL,
        energetic_relaxed varchar(64) NOT NULL,
        shy_friendly varchar(64) NOT NULL,
        apathetic_curious varchar(64) NOT NULL,
        bio varchar(1000),
        CONSTRAINT PK_pet PRIMARY KEY (pet_id),
        CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
        
        CONSTRAINT check_energetic_relaxed CHECK(energetic_relaxed IN ('energetic', 'relaxed')),
        CONSTRAINT check_shy_friendly CHECK(shy_friendly IN ('shy', 'friendly')),
        CONSTRAINT check_apathetic_curious CHECK(apathetic_curious IN ('empathetic','curious'))
);




COMMIT TRANSACTION;
