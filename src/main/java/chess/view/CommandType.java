package chess.view;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum CommandType {

    START("^start$"),
    SHOW("^show$"),
    ENTER("^enter \\d+$"),
    CREATE("create [ㄱ-ㅎ가-힣a-zA-Z0-9]+$"),
    MOVE("^move [a-h][1-8] [a-h][1-8]$"),
    END("^end$"),
    STATUS("^status$"),
    NONE(""),
    ;

    private static final String ERROR_INVALID_COMMAND = " 은(는) 올바르지 않은 명령어 입니다.";

    private final Pattern format;

    CommandType(final String format) {
        this.format = Pattern.compile(format);
    }

    public static CommandType findByValue(final String value) {
        return Arrays.stream(values())
                .filter(commandType -> commandType.isMatchedCommandFormat(commandType.format, value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value + ERROR_INVALID_COMMAND));
    }

    // InputView에서 이 메서드로 검증 안 하면 무한 루프 발생
    public static void validateFormat(final String value) {
        boolean isInValidFormat = Arrays.stream(values())
                .noneMatch(commandType -> commandType.isMatchedCommandFormat(commandType.format, value));
        if (isInValidFormat) {
            throw new IllegalArgumentException(value + ERROR_INVALID_COMMAND);
        }
    }

    private boolean isMatchedCommandFormat(final Pattern format, final String value) {
        return format.matcher(value).matches();
    }
}
