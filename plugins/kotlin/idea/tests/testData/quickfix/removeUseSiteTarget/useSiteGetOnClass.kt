// "Remove useless 'get:' target (changes semantics, see: https://youtrack.jetbrains.com/issue/KT-48141)" "false"
// IGNORE_FIR
// ACTION: Create test
// ACTION: Make internal
// ACTION: Make private
// WITH_STDLIB
// ERROR: This annotation is not applicable to target 'class' and use site target '@get'
<caret>@get:Deprecated("", level = DeprecationLevel.ERROR)
class Foo
