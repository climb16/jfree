package com.jfree.web.listener;

import com.jfree.ioc.Ioc;
import com.jfree.ioc.IocFactory;
import com.jfree.util.StringUtil;
import com.jfree.web.ServletContextResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * jfree listener
 */
public class ContextLoaderListener extends ServletContextResource implements ServletContextListener {

    Logger logger = LogManager.getLogger(ContextLoaderListener.class);

    Ioc ioc = IocFactory.getIoc();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (ioc != null) {
            logger.info("jfree ioc init start...");
            try{
                servletPath = servletContextEvent.getServletContext().getRealPath("/");
                logger.debug("jfree ioc load servletPath \t\t " + servletPath);
                String jfreeConfig = servletContextEvent.getServletContext().getInitParameter("jfree-config");
                if (!StringUtil.isEmpty(jfreeConfig)) this.jfreeConfig = jfreeConfig;
                ioc.init();
                logger.info("jfree ioc init success...");
            }catch (Exception e){
                logger.error("jfree ioc init failed...", e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try{
            ioc.destroyed();
            logger.info("jfree ioc destroyed success...");
        }catch (Exception e){
            logger.error("jfree ioc destroyed failed...", e);
        }

    }
}