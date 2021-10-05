package org.imzdong.tool.pxml;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 * @author admin
 * @since 2021/10/5 上午11:22
 */
public class DomParseXml {

    public static void main(String[] args) throws Exception{
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        System.out.println(ClassLoader.getSystemResource(""));

        URL resource = DomParseXml.class.getClassLoader().getResource("");
        String path = resource.getPath();
        System.out.println(path);
        String xmlPath = path + "pxml/" + "data.xml";
        //dom4j(xmlPath);
        orgDomParseXml(xmlPath);
    }

    private static void dom4j(String xmlPath) throws DocumentException {
        SAXReader reader = new SAXReader();
        org.dom4j.Document read = reader.read(xmlPath);
        Element rootElement = read.getRootElement();
        for (Iterator<Element> it = rootElement.elementIterator("order"); it.hasNext();) {
            Element order = it.next();
            for (Iterator<Element> subOrder = order.elementIterator(); subOrder.hasNext();) {
                Element next = subOrder.next();
                if(next.getNodeType() == org.dom4j.Node.ELEMENT_NODE){
                    System.out.println(next.getName()+"-----"+next.getText());
                }
            }
            System.out.println("------");
        }
    }

    private static void orgDomParseXml(String xmlPath) throws ParserConfigurationException, SAXException, IOException {
        //获取解析器工厂
        DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
        //获取解析器
        DocumentBuilder builder= factory.newDocumentBuilder();
        //解析文档
        Document parse = builder.parse(xmlPath);
        NodeList childNodes = parse.getElementsByTagName("order");
        System.out.println(childNodes.getLength());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            NodeList subChild = item.getChildNodes();
            for (int j = 0; j < subChild.getLength(); j++) {
                Node subNode = subChild.item(j);
                if (subNode.getNodeType() == Node.ELEMENT_NODE){
                    System.out.println(subNode.getNodeName()+"---"+subNode.getTextContent());
                }
            }
            System.out.println("---------");
        }
    }
}
