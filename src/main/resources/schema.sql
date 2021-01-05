CREATE TABLE users (
    id IDENTITY,
    user CHAR NOT NULL,
    room INT NOT NULL
);
CREATE TABLE rooms (
  id IDENTITY
);

CREATE TABLE word (
  id IDENTITY,
  room INT NOT NULL,
  user CHAR NOT NULL,
  time CHAR NOT NULL,
  word CHAR NOT NULL

);
