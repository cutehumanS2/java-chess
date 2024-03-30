package chess.view;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum Command {

    START("^start$"),
    MOVE("^move [a-h][1-8] [a-h][1-8]$"),
    END("^end$"),
    STATUS("^status$"),
    ;

    private static final String ERROR_INVALID_COMMAND = " 은(는) 올바르지 않은 명령어 입니다.";

    private final Pattern format;

    Command(final String format) {
        this.format = Pattern.compile(format);
    }

    public static Command findByValue(final String value) {
        return Arrays.stream(values())
                .filter(command -> command.isMatchedCommandFormat(command.format, value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value + ERROR_INVALID_COMMAND));
    }

    public static void validateFormat(final String value) {
        boolean isInValidFormat = Arrays.stream(values())
                .noneMatch(command -> command.isMatchedCommandFormat(command.format, value));
        if (isInValidFormat) {
            throw new IllegalArgumentException(value + ERROR_INVALID_COMMAND);
        }
    }

    private boolean isMatchedCommandFormat(final Pattern format, final String value) {
        return format.matcher(value).matches();
    }
}
