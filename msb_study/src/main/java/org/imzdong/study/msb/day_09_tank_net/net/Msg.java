package org.imzdong.study.msb.day_09_tank_net.net;

public abstract class Msg {

    public abstract byte[] toBytes();

    public abstract MsgType getMsgType();

    public abstract void handle();

    public abstract Msg parse(byte[] bytes);

}
