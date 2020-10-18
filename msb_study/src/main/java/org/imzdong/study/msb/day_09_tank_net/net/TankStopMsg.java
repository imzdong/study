package org.imzdong.study.msb.day_09_tank_net.net;

import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class TankStopMsg extends Msg{

    private int x,y;
    private Dir dir;
    private boolean moving;
    private UUID uuid;

    public TankStopMsg(){}

    public TankStopMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.isMoving();
        this.uuid = tank.getUuid();
    }

    public TankStopMsg(int x, int y, Dir dir, boolean moving, UUID uuid) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.uuid = uuid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream bot = new ByteArrayOutputStream();
        DataOutputStream dot = new DataOutputStream(bot);
        try {
            dot.writeInt(x);
            dot.writeInt(y);
            dot.writeInt(dir.ordinal());
            dot.writeBoolean(moving);
            dot.writeLong(uuid.getMostSignificantBits());
            dot.writeLong(uuid.getLeastSignificantBits());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bot.toByteArray();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_STOP;
    }

    @Override
    public void handle() {
        String msgUuid = this.getUuid().toString();
        TankFrame instance = TankFrame.getInstance();
        Tank myTank = instance.getTank();
        String myUuid = myTank.getUuid().toString();
        if(Objects.equals(myUuid, msgUuid)){
            return;
        }
        Tank tank = instance.enemyList.get(msgUuid);
        tank.setDir(this.dir);
        tank.setMoving(this.moving);
        tank.setX(this.x);
        tank.setY(this.y);
    }

    @Override
    public Msg parse(byte[] bytes) {
        ByteArrayInputStream bit = new ByteArrayInputStream(bytes);
        DataInputStream dit = new DataInputStream(bit);
        try {
            x = dit.readInt();
            y = dit.readInt();
            dir = Dir.values()[dit.readInt()];
            moving = dit.readBoolean();
            uuid = new UUID(dit.readLong(), dit.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", uuid=" + uuid +
                '}';
    }
}
