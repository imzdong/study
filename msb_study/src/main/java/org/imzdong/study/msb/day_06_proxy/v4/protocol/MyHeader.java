package org.imzdong.study.msb.day_06_proxy.v4.protocol;

import java.io.Serializable;

public class MyHeader implements Serializable {
    int flag;
    int dateLength;
    long requestId;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getDateLength() {
        return dateLength;
    }

    public void setDateLength(int dateLength) {
        this.dateLength = dateLength;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "MyHeader{" +
                "flag=" + flag +
                ", dateLength=" + dateLength +
                ", requestId=" + requestId +
                '}';
    }
}
