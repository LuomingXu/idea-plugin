package com.github.luomingxu.idea.entity

import com.github.luomingxu.idea.util.RESTFullIcons
import com.intellij.navigation.ItemPresentation
import javax.swing.Icon

class RESTItemPresentation(
    private val apiPath: String,
    private val httpMethod: HttpMethod,
    private val locationString: String
) : ItemPresentation {
    override fun getPresentableText(): String {
        return apiPath
    }

    override fun getIcon(unused: Boolean): Icon {
        return RESTFullIcons.getIcon(httpMethod)
    }

    /**
     * 辅助字段
     */
    override fun getLocationString(): String {
        return locationString
    }
}