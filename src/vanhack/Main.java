package vanhack;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    String prog = "[ x y z ] ( 2*3*x + 5*y - 3*z ) / (1 + 3 + 2*2)";
    String prog2 = "[ a b ] a*a + b*b";
    String prog3 = " [ first second ] (first + second) / 2";

    // {'op':'/','a':{'op':'-','a':{'op':'+','a':{'op':'*','a':{'op':'*','a':{'op':'imm','n':2},
    // 'b':{'op':'imm','n':3}},'b':{'op':'arg','n':0}},'b':{'op':'*','a':{'op':'imm','n':5},
    // 'b':{'op':'arg','n':1}}},'b':{'op':'*','a':{'op':'imm','n':3},'b':{'op':'arg','n':2}}},
    // 'b':{'op':'+','a':{'op':'+','a':{'op':'imm','n':1},'b':{'op':'imm','n':3}},
    // 'b':{'op':'*','a':{'op':'imm','n':2},'b':{'op':'imm','n':2}}}}
    Ast t1 = new BinOp("/", new BinOp("-", new BinOp("+",
        new BinOp("*", new BinOp("*", new UnOp("imm", 2), new UnOp("imm", 3)), new UnOp("arg", 0)),
        new BinOp("*", new UnOp("imm", 5), new UnOp("arg", 1))),
        new BinOp("*", new UnOp("imm", 3), new UnOp("arg", 2))),
        new BinOp("+", new BinOp("+", new UnOp("imm", 1), new UnOp("imm", 3)),
            new BinOp("*", new UnOp("imm", 2), new UnOp("imm", 2))));
    final Compiler compiler = new Compiler();
    Ast p1 = compiler.pass1(prog);
    System.out.println(p1.equals(t1));


    // {'op':'/','a':{'op':'-','a':{'op':'+','a':{'op':'*','a':{'op':'imm','n':6},'b':{'op':'arg','n':0}},'b':{'op':'*','a':{'op':'imm','n':5},'b':{'op':'arg','n':1}}},'b':{'op':'*','a':{'op':'imm','n':3},'b':{'op':'arg','n':2}}},'b':{'op':'imm','n':8}}
    Ast t2 = new BinOp("/", new BinOp("-", new BinOp("+", new BinOp("*", new UnOp("imm", 6),
        new UnOp("arg", 0)), new BinOp("*", new UnOp("imm", 5), new UnOp("arg", 1))), new BinOp("*", new UnOp("imm", 3), new UnOp("arg", 2))), new UnOp("imm", 8));
    Ast p2 = compiler.pass2(p1);
    System.out.println(p2.equals(t2));


    List<String> p3 = compiler.pass3(p2);
    System.out.printf("prog(4,0,0) == %d  should be  %d%n",3, Simulator.simulate(p3, 4, 0, 0));
    System.out.printf("prog(4,8,0) == %d  should be  %d%n", 8, Simulator.simulate(p3, 4, 8, 0));
    System.out.printf("prog(4,8,16) == %d  should be  %d%n", 2, Simulator.simulate(p3, 4, 8, 16));
  }
}
