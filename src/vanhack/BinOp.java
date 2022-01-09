package vanhack;

public class BinOp implements Ast {

  private final String operation;
  private final Ast subtreeA;
  private final Ast subtreeB;

  public BinOp(final String s,final Ast subtreeA,final Ast subtreeB) {
    this.operation = s;
    this.subtreeA = subtreeA;
    this.subtreeB = subtreeB;
  }

  public final Ast a() {
    return subtreeA;
  }

  public final Ast b() {
    return subtreeB;
  }

  @Override
  public final String op() {
    return operation;
  }
}
