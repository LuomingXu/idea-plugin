package com.github.luomingxu.idea.action.setting;

import com.github.luomingxu.idea.settings.IdeaEnhanceState;
import com.github.luomingxu.idea.util.NotifyUtil;

public class FullDirSetting extends SettingAction {

    @Override
    public void set(IdeaEnhanceState state) {
        state.is = !state.is;
        NotifyUtil.info(String.format("Current setting is: %s", state.is));
    }
}
