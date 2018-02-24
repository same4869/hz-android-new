package com.renren.wawa.socket;

public interface RoomStatus {
    public static final int CONNECT = 0X0000;
    public static final int PING = 0x0001;
    public static final int NOTICE = 0x0002;
    public static final int ACK = 0x0003;
    public static final int JOIN = 0x0004;
    public static final int LEFT = 0x0005;
    public static final int GOODJOB = 0x0006;
    public static final int ROOMSTATUS = 0x0007;
    public static final int ROOMENDLESS = 0X0008;

}
