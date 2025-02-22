// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.platform.workspaceModel.storage.testEntities.entities

import com.intellij.workspaceModel.storage.DummyParentEntitySource
import com.intellij.workspaceModel.storage.EntitySource

data class SampleEntitySource(val name: String) : EntitySource
object MySource : EntitySource {
  override fun toString(): String = "MySource"
}

object AnotherSource : EntitySource {
  override fun toString(): String = "AnotherSource"
}

object MyDummyParentSource : DummyParentEntitySource {
  override fun toString(): String = "DummyParent"
}