package com.renren.wawa.socket;


import com.renren.wawa.config.Constants;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.StringUtil;

import java.io.IOException;
import java.net.Socket;

import room.protobuf.Wawa;

public class RoomSocketClient {

    private int retryTimes = 0;
    private Thread sServerThread;
    private Socket sServerSocket = null;
    private RoomStreamHandler roomStreamHandler = null;
    private long uid;
    private String token;
    private ReceiveGameMessage receiveGameMessage;
    private String ip;
    private int port;
    private String nickName = "";
    private String avatar = "";
    private boolean isAutoConnect = true;


    public RoomSocketClient(String ip, String port, long uid, String nickName, String avatar, String token, final ReceiveGameMessage receiveGameMessage) {
        this.uid = uid;
        this.token = token;
        this.receiveGameMessage = receiveGameMessage;
        this.ip = ip;
        this.port = Integer.valueOf(port);
        this.nickName = nickName;
        if (StringUtil.isNotBlank(avatar)) {
            this.avatar = avatar;
        }

        roomStreamHandler = new RoomStreamHandler(new RoomStreamHandler.RWListener() {
            @Override
            public void onRoomFail() {
                BBLog.e(Constants.TAG, "RoomStreamHandler onDisConnect");
                if (isAutoConnect) {
                    onDisconnect();
                    if (retryTimes > 5) {
                        return;
                    }
                    retryTimes++;
                    connectServer();
                    BBLog.e(Constants.TAG, "onRoomFail");
                }

            }
        });
    }

    /**
     * 连接服务
     */
    public void connectServer() {
        if (sServerThread == null || !sServerThread.isAlive()) {
            sServerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sServerSocket = new Socket(ip, port);
                        sServerSocket.setKeepAlive(true);
                        roomStreamHandler.updateStream(sServerSocket.getInputStream(), sServerSocket.getOutputStream());
                        sendAuthMsg();
                        isAutoConnect = true;
                        readServerSocket();
                        retryTimes = 0;
                    } catch (IOException e) {
                        receiveGameMessage.roomSocketError();
                        BBLog.e(Constants.TAG, "connectServer room server socket io:" + e.getMessage());
                    }
                    BBLog.e(Constants.TAG, "connectServer onDisconnect");

                    onDisconnect();
                }
            };
            sServerThread.start();
        }
    }

    /**
     * 接收服务器数据
     */
    private void readServerSocket() {
        while (roomStreamHandler.readMsg()) {
            receiveGameMessage.receiveGameMessage(roomStreamHandler.getReadData());
        }
    }


    /**
     * 断开连接   释放
     */
    public synchronized void onDisconnect() {
        isAutoConnect = false;
        // close
        try {
            if (sServerSocket != null && sServerSocket.getInputStream() != null) {
                sServerSocket.getInputStream().close();
            }
            if (sServerSocket != null && sServerSocket.getOutputStream() != null) {
                sServerSocket.getOutputStream().close();
            }
            if (sServerSocket != null) {
                sServerSocket.close();
            }
        } catch (IOException e) {
            BBLog.e(Constants.TAG, "room socket close int io " + e.toString());
        }
        sServerThread = null;
        sServerSocket = null;
        roomStreamHandler.updateStream(null, null);
    }


    /**
     * 发送认证消息
     */
    private void sendAuthMsg() {
        Wawa.Connect auth = Wawa.Connect.newBuilder().setUid(uid).setToken(token).build();
        Wawa.Protocol protocol = Wawa.Protocol.newBuilder().setConnect(auth).build();
        roomStreamHandler.sendMsg(protocol.toByteArray(), RoomStatus.CONNECT);
        BBLog.e(Constants.TAG,"sendAuthMsg");
    }

    /**
     * 发送心跳
     */
    public void sendPingMsg() {
        Wawa.Ping ping = Wawa.Ping.newBuilder().setUid(uid).build();
        Wawa.Protocol protocol = Wawa.Protocol.newBuilder().setPing(ping).build();
        roomStreamHandler.sendMsg(protocol.toByteArray(), RoomStatus.PING);
    }

    /**
     * 发送进入房间消息
     *
     * @param roomId
     */
    public void sendJoinRoomMsg(long roomId) {
        Wawa.Join userJoin = Wawa.Join.newBuilder()
                .setRoomId(roomId).setUid(uid).setUname(nickName).setUimg(avatar).build();
        Wawa.Protocol protocol = Wawa.Protocol.newBuilder().setJoin(userJoin).build();
        roomStreamHandler.sendMsg(protocol.toByteArray(), RoomStatus.JOIN);

    }


    /**
     * 发送离开房间消息
     *
     * @param roomId
     */
    public void sendLeftRoomMsg(long roomId) {
        Wawa.Left userLeft = Wawa.Left.newBuilder()
                .setRoomId(roomId).setUid(uid).setUname(nickName).setUimg(avatar).build();
        Wawa.Protocol protocol = Wawa.Protocol.newBuilder().setLeft(userLeft).build();
        roomStreamHandler.sendMsg(protocol.toByteArray(), RoomStatus.LEFT);

    }


    /**
     * 接收消息接口
     */
    public interface ReceiveGameMessage {
        void receiveGameMessage(byte[] data);

        void roomSocketError();
    }


}
