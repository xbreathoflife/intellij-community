package com.siyeh.igfixes.performance.replace_with_isempty;

public class Ternary {
  public void someAction(boolean flag, String s1, String s2) {
    if ((flag ? s1 : s2) != null && (flag ? s1 : s2).isEmpty(<caret>)) {
      System.out.println("");
    }
  }
}