package lox.tool;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {

  public static void main(String[] args)
      throws FileNotFoundException, UnsupportedEncodingException {
    if (args.length != 1) {
      System.err.println("Usage: generate_ast <output directory>");
      System.exit(64);
    }
    final String outputDir = args[0];
    defineAst(outputDir, "Expr", Arrays.asList("Binary : Expr left, Token operator, Expr right",
        "Grouping : Expr expression", "Literal : Object value",
        "Unary : Token operator, Expr right"));

  }

  private static void defineAst(String outputDir, String baseName, List<String> types)
      throws FileNotFoundException, UnsupportedEncodingException {
    final String path = outputDir + "/" + baseName + ".java";
    final PrintWriter writer = new PrintWriter(path, "UTF-8");

    writer.println("package lox;");
    writer.println();
    writer.println("import java.util.List;");
    writer.println();
    writer.println("abstract class " + baseName + " {");
    for (String type : types) {
      final String className = type.split(":")[0].trim();
      final String fields = type.split(":")[1].trim();
      defineType(writer, baseName, className, fields);
    }
    writer.println("}");
    writer.close();
  }

  private static void defineType(PrintWriter writer, String baseName, String className,
      String fieldList) {
    writer.println("  static class " + className + " extends " + baseName + " {");

    writer.println("    " + className + "(" + fieldList + "){");
    String[] fields = fieldList.split(", ");
    for (String field : fields) {
      final String name = field.split(" ")[1];
      writer.println(" this." + name + " = " + name + ";");
    }
    writer.println("}");
    writer.println();
    for (String field : fields) {
      writer.println("final " + field + ";");
    }
    writer.println("}");
  }

}