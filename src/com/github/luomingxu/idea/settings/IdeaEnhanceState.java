package com.github.luomingxu.idea.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@com.intellij.openapi.components.State(
        name = "Idea.Enhance",
        storages = {@Storage("setting.xml")})
public class IdeaEnhanceState implements PersistentStateComponent<IdeaEnhanceState> {
    public Boolean is = false;

    @Override
    public @Nullable
    IdeaEnhanceState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull IdeaEnhanceState ideaEnhanceState) {
        XmlSerializerUtil.copyBean(ideaEnhanceState, this);
    }
}
