package com.jfree.web;

import com.jfree.core.Resource;

/**
 * web 资源类
 */
public class ServletContextResource extends Resource {

    protected static String servletPath;

    public static String getServletPath() {
        return servletPath;
    }
}