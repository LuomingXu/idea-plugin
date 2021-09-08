package com.github.luomingxu.idea.util

import com.github.luomingxu.idea.entity.HttpMethod
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object RESTFullIcons {

    @JvmField
    val GET = IconLoader.getIcon("/icons/GET.svg", javaClass)

    @JvmField
    val HEAD = IconLoader.getIcon("/icons/HEAD.svg", javaClass)

    @JvmField
    val POST = IconLoader.getIcon("/icons/POST.svg", javaClass)

    @JvmField
    val PUT = IconLoader.getIcon("/icons/PUT.svg", javaClass)

    @JvmField
    val PATCH = IconLoader.getIcon("/icons/PATCH.svg", javaClass)

    @JvmField
    val DELETE = IconLoader.getIcon("/icons/DELETE.svg", javaClass)

    @JvmField
    val OPTIONS = IconLoader.getIcon("/icons/OPTIONS.svg", javaClass)

    @JvmField
    val TRACE = IconLoader.getIcon("/icons/TRACE.svg", javaClass)

    fun getIcon(httpMethod: HttpMethod): Icon {
        return when (httpMethod) {
            HttpMethod.GET -> GET
            HttpMethod.HEAD -> HEAD
            HttpMethod.POST -> POST
            HttpMethod.PUT -> PUT
            HttpMethod.PATCH -> PATCH
            HttpMethod.DELETE -> DELETE
            HttpMethod.OPTIONS -> OPTIONS
            HttpMethod.TRACE -> TRACE
        }
    }
}