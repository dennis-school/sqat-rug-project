package nl.dennis.api;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class CoverageChecker {
  private static PrintWriter methodOut;
  private static PrintWriter lineOut;
  private static final Set<MethodEntry> methodHits = new HashSet<>();
  private static final Set<LineEntry> lineHits = new HashSet<>();

  static {
    try {
      methodOut = new PrintWriter("methods-hits.csv");
      lineOut = new PrintWriter("line-hits.csv");
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }
  
  public static void hit(String className, String methodName) {
    methodHits.add(new MethodEntry(className, methodName));
    methodOut.printf("\"%s\",\"%s\"\n", className, methodName);
    methodOut.flush();
  }

  public static void hit(String className, String methodName, int line) {
    lineHits.add(new LineEntry(className, methodName, line));
    lineOut.printf("\"%s\",\"%s\",%d\n", className, methodName, line);
    methodOut.flush();
  }

  public static void writeToFile( ) {
    try (PrintWriter out = new PrintWriter("hits.csv")) {
      for (MethodEntry e : methodHits) {
        out.printf("\"%s\",\"%s\"\n", e.className, e.methodName);
      }
      for (LineEntry e : lineHits) {
        out.printf("\"%s\",\"%s\",%d\n", e.className, e.methodName, e.line);
      }
    } catch ( FileNotFoundException ex ) {
      ex.printStackTrace();
    }
  }

  private static class MethodEntry {
    public final String className;
    public final String methodName;

    public MethodEntry(String className, String methodName) {
      this.className = className;
      this.methodName = methodName;
    }
  }

  private static class LineEntry {
    public final String className;
    public final String methodName;
    public final int line;

    public LineEntry(String className, String methodName, int line) {
      this.className = className;
      this.methodName = methodName;
      this.line = line;
    }
  }
}
