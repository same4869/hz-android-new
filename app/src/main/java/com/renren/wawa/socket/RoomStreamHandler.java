package com.renren.wawa.socket;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.renren.wawa.config.Constants;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.PbUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 读写服务器tcp 消息
 */
class RoomStreamHandler {

    private static final String TAG = RoomStreamHandler.class.getSimpleName();

    private static final int MSG_SEND_TCP = 10001;
    private static final int MSG_SEND_BEAT = 10002;

    private static int HEAD_SIZE = 6;

    public interface RWListener {
        void onRoomFail();
    }


    private HandlerThread mWriteThread = null;
    private Handler mHandler;
    private RWListener mRWListenr = null;

    private DataInputStream mIs;
    private OutputStream mOs;

    private byte[] mHeadBuffer;
    private byte[] mReadBuffer;
    private byte[] mData;
    private String mReadResult;
    private int mCommandType;


    public RoomStreamHandler(RWListener listener) {
        mHeadBuffer = new byte[HEAD_SIZE];
        mReadBuffer = new byte[1024 * 1024];
        mRWListenr = listener;

        mWriteThread = new HandlerThread("roomServserStream");
        mWriteThread.start();
        mHandler = new Handler(mWriteThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                int type = msg.arg1;
                if (what == MSG_SEND_TCP) {
                    writeOneMessage((byte[]) msg.obj, type);
                }
            }
        };
    }

    public void updateStream(InputStream is, OutputStream os) {
        mIs = new DataInputStream(is);
        mOs = os;
    }

    public void sendMsg(byte[] msg, int serverType) {
        Message message = Message.obtain();
        message.what = MSG_SEND_TCP;
        message.arg1 = serverType;
        message.obj = msg;
        mHandler.sendMessage(message);
    }

    public byte[] getReadData() {
        return mData;
    }


    public boolean readMsg() {
        try {
            byte[] readBuff = new byte[1024];
            int resultLen = -1;
            if (mIs == null) {
                mRWListenr.onRoomFail();
                return false;
            }
            if (mIs != null && (resultLen = mIs.read(readBuff)) >= 0) {
                byte[] response = new byte[resultLen - 4 - 2];
                System.arraycopy(readBuff, 4 + 2, response, 0, resultLen - 4 - 2);
                mData = response;
            }
            return true;
        } catch (Exception e) {
            mRWListenr.onRoomFail();
            BBLog.e(Constants.TAG, "room socket read io " + e.toString());
        }
        return false;
    }


    private boolean writeOneMessage(byte[] data, int serverType) {
        boolean ret = writeOneMessage(data, 0, data.length);
        return ret;
    }


    /**
     * int 转4字节数组
     *
     * @param value
     * @return
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }


    private boolean writeOneMessage(byte[] buffer, int bufferStart, int bufferSize) {


        // pb消息体
        byte[] msgBytes = buffer;

        int totalMsgLen = msgBytes.length + 4 + 2;
        byte[] lengthBytes = intToBytes(msgBytes.length);
        byte[] totalMsg = new byte[totalMsgLen]; // 最终发送的消息
        System.arraycopy(lengthBytes, 0, totalMsg, 0, 4);
        System.arraycopy(msgBytes, 0, totalMsg, 4 + 2, msgBytes.length);


        PbUtil.intToByteArray(bufferSize, mHeadBuffer, 0);
        try {
            if (mOs != null) {
                mOs.write(totalMsg);
                return true;
            }
        } catch (Exception e) {
            BBLog.e(Constants.TAG, "room write error" + e.toString());
            mRWListenr.onRoomFail();
            return false;
        }
        return false;
    }

}
