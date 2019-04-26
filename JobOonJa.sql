CREATE TABLE user(
    id CHAR(20),
    firstname CHAR(20),
    lastname CHAR(20),
    username CHAR(20),
    password CHAR(20),
    jobTitle CHAR(20),
    profilePic CHAR(40),
    bio CHAR(100),
    PRIMARY KEY(id),
    UNIQUE(username)
);

CREATE TABLE project(
    id CHAR(20),
    title CHAR(20),
    description CHAR(100),
    imageURL CHAR(40),
    budget INTEGER,
    deadLine INTEGER,
    creationDate INTEGER,
    PRIMARY KEY(id)
);

CREATE TABLE skill(
    skillName CHAR(15),
    PRIMARY KEY(skillName)
);

CREATE TABLE userSkill(
    userId CHAR(20),
    skillName CHAR(15),
    point INTEGER,
    PRIMARY KEY(userId, skillName),
    FOREIGN KEY(skillName)
    REFERENCES skill,
    FOREIGN KEY (userId)
    REFERENCES user
);

CREATE TABLE endorse(
    endorserId CHAR(20),
    endorsedId CHAR(20),
    skillName CHAR(15),
    PRIMARY KEY(endorserId, endorsedId, skillName),
    FOREIGN KEY (endorsedId, skillName)
    REFERENCES userSkill,
    FOREIGN KEY (endorserId)
    REFERENCES user
);

CREATE TABLE projectSkill(
    projectId CHAR(20),
    skillName CHAR(15),
    point INTEGER,
    PRIMARY KEY(projectId, skillName),
    FOREIGN KEY(skillName)
    REFERENCES skill,
    FOREIGN KEY (projectId)
    REFERENCES project
);

CREATE TABLE bid(
    userId CHAR(20),
    projectId CHAR(20),
    offer INTEGER,
    PRIMARY KEY(userId, projectId),
    FOREIGN KEY (userId)
    REFERENCES user,
    FOREIGN KEY (projectId)
    REFERENCES project
);

CREATE TABLE auction(
    winner CHAR(20),
    projectId CHAR(20),
    PRIMARY KEY(projectId, winner),
    FOREIGN KEY (winner)
    REFERENCES user,
    FOREIGN KEY (projectId)
    REFERENCES project
);