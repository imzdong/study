package org.imzdong.study.msb.desgin.factory.method;

import org.imzdong.study.msb.desgin.factory.model.Product;

/**
 * 工厂方法：产品的纵向扩展，各种产品扩展
 */
public interface MethodFactory {

    Product create();
}
