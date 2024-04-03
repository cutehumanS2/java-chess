CREATE DATABASE IF NOT EXISTS chess DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS chess.chess_game (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    turn VARCHAR(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS chess.movement (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    chess_game_id BIGINT NOT NULL,
    source_file VARCHAR(8) NOT NULL,
    source_rank VARCHAR(8) NOT NULL,
    target_file VARCHAR(8) NOT NULL,
    target_rank VARCHAR(8) NOT NULL,
    FOREIGN KEY (chess_game_id) REFERENCES chess_game (id),
    UNIQUE KEY unique_movement (source_file, source_rank, target_file, target_rank)
);


CREATE DATABASE IF NOT EXISTS chess_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE chess_test.chess_game LIKE chess.chess_game;
CREATE TABLE chess_test.movement LIKE chess.movement;



