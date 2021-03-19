package com.github.luomingxu.idea.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "Idea.Enhance", storages = [Storage("setting.xml")])
class IdeaEnhanceState : PersistentStateComponent<IdeaEnhanceState> {
    var `is` = false
    override fun getState(): IdeaEnhanceState {
        return this
    }

    override fun loadState(ideaEnhanceState: IdeaEnhanceState) {
        XmlSerializerUtil.copyBean(ideaEnhanceState, this)
    }
}