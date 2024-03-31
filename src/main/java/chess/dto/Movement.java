package chess.dto;

import chess.domain.square.Square;

public record Movement(Square source, Square target) {
}
