package org.imzdong.study.msb.day_09_tank_net.net;

import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Group;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class TankJoinMsg extends Msg{

    private int x,y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private UUID uuid;

    public TankJoinMsg(){}

    public TankJoinMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
        this.uuid = tank.getUuid();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID uuid) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
            dot.writeInt(group.ordinal());
            //dos.writeLong(id.getMostSignificantBits());
            //			dos.writeLong(id.getLeastSignificantBits());
            dot.writeLong(uuid.getMostSignificantBits());
            dot.writeLong(uuid.getLeastSignificantBits());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bot.toByteArray();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TANK_JOIN;
    }

    @Override
    public void handle() {
        System.out.println("client channelRead..."+ this);
        String msgUuid = this.getUuid().toString();
        TankFrame instance = TankFrame.getInstance();
        Tank myTank = instance.getTank();
        String myUuid = myTank.getUuid().toString();
        if(Objects.equals(myUuid, msgUuid) || instance.enemyList.containsKey(msgUuid)){
            return;
        }
        //不是自己都发一遍，但是不添加自己
        Client.getInstance().getChannel().writeAndFlush(new TankJoinMsg(myTank));
        //如果未添加就添加
        Tank tank = new Tank(this.getX(), this.getY(), this.getDir(), instance, this.getGroup(), this.getUuid());
        instance.enemyList.put(tank.getUuid().toString(), tank);
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
            group = Group.values()[dit.readInt()];
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
                ", group=" + group +
                ", uuid=" + uuid +
                '}';
    }
}
