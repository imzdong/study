package org.imzdong.tool.pxml;

import org.w3c.dom.Node;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author admin
 * @since 2021/10/5 下午4:38
 */
public class Xml2Model {

    private static final String ymd = "yyyy-MM-dd";
    private static final SimpleDateFormat  sdf = new SimpleDateFormat(ymd);

    public static void xml2Model(Node node, Order order){

        /**
         * orderid---ORDER9240ea05-911e-423e-9083-68c176e9e56c
         * productid---P017
         * name---外交官TC6012
         * category---箱包
         * price---599.0
         * salenum---317
         * saledate---2019-01-25
         */

        String nodeName = node.getNodeName();
        String nodeText = node.getTextContent().trim();

        switch (nodeName){
            case "orderid":
                order.setOrderId(nodeText);
                break;
            case "productid":
                order.setProductId(nodeText);
                break;
            case "name":
                order.setName(nodeText);
                break;
            case "category":
                order.setCategory(nodeText);
                break;
            case "price":
                order.setPrice(new BigDecimal(nodeText));
                break;
            case "salenum":
                order.setSaleNum(Integer.parseInt(nodeText));
                break;
            case "saledate":
                try {
                    order.setSaleDate(sdf.parse(nodeText));
                } catch (ParseException e) {
                    System.out.println("error");
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }


    }
}
