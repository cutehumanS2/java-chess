CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS chess.room (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS chess.movement (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    source_file VARCHAR(8) NOT NULL,
    source_rank VARCHAR(8) NOT NULL,
    target_file VARCHAR(8) NOT NULL,
    target_rank VARCHAR(8) NOT NULL,
    FOREIGN KEY (room_id) REFERENCES room (id),
    UNIQUE KEY unique_movement (room_id, source_file, source_rank, target_file, target_rank)
);

CREATE DATABASE IF NOT EXISTS chess_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE chess_test.room LIKE chess.room;
CREATE TABLE chess_test.movement LIKE chess.movement;



