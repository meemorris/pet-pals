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

INSERT INTO users (username,password_hash,role) VALUES ('mustLuvDogs','$2a$10$sD6Yuh5.Vq5KF79l16B8e.vTiIxWtAFp9yXucn5EKMprdTgSmO.lm','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('catlady4lyfe','$2a$10$sD6Yuh5.Vq5KF79l16B8e.vTiIxWtAFp9yXucn5EKMprdTgSmO.lm','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('petlover2021','$2a$10$sD6Yuh5.Vq5KF79l16B8e.vTiIxWtAFp9yXucn5EKMprdTgSmO.lm','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('mypetsrok','$2a$10$sD6Yuh5.Vq5KF79l16B8e.vTiIxWtAFp9yXucn5EKMprdTgSmO.lm','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('petsrbest','$2a$10$sD6Yuh5.Vq5KF79l16B8e.vTiIxWtAFp9yXucn5EKMprdTgSmO.lm','ROLE_USER');

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

INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, pic, lat, lng) VALUES(1,'Matt','McUserson','user@gmail.com',null,'1776 Mentor Ave','Cincinnati','OH','45212','I love pets and Tech Elevator','https://friendlystock.com/wp-content/uploads/2019/10/1-black-man-holding-puppy-dog-cartoon-clipart.jpg','39.15239450000001', '-84.4667027');
INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, pic, lat, lng) VALUES(2,'Adminnie','McUserson','admin@gmail.com',null,'1776 Mentor Ave','Cincinnati','OH','45212','I have two cats that I love so much. Thinking of getting a pet rabbit, too.','https://png.pngtree.com/png-clipart/20190927/ourlarge/pngtree-side-face-beautiful-retro-trend-woman-png-image_1748687.jpg','39.15239450000001', '-84.4667027');
INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, pic, lat, lng) VALUES(3,'Sally','Smith','petlover2021@gmail.com','513-555-1234','300 W Loveland Ave','Loveland','OH','45140','Pets are amazing! Excited for my pets to make new friends.','https://img2.pngio.com/animated-faces-my-hero-design-clip-art-woman-singing-angry-woman-female-cartoon-faces-png-820_1059.png', '39.2684903', '-84.2593833');
INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, pic, lat, lng) VALUES(4,'Joe','McGuy','mypetsrok@gmail.com','513-555-9876','1776 Mentor Ave','Cincinnati','OH','45212','I love pets and Tech Elevator', 'https://coursereport-production.imgix.net/uploads/school/logo/259/original/mark.png?w=200&h=200','39.15239450000001', '-84.4667027');
INSERT INTO accounts (user_id, first_name, last_name, email, phone, address, city, state, zip, bio, lat, lng) VALUES(5,'Jane','Johnson','petsrbest@gmail.com',null,'1776 Mentor Ave','Cincinnati','OH','45212','Animals are better than humans!', '39.15239450000001', '-84.4667027');
               


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
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pic) VALUES ('Quilliam', 5, 'Hedgehog', 1, 2020, 'Relaxed', 'Shy', 'Curious', 'https://scontent-ort2-2.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/163175945_463728028084756_1030008473754746239_n.jpg?tp=1&_nc_ht=scontent-ort2-2.cdninstagram.com&_nc_cat=104&_nc_ohc=y1-u1u-xJosAX_1qmJX&edm=AP_V10EAAAAA&ccb=7-4&oh=2860f21f232d002c06c52d79712aaf35&oe=60907661&_nc_sid=4f375e');
INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Ollie', 3, 'Dog', 'Golden Retriever', 17, 2021, 'Energetic', 'Friendly', 'Curious', 'Ollie likes cuddles and attention.', 'https://www.rd.com/wp-content/uploads/2021/01/GettyImages-588935825.jpg');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Toby', 4, 'Dog', 54, 2017, 'Relaxed', 'Shy', 'Curious', 'Toby is a bit quirky, and loves learning all that he can about the world around him.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxL5QtpvnUtBaMKw7FN0k4wslIkPW50eGQnQ&usqp=CAU');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Tucker', 5, 'Dog', 26, 2016, 'Energetic', 'Friendly', 'Curious', 'Tucker loves to frolick in open spaces.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXKDSv9C9N0TFrJG0rVHyQgIlIdr7UTF2AfA&usqp=CAU');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Mr. Moe', 3, 'Hedgehog', 1, 2018, 'Energetic', 'Friendly', 'Apathetic', 'Mr. Moe is an albino hedgie who love to be green for St. Pattys Day!', 'https://scontent-ort2-1.xx.fbcdn.net/v/t1.18169-9/12108979_10206868993657800_8039385654076157750_n.jpg?_nc_cat=108&ccb=1-3&_nc_sid=8bfeb9&_nc_ohc=4Af94S87aJMAX8GoeNj&_nc_ht=scontent-ort2-1.xx&oh=967adb978cb989738121414a43b0659e&oe=609E5026');
INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Lego', 2, 'Cat', 'Calico', 7, 2014, 'Relaxed', 'Shy', 'Apathetic', 'Lego may have been born without legs, but she loves chasing strings and snuggling', 'https://scontent-ort2-1.xx.fbcdn.net/v/t35.18174-12/10492655_10203307168534398_883880016_o.jpg?_nc_cat=108&ccb=1-3&_nc_sid=8bfeb9&_nc_ohc=zGTkrqswBFUAX9Twjwm&_nc_ht=scontent-ort2-1.xx&oh=5a00cc09a4486b325b11441dfd2fc514&oe=607A8065');
INSERT INTO pets (name, user_id, species, breed, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, bio, pic) VALUES ('Carrots', 5, 'Rabbit', 'Holland Lop', 4, 2018, 'Energetic', 'Friendly', 'Curious', 'Carrots loves hopping and making friends.', 'https://i.redd.it/zrowzdyln1o41.jpg');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pic) VALUES ('Samara', 3, 'Cat', 9, 2016, 'Relaxed', 'Friendly', 'Apathetic', 'https://c.files.bbci.co.uk/12A9B/production/_111434467_gettyimages-1143489763.jpg');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pic) VALUES ('Snuggles', 4, 'Alligator', 60, 2017, 'Relaxed', 'Friendly', 'Curious', 'https://cdn.pixabay.com/photo/2018/11/19/09/01/aligator-3824881_1280.jpg');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pic) VALUES ('Jai', 1, 'Parrot', 1, 2020, 'Energetic', 'Shy', 'Curious', 'https://macawparroteggsforsale.com/wp-content/uploads/2019/06/vira4.jpg');
INSERT INTO pets (name, user_id, species, weight, birth_year, energetic_relaxed, shy_friendly, apathetic_curious, pic) VALUES ('Alexander Hamilton', 3, 'Hamster', 1, 2020, 'Energetic', 'Shy', 'Curious', 'https://hamsterpal.com/wp-content/uploads/2019/08/best-hamster-food.jpg');

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
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (2, '7850 VOA Park Dr', 'Cincinnati', 'Ohio', '45069', '2021-04-17 12:00:00', '39.3686391', '-84.346541');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (3, '4686 Old Irwin Simpson Rd', 'Mason', 'Ohio', '45040', '2021-04-18 03:00:00', '39.3090199', '-84.3074929');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (4, '4335 Glendale Milford Rd', 'Blue Ash', 'Ohio', '45242', '2021-04-20 10:00:00', '39.2519331', '-84.38789640000002');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (5, '5057 Wooster Pike', 'Cincinnati', 'Ohio', '45226', '2021-04-19 19:00:00', '39.12185119999999', '-84.403733');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (10, '3006 Westwood Northern Blvd', 'Cincinnati', 'Ohio', '45211', '2021-04-28 09:30:00', '39.1575827', '-84.59373169999999');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (7, '4335 Glendale Milford Rd', 'Blue Ash', 'Ohio', '45242', '2021-04-17 12:00:00', '39.2303513', '-84.71075429999999');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (11, '6701 Kellogg Rd', 'Cincinnati', 'Ohio', '45230', '2021-04-25 10:30:00', '39.048995', '-84.3972387');
INSERT INTO playdates (pet_id, address, city, state, zip, date, lat, lng) VALUES (12, '2234 Eastern Ave', 'Covington', 'KY', '41014', '2021-04-30 18:00:00', '39.0640171', '-84.49827569999999');


