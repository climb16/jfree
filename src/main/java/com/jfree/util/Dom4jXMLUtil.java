package com.jfree.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * XML dom4j解析xml
 * Created by xiao on 2015/9/13.
 * @version 1.0.0
 */
public class Dom4jXMLUtil {

    //xml dom模型
    private Document document;

    /**
     * 构造函数 根据xmlName加载Dom
     * @param xml
     */
    public Dom4jXMLUtil(File xml){
        SAXReader saxReader = new SAXReader();
        try{
            document = saxReader.read(xml);
        }catch(DocumentException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取文档根节点
     * @return
     */
    public Element getRoot(){
        return document.getRootElement();
    }

    /**
     * 获取指定子节点
     * @param element
     * @param name
     * @return
     */
    public Element getChildElement(Element element, String name){
        return element.element(name);
    }

    /**
     * 获取指定子节点 list
     * @param element
     * @param name
     * @return
     */
    public List<Element> getChildElements(Element element, String name){
        return element.elements(name);
    }
    /**
     * 获取节点名字
     * @param element
     * @return
     */
    public String getElementName(Element element){
        return element.getName();
    }

    /**
     * 获取节点文本
     * @param element
     * @return
     */
    public String getElementText(Element element){
        return element.getTextTrim();
    }

    /**
     * 获取该节点的子节点
     * @return 子节点map
     */
    public Map<String, String> getChildElementMap(Element element){
        Map<String,String> elemMap = new HashMap<>();
        Iterator<Element> elements = element.elementIterator();
        while(elements.hasNext()){
            Element child = elements.next();
            elemMap.put(child.getName(), child.getText());
        }
        return elemMap;
    }

    /**
     * 获取节点 指定名字的属性节点
     * @param element
     * @param attrName
     * @return
     */
    public Attribute getAttribute(Element element, String attrName){
        return element.attribute(attrName);
    }
    /**
     * 获取节点属性值
     * @param element
     * @param attrName
     * @return
     */
    public String getAttrValue(Element element, String attrName){
        return getAttribute(element, attrName).getValue();
    }
    /**
     * 获取该节点的属性
     * @return 节点属性map
     */
    public Map<String, String> getAttribute(Element element){
        Map<String,String> attrMap = new HashMap<>();
        Iterator<Attribute> attrs = element.attributeIterator();
        while(attrs.hasNext()){
            Attribute attr = attrs.next();
            attrMap.put(attr.getName(), attr.getValue());
        }
        return  attrMap;
    }
}
