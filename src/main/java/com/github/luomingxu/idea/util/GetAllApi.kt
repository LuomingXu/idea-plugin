package com.github.luomingxu.idea.util

import com.github.luomingxu.idea.entity.HttpMethod
import com.github.luomingxu.idea.entity.RESTNavigationItem
import com.github.luomingxu.idea.entity.SpringHttpMethodAnnotation
import com.intellij.codeInsight.AnnotationUtil
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.search.searches.AllClassesSearch
import kotlinx.coroutines.*
import org.codehaus.groovy.ast.tools.BeanUtils
import java.util.*
import java.util.concurrent.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis


object GetAllApi {

    private val log = logger<GetAllApi>()

    private val ALL_METHODS = arrayOf(
        HttpMethod.GET,
        HttpMethod.DELETE,
        HttpMethod.HEAD,
        HttpMethod.OPTIONS,
        HttpMethod.PATCH,
        HttpMethod.POST,
        HttpMethod.PUT,
        HttpMethod.TRACE
    )

    private val ALL_MAPPINGS = arrayOf(
        SpringHttpMethodAnnotation.GetMapping,
        SpringHttpMethodAnnotation.PostMapping,
        SpringHttpMethodAnnotation.PutMapping,
        SpringHttpMethodAnnotation.PatchMapping,
        SpringHttpMethodAnnotation.DeleteMapping
    )

    private val ALL_CONTROLLER = arrayOf(
        SpringHttpMethodAnnotation.Controller,
        SpringHttpMethodAnnotation.RestController,
        SpringHttpMethodAnnotation.FeignClient
    )

    fun getProjectAllApi(project: Project): MutableList<RESTNavigationItem> {
        val items: MutableList<RESTNavigationItem> = ArrayList()
        val modules = ModuleManager.getInstance(project).modules

        for (item: Module in modules) {
            val classes: Collection<PsiClass> = AllClassesSearch.search(item.getModuleScope(false), project).findAll()
            for (clazz: PsiClass in classes) {
                var isController = false
                for (controller in ALL_CONTROLLER) {
                    if (clazz.getAnnotation(controller.clazzFullName) != null) {
                        isController = true
                        break
                    }
                }
                if (!isController) {
                    continue
                }

                val requestMapping: PsiAnnotation? =
                    clazz.getAnnotation(SpringHttpMethodAnnotation.RequestMapping.clazzFullName)
                var baseUrls: MutableList<String> = ArrayList()
                var baseMethods: MutableList<HttpMethod> = ArrayList()
                if (requestMapping != null) {
                    baseUrls = getAllPath(requestMapping)
                    baseMethods = getAllMethod(requestMapping)
                }

                for (m: PsiMethod in clazz.allMethods) {
                    var existApi = false
                    val methods: MutableList<HttpMethod> = ArrayList()
                    val paths: MutableList<String> = ArrayList()
                    var annotation = m.getAnnotation(SpringHttpMethodAnnotation.RequestMapping.clazzFullName)
                    if (annotation != null) {
                        methods.addAll(getAllMethod(annotation))
                        paths.addAll(getAllPath(annotation))
                        existApi = true
                    }

                    for (mapping in ALL_MAPPINGS) {
                        annotation = m.getAnnotation(mapping.clazzFullName)
                        if (annotation != null) {
                            paths.addAll(getAllPath(annotation))
                            mapping.method?.let { methods.add(it) }
                            existApi = true
                        }
                    }

                    // gen apis
                    if (existApi) {
                        items.addAll(getAllRequest(baseMethods, baseUrls, methods, paths, m))
                    }
                }
            }
        }

        return items
    }

    private fun getAllPath(annotaion: PsiAnnotation): MutableList<String> {
        val allV: MutableList<PsiAnnotationMemberValue> = ArrayList()
        val paths: MutableList<String> = ArrayList()
        val v: PsiAnnotationMemberValue? = annotaion.findDeclaredAttributeValue("value")
        val p: PsiAnnotationMemberValue? = annotaion.findDeclaredAttributeValue("path")

        v?.let { allV.addAll(AnnotationUtil.arrayAttributeValues(v)) }
        p?.let { allV.addAll(AnnotationUtil.arrayAttributeValues(p)) }

        if (allV.isEmpty()) {
            paths.add("") // 直接继承class request mapping的path
        } else {
            for (item in allV) {
                var temp = item.text.removeSurrounding("\"")
                if (!temp.startsWith("/")) {
                    temp = "/$temp"
                }
                temp = temp.removeSuffix("/")

                paths.add(temp)
            }
        }

        return paths
    }

    private fun getAllMethod(annotaion: PsiAnnotation): MutableList<HttpMethod> {
        val allV: MutableList<PsiAnnotationMemberValue> = ArrayList()
        val methods: MutableList<HttpMethod> = ArrayList()
        val v: PsiAnnotationMemberValue? = annotaion.findDeclaredAttributeValue("method")

        v?.let { allV.addAll(AnnotationUtil.arrayAttributeValues(v)) }

        for (item in allV) {
            for (m in ALL_METHODS) {
                if (item.text.lowercase().contains(m.name.lowercase())) {
                    methods.add(m)
                }
            }
        }

        return methods
    }

    private fun getAllRequest(
        bastMethods: MutableList<HttpMethod>,
        baseUrls: MutableList<String>,
        methods: MutableList<HttpMethod>,
        urls: MutableList<String>,
        psiElement: PsiElement
    ): MutableList<RESTNavigationItem> {
        val items: MutableList<RESTNavigationItem> = ArrayList()

        if (baseUrls.isEmpty()) {
            baseUrls.add("")
        }

        if (bastMethods.isNotEmpty()) {
            for (baseUrl in baseUrls) {
                for (m in methods) {
                    if (bastMethods.contains(m)) {
                        for (u in urls) {
                            log.debug("m: ${m.name}, path: ${baseUrl + u}")
                            items.add(RESTNavigationItem(baseUrl + u, m, psiElement))
                        }
                    }
                }
            }
        } else {
            for (baseUrl in baseUrls) {
                for (m in methods) {
                    for (u in urls) {
                        log.debug("m: ${m.name}, path: ${baseUrl + u}")
                        items.add(RESTNavigationItem(baseUrl + u, m, psiElement))
                    }
                }
            }
        }

        return items
    }
}