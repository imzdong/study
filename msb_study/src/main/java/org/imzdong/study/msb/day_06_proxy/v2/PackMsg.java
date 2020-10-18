package org.imzdong.study.msb.day_06_proxy.v2;

import java.io.Serializable;

public class PackMsg implements Serializable {

    private MyHeader myHeader;
    private MyContent myContent;

    public PackMsg(MyHeader myHeader, MyContent myContent) {
        this.myHeader = myHeader;
        this.myContent = myContent;
    }

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public void setMyHeader(MyHeader myHeader) {
        this.myHeader = myHeader;
    }

    public MyContent getMyContent() {
        return myContent;
    }

    public void setMyContent(MyContent myContent) {
        this.myContent = myContent;
    }

    @Override
    public String toString() {
        return "PackMsg{" +
                "myHeader=" + myHeader +
                ", myContent=" + myContent +
                '}';
    }
}
