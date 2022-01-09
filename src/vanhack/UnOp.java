package vanhack;

public class UnOp implements Ast {

  private final String operation;
  private final int number;

  public UnOp(final String operation, final int number) {
    this.operation = operation;
    this.number = number;
  }

  public final int n() {
    return number;
  }

  @Override
  public final String op() {
    return operation;
  }
}
