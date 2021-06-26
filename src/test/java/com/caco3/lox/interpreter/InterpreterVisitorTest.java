package com.caco3.lox.interpreter;

import com.caco3.lox.lexer.DefaultLexer;
import com.caco3.lox.lexer.Lexer;
import com.caco3.lox.lexer.Token;
import com.caco3.lox.parser.DefaultParser;
import com.caco3.lox.parser.Parser;
import com.caco3.lox.statement.Statement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class InterpreterVisitorTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStream);
    private final InterpreterVisitor interpreterVisitor = InterpreterVisitor.of(printStream);

    private String getOutput() {
        printStream.flush();
        return normalize(outputStream.toString(StandardCharsets.UTF_8));
    }

    private String normalize(String string) {
        return string.replace("\r\n", "\n");
    }

    @ParameterizedTest
    @MethodSource(value = "testInputs")
    void interpretationTest(String input, String expectedOutput) {
        Lexer lexer = new DefaultLexer(input);
        List<Token> tokens = lexer.parseTokens();
        Parser parser = new DefaultParser(tokens);
        List<Statement> statements = parser.parseStatements();

        statements.forEach(it -> it.accept(interpreterVisitor));

        String actualOutput = getOutput();

        assertThat(actualOutput)
                .isEqualTo(expectedOutput);
    }

    public static Stream<Arguments> testInputs() {
        return Stream.of(
                Arguments.of("print 123;", "123"),
                Arguments.of("print 1 + 3 * 5 - 3;", "13"),
                Arguments.of("print 3 + 13 / 2;", "9"),
                Arguments.of("print -5;", "-5"),
                Arguments.of("print \"abc\" + \" \" + \"def\";", "abc def"),
                Arguments.of("print 1 > 2;", "false"),
                Arguments.of("print !(1 > 2);", "true"),
                Arguments.of("print 1 == 2;", "false"),
                Arguments.of("print 1 != 2;", "true"),
                Arguments.of("print 1 >= 2;", "false"),
                Arguments.of("print 1 < 2;", "true"),
                Arguments.of("print 1 <= 2;", "true")
        );
    }
}