BEGIN TRANSACTION;

DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS playdates_pets;
DROP TABLE IF EXISTS playdates;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS pets;
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

CREATE TABLE accounts (
        account_id SERIAL,
        user_id int NOT NULL UNIQUE,
        first_name varchar(32) NOT NULL,
        last_name varchar(32) NOT NULL,
        email varchar(64) NOT NULL,
        phone varchar(20),
        address varchar(64) NOT NULL,
        city varchar(64) NOT NULL,
        state varchar(32) NOT NULL,
        zip varchar(10) NOT NULL,
        bio varchar(1000),
        pic varchar(2083),
        lat varchar(32),
        lng varchar(32),
        CONSTRAINT PK_account PRIMARY KEY (account_id),
        CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, pic, lat, lng) VALUES(2,'Adminnie','McUserson','admin@gmail.com',null,'1776 Mentor Ave','Cincinnati','OH','45212','I love pets and Tech Elevator','https://coursereport-production.imgix.net/uploads/school/logo/259/original/mark.png?w=200&h=200', '39.15239450000001', '-84.4667027');
INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, lat, lng) VALUES(1,'User','McUserson','user@gmail.com',null,'1776 Mentor Ave','Cincinnati','OH','45212','I love pets and Tech Elevator','39.15239450000001', '-84.4667027');
               


CREATE TABLE pets (
        pet_id SERIAL,
        name varchar(64) NOT NULL,
        user_id int NOT NULL,
        species varchar(64) NOT NULL,
        breed varchar(64),
        weight integer NOT NULL,
        birth_year integer NOT NULL,
        energetic_relaxed varchar(64) NOT NULL,
        shy_friendly varchar(64) NOT NULL,
        apathetic_curious varchar(64) NOT NULL,
        bio varchar(1000),
        pic varchar(2083),
        CONSTRAINT PK_pet PRIMARY KEY (pet_id),
        CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users(user_id),
        
        CONSTRAINT check_energetic_relaxed CHECK(energetic_relaxed IN ('Energetic', 'Relaxed')),
        CONSTRAINT check_shy_friendly CHECK(shy_friendly IN ('Shy', 'Friendly')),
        CONSTRAINT check_apathetic_curious CHECK(apathetic_curious IN ('Apathetic','Curious'))
);

INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio) VALUES ('Gimli', 1, 'Cat', 'American Longhair', 8, 2020, 'Energetic', 'Friendly', 'Curious', 'Gimli likes to type along with dad while he works on school projects');
INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Jasper', 2, 'Cat', 'Russian Siberian', 19, 2010, 'Relaxed', 'Friendly', 'Apathetic', 'Jasper is the laziest, friendliest cat in all the land', 'https://scontent-ort2-2.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/96138189_241521896906719_5444118948239169433_n.jpg?tp=1&_nc_ht=scontent-ort2-2.cdninstagram.com&_nc_cat=107&_nc_ohc=ZgEO3E3PeeQAX8TvPcl&edm=AP_V10EAAAAA&ccb=7-4&oh=d80e497dfc4ea08a3fcfec87ccbcf5e8&oe=6092A5CC&_nc_sid=4f375e');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio) VALUES ('Sable', 1, 'Dog', 50, 2016, 'Energetic', 'Friendly', 'Curious', 'Sable likes to run around until she smashes into something.');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pic) VALUES ('Quilliam', 2, 'Hedgehog', 1, 2020, 'Relaxed', 'Shy', 'Curious', 'https://scontent-ort2-2.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/163175945_463728028084756_1030008473754746239_n.jpg?tp=1&_nc_ht=scontent-ort2-2.cdninstagram.com&_nc_cat=104&_nc_ohc=y1-u1u-xJosAX_1qmJX&edm=AP_V10EAAAAA&ccb=7-4&oh=2860f21f232d002c06c52d79712aaf35&oe=60907661&_nc_sid=4f375e');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Mr. Moe', 3, 'Hedgehog', 1, 2018, 'Energetic', 'Friendly', 'Apathetic', 'Mr. Moe is an albino hedgie who love to be green for St. Pattys Day!', 'https://scontent-ort2-1.xx.fbcdn.net/v/t1.18169-9/12108979_10206868993657800_8039385654076157750_n.jpg?_nc_cat=108&ccb=1-3&_nc_sid=8bfeb9&_nc_ohc=4Af94S87aJMAX8GoeNj&_nc_ht=scontent-ort2-1.xx&oh=967adb978cb989738121414a43b0659e&oe=609E5026');
INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Lego', 4, 'Cat', 'Calico', 7, 2014, 'Relaxed', 'Shy', 'Apathetic', 'Lego may have been born without legs, but she loves chasing strings and snuggling', 'https://scontent-ort2-1.xx.fbcdn.net/v/t35.18174-12/10492655_10203307168534398_883880016_o.jpg?_nc_cat=108&ccb=1-3&_nc_sid=8bfeb9&_nc_ohc=zGTkrqswBFUAX9Twjwm&_nc_ht=scontent-ort2-1.xx&oh=5a00cc09a4486b325b11441dfd2fc514&oe=607A8065');
INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Carrots', 5, 'Rabbit' 'Holland Lop' 4, 2018, 'Energetic', 'Friendly', 'Curious', 'Carrots loves hopping and making friends.', 'https://i.redd.it/zrowzdyln1o41.jpg');

