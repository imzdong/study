package org.imzdong.study.msb.day_09_tank_net.net;

import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Group;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TankMsg extends Msg{

    private int x,y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private UUID uuid;

    public TankMsg(){}

    public TankMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
        this.uuid = tank.getUuid();
    }

    public TankMsg(int x, int y, Dir dir, boolean moving, Group group, UUID uuid) {
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
            dot.writeLong(uuid.getLeastSignificantBits());
            dot.writeLong(uuid.getMostSignificantBits());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bot.toByteArray();
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
