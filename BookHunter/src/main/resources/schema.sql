CREATE TABLE user_profile (
     id UUID NOT NULL,
     nick VARCHAR(50) NOT NULL,
     name VARCHAR(128) NOT NULL,
     latitude DECIMAL NOT NULL,
     longitude DECIMAL NOT NULL,
     gender VARCHAR(50) NOT NULL,
     age INTEGER NOT NULL,
     PRIMARY KEY (id)
);