CREATE TABLE playdates_pets (
        playdate_id int NOT NULL,
        pet_id int NOT NULL,
        is_host boolean NOT NULL,
        deleted_date date,
        
        CONSTRAINT FK_playdate FOREIGN KEY(playdate_id) REFERENCES playdates(playdate_id),
        CONSTRAINT FK_pet FOREIGN KEY(pet_id) REFERENCES pets(pet_id)
);

INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (1, 2, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (2, 2, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (3, 3, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (4, 4, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (5, 5, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (6, 10, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (7, 7, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (8, 11, true);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (9, 12, true);

INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (4, 6, false);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (5, 9, false);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (6, 12, false);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (7, 13, false);
INSERT INTO playdates_pets (playdate_id, pet_id, is_host) VALUES (8, 1, false);

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

INSERT INTO messages (user_id, message, posted_date) VALUES (3, 'Pet Pals is amazing!', '2021-04-12 10:00:00');
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (1, 'Gimli had so much fun bird watching with the fellas today.', '2021-04-13 2:45:00', 1);
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (5, 'Off to the park to look for worms!!', '2021-04-14 11:12:00', 4);
INSERT INTO messages (user_id, message, posted_date) VALUES (4, 'I just joined Pet Pals! So excited to find some friends for my pets :)', '2021-04-15 2:01:00');
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (2, 'The forecast today calls for sunbathing in the park.', '2021-04-15 9:02:00', 2);
INSERT INTO messages (user_id, message, posted_date, pet_id) VALUES (1, 'Sable met her best friend through Pet Pals <3', '2021-04-14 3:09:00', 3);

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO final_capstone_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO final_capstone_owner;

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO final_capstone_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO final_capstone_appuser;

SELECT nextval('messages_message_id_seq');


COMMIT TRANSACTION;

