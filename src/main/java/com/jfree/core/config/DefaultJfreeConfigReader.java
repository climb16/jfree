package com.jfree.core.config;

import com.jfree.ioc.holder.Bean;
import com.jfree.ioc.holder.BeanHolder;
import com.jfree.util.Dom4jXMLUtil;
import com.jfree.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.io.File;
import java.util.*;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/6/24 16:11
 */
public class DefaultJfreeConfigReader  implements JfreeConfigReader, BeanHolder {

    Logger logger = LogManager.getLogger(DefaultJfreeConfigReader.class);

    private static final String JFREE = "jfree", BEANS = "beans", BEAN = "bean";
    private static final String ID = "id", CLASS = "class", INIT = "init", DESTROYED = "destroyed";
    private static final String PRORERTY = "property", NAME = "name";
    private static final String MAP = "map", LIST = "list", PROPS = "props", SET="set", KEY = "key", VALUE = "value", ENTITY = "entity", PROP = "prop", ARRAY = "array";

    private Dom4jXMLUtil xml;


    /**
     * 构造方法 获取bean类， 并实例化
     */
    public DefaultJfreeConfigReader(File config){
        xml = new Dom4jXMLUtil(config);
    }


    @Override
    public void init(){
        Element root = xml.getRoot();
        logger.debug(root.getName());
        beans(root);
    }

    /**
     * dom4j jfree-config reade
     * 目前只支持基本属性的读取注入
     * @param root
     */
    private void beans(Element root){
        Element bean = root.element(BEANS);
        List<Element> beans = bean.elements(BEAN);
        for (Element b : beans){
            String beanId;
            String beanClass = b.attributeValue(CLASS);
            Attribute idAtt = b.attribute(ID);
            if (idAtt == null || StringUtil.isEmpty(idAtt.getValue()))
                beanId = StringUtil.toStartLowerCase(beanClass.substring(beanClass.lastIndexOf(".") + 1));
            else beanId = idAtt.getValue();
            logger.debug("bean: " + beanId + "\t beanClass: " + beanClass);
            List<Element> properties = b.elements(PRORERTY);
            Map<String, Object> propMap = new HashMap<>();
            for (Element p : properties){
                String name = p.attribute(NAME).getValue();
                logger.debug("bean: " + beanId + "\t property: " + name);
                if (StringUtil.isEmpty(name)) logger.error("bean: " + beanId + " the property name is empty");
                else {
                    Attribute value = p.attribute(VALUE);
                    if (value != null) {
                        logger.debug("bean: " + beanId + "\t property: " + name + "\t value: " + value);
                        propMap.put(name, value.getValue());
                    } else {
                        Iterator<Element> it = p.elementIterator();
                        while (it.hasNext()) {
                            Element e = it.next();
                            String eName = e.getName();
                            logger.debug("bean: " + beanId + "\t property: " + name + "properties: " + eName);
                            switch (eName) {
                                case VALUE:
                                    propMap.put(name, e.getText());
                                    break ;
                                case MAP:
                                    Map<String, Object> enties = new HashMap<>();
                                    List<Element> es = e.elements(ENTITY);
                                    for (Element entity : es ){
                                        Attribute key = entity.attribute(KEY);
                                        if (key == null) logger.error("bean: " + beanId + "\t property " + name + "entity key is empty");
                                        else {
                                            if (entity.attribute(VALUE) != null) enties.put(key.getName(), entity.attribute(VALUE).getValue());
                                            else if (!StringUtil.isEmpty(entity.getText())) enties.put(key.getName(), entity.getText());
                                            else if(entity.element(VALUE) != null) enties.put(key.getName(), entity.element(VALUE).getText());
                                        }
                                    }
                                    propMap.put(name, enties);
                                    break ;
                                case LIST:
                                    List<Object> enties1ist = new ArrayList<>();
                                    es = e.elements(ARRAY);
                                    for (Element array : es ){
                                        if (array.attribute(VALUE) != null) enties1ist.add(array.attribute(VALUE).getValue());
                                        else  if (!StringUtil.isEmpty(array.getText())) enties1ist.add(array.getText());
                                        else if(array.element(VALUE) != null) enties1ist.add(array.element(VALUE).getText());
                                    }
                                    propMap.put(name, enties1ist);
                                    break ;
                                case PROPS:
                                    Properties entiesprop = new Properties();
                                    es = e.elements(PROP);
                                    for (Element prop : es ){
                                        Attribute key = prop.attribute(KEY);
                                        if (key == null) logger.error("bean: " + beanId + "\t property " + name + "entity key is empty");
                                        else{
                                            if (prop.attribute(VALUE) != null) entiesprop.setProperty(key.getValue(), prop.attribute(VALUE).getValue());
                                            else  if (!StringUtil.isEmpty(prop.getText())) entiesprop.setProperty(key.getValue(), prop.getText());
                                            else if(prop.element(VALUE) != null) entiesprop.setProperty(key.getValue(), prop.element(VALUE).getText());
                                        }
                                    }
                                    propMap.put(name, entiesprop);
                                    break ;
                                case SET:
                                    Set<Object> entiesSet = new HashSet<>();
                                    es = e.elements(ARRAY);
                                    for (Element array : es ){
                                        if (array.attribute(VALUE) != null) entiesSet.add(array.attribute(VALUE).getValue());
                                        else  if (!StringUtil.isEmpty(array.getText())) entiesSet.add(array.getText());
                                        else if(array.element(VALUE) != null) entiesSet.add(array.element(VALUE).getText());
                                    }
                                    propMap.put(name, entiesSet);
                                    break ;
                                case BEAN:
                                    Element bl = e.element(BEAN);
                                    if (bl != null && bl.attribute(CLASS) != null) propMap.put(name, bl.attribute(CLASS).getValue());
                                    break ;
                                default:
                            }

                        }
                    }
                }
            }
            Bean beanEnty = new Bean(beanId, beanClass, b.attributeValue(INIT), b.attributeValue(DESTROYED));
            beanEnty.setPropertise(propMap);
            beanEnties.add(beanEnty);
        }
    }
}