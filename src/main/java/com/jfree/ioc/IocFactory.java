package com.jfree.ioc;

/**
 * ioc factory
 */
public class IocFactory {

    private static Ioc ioc;

    public synchronized static Ioc getIoc(){
        if (ioc == null) ioc = new DefaultXmlIoc();
        return ioc;
    }

}