CREATE TABLE playdates (
        playdate_id SERIAL,
        pet_id int NOT NULL,
        address varchar(64) NOT NULL,
        city varchar(64) NOT NULL,
        state varchar(32) NOT NULL,
        zip varchar(10) NOT NULL,
        date timestamp NOT NULL,
        lat varchar(32),
        lng varchar(32),
        deleted_date date,
        CONSTRAINT PK_playdate PRIMARY KEY (playdate_id),
        CONSTRAINT FK_pet FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
        
);

INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (2, '7850 VOA Park Dr', 'Cincinnati', 'Ohio', '45069', '2021-04-10 12:00:00', '39.3686391', '-84.346541');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (3, '4686 Old Irwin Simpson Rd', 'Mason', 'Ohio', ' 45040', '2021-04-11 03:00:00', '39.3090199', '-84.3074929');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (4, '4335 Glendale Milford Rd', 'Blue Ash', 'Ohio', ' 45242', '2021-04-15 10:00:00', '39.2519331', '-84.38789640000002');

CREATE TABLE playdates_pets (
        playdate_id int NOT NULL,
        pet_id int NOT NULL,
        is_host boolean NOT NULL,
        deleted_date date,
        
        CONSTRAINT FK_playdate FOREIGN KEY(playdate_id) REFERENCES playdates(playdate_id),
        CONSTRAINT FK_pet FOREIGN KEY(pet_id) REFERENCES pets(pet_id)
);

INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (1, 2, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (2, 3, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (3, 4, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (3, 2, false);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (3, 3, false);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (1, 1, false);

CREATE TABLE messages (
        message_id SERIAL,
        user_id int NOT NULL,
        message varchar(2000) NOT NULL,
        posted_date timestamp NOT NULL,
        pet_id int,
        
        CONSTRAINT PK_message PRIMARY KEY(message_id),
        CONSTRAINT FK_user FOREIGN KEY(user_id) REFERENCES users(user_id),
        CONSTRAINT FK_pet FOREIGN KEY(pet_id) REFERENCES pets(pet_id)
);

INSERT INTO messages (user_id, message, posted_date) VALUES (1, 'Pet Pals is amazing!', '2021-04-12 10:00:00');
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (1, 'Gimli had so much fun bird watching with the fellas today.', '2021-04-13 2:45:00', 1);
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (2, 'Off to the park to look for worms!!', '2021-04-14 11:00:00', 4);
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (1, 'Sable met her best friend through Pet Pals <3', '2021-04-14 3:00:00', 3);
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (2, 'The forecast today calls for sunbathing in the park.', '2021-04-15 9:00:00', 2);
COMMIT TRANSACTION;

