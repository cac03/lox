package com.caco3.lox.statement;

import com.caco3.lox.expression.Expression;
import com.caco3.lox.lexer.Token;
import com.caco3.lox.statement.visitor.StatementVisitor;
import com.caco3.lox.util.Assert;
import com.caco3.lox.util.Nullable;
import lombok.Value;

@Value(staticConstructor = "of")
public class ReturnStatement implements Statement {
    Token returnToken;
    @Nullable
    Expression expression;
    Token semicolon;

    @Override
    public void accept(StatementVisitor statementVisitor) {
        Assert.notNull(statementVisitor, "statementVisitor == null");

        statementVisitor.visitReturnStatement(this);
    }
}
