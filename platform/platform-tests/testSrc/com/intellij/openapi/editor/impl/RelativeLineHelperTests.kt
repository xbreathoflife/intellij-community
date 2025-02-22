// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.openapi.editor.impl

class RelativeLineHelperTests : AbstractEditorTest() {
  fun `test relative line above caret`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    assertEquals(-1, RelativeLineHelper.getRelativeLine(editor, 2, 1))
  }

  fun `test relative line below caret`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    assertEquals(1, RelativeLineHelper.getRelativeLine(editor, 2, 3))
  }

  fun `test relative line at caret`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 2, 2))
  }

  fun `test hybrid line at caret`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    assertEquals(3, RelativeLineHelper.getHybridLine(editor, 2, 2))
  }

  fun `test relative line above caret in fold`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    foldLines(2, 3)
    assertEquals(-1, RelativeLineHelper.getRelativeLine(editor, 2, 1))
  }

  fun `test relative line below caret in fold`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    foldLines(2, 3)
    assertEquals(1, RelativeLineHelper.getRelativeLine(editor, 2, 4))
  }

  fun `test relative line at caret in fold`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    foldLines(2, 3)
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 2, 2))
  }

  fun `test hybrid line at caret in fold`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    foldLines(2, 3)
    assertEquals(3, RelativeLineHelper.getHybridLine(editor, 2, 2))
  }

  fun `test line number for fold below caret`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    foldLines(2, 3)
    assertEquals(1, RelativeLineHelper.getRelativeLine(editor, 1, 2))
    assertEquals(1, RelativeLineHelper.getRelativeLine(editor, 1, 3))
    assertEquals(2, RelativeLineHelper.getRelativeLine(editor, 1, 4))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 1, 1))
  }

  fun `test line number for fold above caret`() {
    initText("line 1\nline 2\nline 3\nline 4\nline 5\n")
    foldLines(2, 3)
    assertEquals(-2, RelativeLineHelper.getRelativeLine(editor, 4, 1))
    assertEquals(-1, RelativeLineHelper.getRelativeLine(editor, 4, 2))
    assertEquals(-1, RelativeLineHelper.getRelativeLine(editor, 4, 3))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 4, 4))
  }

  fun `test fold inside line`() {
    val text = """
      line 0
      line 1
      
      aaa
      
      
      bbb
      
      line 5
    """.trimIndent()
    initText(text)

    foldRegion(text.indexOf("aaa") + 3, text.indexOf("bbb"))
    assertEquals(-3, RelativeLineHelper.getRelativeLine(editor, 3, 0))
    assertEquals(-2, RelativeLineHelper.getRelativeLine(editor, 3, 1))
    assertEquals(-1, RelativeLineHelper.getRelativeLine(editor, 3, 2))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 3, 3))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 3, 4))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 3, 5))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 3, 6))
    assertEquals(1, RelativeLineHelper.getRelativeLine(editor, 3, 7))
    assertEquals(2, RelativeLineHelper.getRelativeLine(editor, 3, 8))

    assertEquals(-3, RelativeLineHelper.getRelativeLine(editor, 4, 0))
    assertEquals(-2, RelativeLineHelper.getRelativeLine(editor, 4, 1))
    assertEquals(-1, RelativeLineHelper.getRelativeLine(editor, 4, 2))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 4, 3))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 4, 4))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 4, 5))
    assertEquals(0, RelativeLineHelper.getRelativeLine(editor, 4, 6))
    assertEquals(1, RelativeLineHelper.getRelativeLine(editor, 4, 7))
    assertEquals(2, RelativeLineHelper.getRelativeLine(editor, 4, 8))
  }

  /**
   * @param endLine is inclusive
   */
  private fun foldLines(startLine: Int, endLine: Int) {
    val startOffset = editor.document.getLineStartOffset(startLine)
    val endOffset = editor.document.getLineEndOffset(endLine)
    foldRegion(startOffset, endOffset)
  }

  private fun foldRegion(startOffset: Int, endOffset: Int) {
    val foldingModel = editor.foldingModel
    foldingModel.runBatchFoldingOperation {
      val foldRegion = editor.foldingModel.addFoldRegion(startOffset, endOffset, "...")
      foldRegion?.isExpanded = false
    }
  }
}