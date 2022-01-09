package vanhack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Compiler {
  public List<String> compile(String prog) {
    return pass3(pass2(pass1(prog)));
  }
  HashMap<String, Integer> argumentMap;

  /**
   * Returns an un-optimized AST
   */
  public Ast pass1(String prog) {
    Deque<String> tokens = tokenize(prog);
    argumentMap = mapArgumentToPostion(tokens);
    return parseExpression(tokens);
  }

  private Ast parseExpression(final Deque<String> tokens) {
    Ast leftHandSide = parseItem(tokens);
    String token = tokens.peek();
    while (token.equals("+") || token.equals("-")) {
      tokens.pop();
      final Ast rightHandSide = parseTerm(tokens);
      if (token.equals("+")) {
        leftHandSide = new BinOp("+", leftHandSide, rightHandSide);
      } else {
        leftHandSide = new BinOp("-", leftHandSide, rightHandSide);

      }
      token = tokens.peek();
    }
    return leftHandSide;
  }

  private Ast parseTerm(final Deque<String> tokens) {
     Ast leftHandSide = parseItem(tokens);

    String token = tokens.peek();
    while (token.equals("*") || token.equals("/")) {
      tokens.pop();
      final Ast rightHandSide = parseItem(tokens);
      if (token.equals("/")) {
        leftHandSide = new BinOp("/", leftHandSide, rightHandSide);
      } else {
        leftHandSide = new BinOp("*", leftHandSide, rightHandSide);
      }
      token = tokens.peek();
    }
    return leftHandSide;
  }

  private Ast parseItem(final Deque<String> tokens) {
    final String token = tokens.peek();
    tokens.pop();
    if (Character.isAlphabetic(token.charAt(0))) {
      return new UnOp("arg", argumentMap.get(token));
    }
    if (Character.isDigit(token.charAt(0))) {
      return new UnOp("imm", Integer.parseInt(token));
    }

    Ast expression = new UnOp("", 0);
    if (token.equals("(")) {
      expression = parseExpression(tokens);
    }
    tokens.pop();
    return expression;
  }

  private HashMap<String, Integer> mapArgumentToPostion(Deque<String> tokens) {
    final HashMap<String, Integer> result = new HashMap<>();
    //remove the opening bracket. We were to assume always correct syntax in the question.
    tokens.pop();
    int position = 0;
    while (!tokens.peek().equals("$")) {
      final String argument = tokens.pop();
      if (argument.equals("]")) {
        break;
      }
      result.put(argument, position);
      position++;
    }
    return result;
  }

  /**
   * Returns an AST with constant expressions reduced
   */
  public Ast pass2(Ast ast) {
    return new UnOp("", 0);
  }

  /**
   * Returns assembly instructions
   */
  public List<String> pass3(Ast ast) {
    return List.of();
  }

  private static Deque<String> tokenize(String prog) {
    Deque<String> tokens = new LinkedList<>();
    Pattern pattern = Pattern.compile("[-+*/()\\[\\]]|[a-zA-Z]+|\\d+");
    Matcher m = pattern.matcher(prog);
    while (m.find()) {
      tokens.add(m.group());
    }
    tokens.add("$"); // end-of-stream
    return tokens;
  }
}