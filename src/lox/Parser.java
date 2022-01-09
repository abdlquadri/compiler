package lox;

import java.util.List;
import lox.Expr.Binary;
import lox.Expr.Grouping;
import lox.Expr.Literal;
import lox.Expr.Unary;

public class Parser {

  private final List<Token> tokens;
  private int current = 0;

  public Parser(List<Token> tokens) {
    this.tokens = tokens;
  }

  Expr parse(){
    try {
      return expression();
    }catch (ParseError error){
      return null;
    }
  }

  private Expr expression() {
    return quality();
  }

  private Expr quality() {
    Expr expr = comparison();
    while (match(TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL)) {
      Token operator = previous();
      Expr right = comparison();
      new Expr.Binary(expr, operator, right);
    }
    return expr;
  }

  private Token previous() {
    return tokens.get(current - 1);
  }

  private boolean match(TokenType... types) {
    for (TokenType type : types) {
      if (check(type)) {
        advance();
        return true;
      }
    }
    return false;
  }

  private boolean check(TokenType type) {
    if (isAtEnd()) {
      return false;
    }
    return peek().type == type;
  }

  private Token peek() {
    return tokens.get(current);
  }

  private boolean isAtEnd() {
    return peek().type == TokenType.EOF;
  }

  private Token advance() {
    if (!isAtEnd()) {
      current++;
    }
    return previous();
  }

  private Expr comparison() {
    Expr expr = term();
    while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS,
        TokenType.LESS_EQUAL)) {
      final Token opeator = previous();
      Expr right = term();
      expr = new Expr.Binary(expr, opeator, right);
    }
    return expr;
  }

  private Expr term() {
    Expr expr = factor();

    while (match(TokenType.MINUS, TokenType.PLUS)) {
      final Token operator = previous();
      final Expr right = factor();
      expr = new Binary(expr, operator, right);
    }
    return expr;
  }

  private Expr factor() {
    Expr expr = unary();
    while (match(TokenType.SLASH, TokenType.STAR)) {
      final Token operator = previous();
      final Expr right = unary();
      expr = new Expr.Binary(expr, operator, right);
    }
    return expr;
  }

  private Expr unary() {
    if (match(TokenType.BANG, TokenType.MINUS)) {
      final Token operator = previous();
      final Expr right = unary();
      return new Unary(operator, right);
    }
    return primary();
  }

  private Expr primary() {
    if (match(TokenType.FALSE)) {
      return new Literal(false);
    }
    if (match(TokenType.TRUE)) {
      return new Literal(true);
    }
    if (match(TokenType.NIL)) {
      return new Literal(null);
    }
    if (match(TokenType.NUMBER, TokenType.STRING)) {
      return new Literal(previous()).literal;
    }
    if (match(TokenType.LEFT_PAREN)) {
      final Expr expr = expression();
      consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.");
      return new Grouping(expr);
    }
    throw error(peek(), "Expect expression.");
  }

  private Token consume(TokenType type, String message) {
    if (check(type)) {
      return advance();
    }
    throw error(peek(), message);
  }

  private ParseError error(Token token, String message) {
    Lox.error(token, message);
    return new ParseError();
  }

  private void synchronize(){
    advance();
    while (!isAtEnd()){
      if (previous().type==TokenType.SEMICOLON) return;

      switch (peek().type){
        case CLASS:
        case FUN:
        case VAR:
        case FOR:
        case IF:
        case WHILE:
        case PRINT:
        case RETURN:
       return;
      }
      advance();
    }
  }

  private static class ParseError extends RuntimeException {

  }
}
