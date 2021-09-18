package lox.tool;

import lox.AstPrinter;
import lox.Expr.Binary;
import lox.Expr.Grouping;
import lox.Expr.Literal;
import lox.Expr.Unary;
import lox.Token;
import lox.TokenType;

public class AstPrinterSimulator {

  public static void main(String[] args) {
    final Binary expression = new Binary(
        new Unary(new Token(TokenType.MINUS, "-", null, 1), new Literal(123)),
        new Token(TokenType.STAR, "*", null, 1), new Grouping(new Literal(45.67)));
    System.out.println(new AstPrinter().print(expression));
  }
}
