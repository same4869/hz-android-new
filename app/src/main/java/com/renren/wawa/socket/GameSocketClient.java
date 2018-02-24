package com.renren.wawa.socket;

import com.renren.wawa.config.Constants;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.PbUtil;

import java.io.IOException;
import java.net.Socket;

import game.protobuf.Game;


public class GameSocketClient {

    private Thread sServerThread;
    private Socket sServerSocket = null;
    private GameStreamHandler sServerRWHandler = null;
    private long uid;
    private String token;
    private int roomId;
    private ReceiveGameMessage receiveGameMessage;
    private String ip;
    private int port;

    public GameSocketClient(String ip, String port, long uid, String token, String roomId , ReceiveGameMessage receiveGameMessage) {
        this.uid = uid;
        this.token = token;
        this.roomId = Integer.valueOf(roomId);
        this.receiveGameMessage = receiveGameMessage;
        this.ip = ip;
        this.port = Integer.valueOf(port);

        sServerRWHandler = new GameStreamHandler(new GameStreamHandler.RWListener() {
            @Override
            public void onBeatMsgWriteFail() {
                // 重连
                BBLog.e(Constants.TAG,"onBeatMsgWriteFail");
//                onDisconnect();
//                connect();
//                LogUtils.e(TAG, "onBeatMsgWriteFail reconnect");
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
                        sServerRWHandler.updateStream(sServerSocket.getInputStream(), sServerSocket.getOutputStream());
                        sendAuthMsg();
                        readServerSocket();
                    } catch (IOException e) {
                        e.printStackTrace();
                        BBLog.e(Constants.TAG, "server socket io:" + e.getMessage());
                    }

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
        while (sServerRWHandler.readMsg()) {
            String msg = sServerRWHandler.getReadResult();
            int cmdType = sServerRWHandler.getCommandType();
            BBLog.e(Constants.TAG," cmdType "+cmdType+" msg "+msg);

            receiveGameMessage.receiveGameMessage(cmdType,sServerRWHandler.getReadData());
        }
    }


    /**
     * 断开连接   释放
     */
    public synchronized void onDisconnect() {
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
            BBLog.e(Constants.TAG, "server socket close int io "+e.toString());
        }
        sServerThread = null;
        sServerSocket = null;
        sServerRWHandler.updateStream(null, null);
    }


    /**
     * 发送认证消息
     */
    private void sendAuthMsg(){
        BBLog.e(Constants.TAG,"uid "+uid);
        Game.Connect auth = Game.Connect.newBuilder().setUid(uid).setToken(token).setRoomId(roomId).build();
        sServerRWHandler.sendMsg(auth.toByteArray(), PbUtil.AUTH);
    }


    /**
     * 停止游戏
     */
    public void sendStopGame(){
        Game.Stop stop = Game.Stop.newBuilder().setUid(uid).build();
        sServerRWHandler.sendMsg(stop.toByteArray(), PbUtil.STOP);
    }

    /**
     * 游戏控制
     * @param cmd
     */
    public void sendGameControl(int cmd){
        Game.Operation control = Game.Operation.newBuilder().setUid(uid).setCmd(cmd).build();
        sServerRWHandler.sendMsg(control.toByteArray(), PbUtil.CONTROL);
    }


    /**
     * 继续上机
     */
    public void sendNextCmd(){
        Game.Another next = Game.Another.newBuilder().setUid(uid).setToken(token).setRoomId(roomId).build();
        sServerRWHandler.sendMsg(next.toByteArray(), PbUtil.NEXT);
    }


    /**
     * 接收消息接口
     */
    public interface ReceiveGameMessage{
         void receiveGameMessage(int cmdType, byte[] data);
    }


}
