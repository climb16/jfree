package com.jfree.ioc;

import com.jfree.core.Resource;
import com.jfree.core.config.DefaultJfreeConfigReader;
import com.jfree.core.config.JfreeConfigReader;
import com.jfree.util.FileUtil;
import com.jfree.web.ServletContextResource;

import java.io.File;

/** 默认的xml Ioc实现类
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/6/28 17:36
 */
public class DefaultXmlIoc extends AbstractIoc {

    @Override
    void setReader() {
        String jfreeConfig = Resource.getJfreeConfig();
        logger.debug("ioc init jfreeConfig " + jfreeConfig);
        if(jfreeConfig.startsWith("classpath:")) jfreeConfig = FileUtil.getFileURL(jfreeConfig.substring(10));
        else jfreeConfig = ServletContextResource.getServletPath() + jfreeConfig.substring(10);
        reader = new DefaultJfreeConfigReader(new File(jfreeConfig));
    }
}