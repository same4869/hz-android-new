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
class GameStreamHandler {

    private static final String TAG = GameStreamHandler.class.getSimpleName();

    private static final int MSG_SEND_TCP = 10001;
    private static final int MSG_SEND_BEAT = 10002;

    private static int HEAD_SIZE = 8;
    private static int RECEIVE_HEAD_SIZE = 6;

    private byte[] mData;


    public interface RWListener {

        // 暂时用不到
//        void onWriteSuccess(String msg);
//
//        void onWriteFail(String msg);

        void onBeatMsgWriteFail();
    }


    private HandlerThread mWriteThread = null;
    private Handler mHandler;
    private RWListener mRWListenr = null;

    private DataInputStream mIs;
    private OutputStream mOs;

    private byte[] mHeadBuffer;
    private byte[] mReadBuffer;
    private String mReadResult;
    private int mCommandType;

    public GameStreamHandler(RWListener listener) {
        mHeadBuffer = new byte[HEAD_SIZE];
        mReadBuffer = new byte[1024 * 1024];
        mRWListenr = listener;

        mWriteThread = new HandlerThread("servserstream");
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

    public byte[] getReadData(){
        return mData;
    }


    public String getReadResult() {
        return mReadResult;
    }

    public int getCommandType() {
        return mCommandType;
    }

    public boolean readMsg() {
        try {
            mIs.readFully(mReadBuffer, 0, RECEIVE_HEAD_SIZE);

            mCommandType = PbUtil.getCommandType(mReadBuffer);

            int length = PbUtil.byteArrayToIntNew(mReadBuffer, 0);

            //BBLog.e(Constants.TAG, "length " + length );


            mIs.readFully(mReadBuffer, RECEIVE_HEAD_SIZE , length);

            byte[] data = new byte[length];
            System.arraycopy(mReadBuffer, RECEIVE_HEAD_SIZE , data, 0, length);

            mData = data;

            String msg = new String(mReadBuffer, RECEIVE_HEAD_SIZE , length);
           // BBLog.e(Constants.TAG, "msg " + msg);
            mReadResult = msg;
            return true;
        } catch (IOException e) {
            BBLog.e(Constants.TAG, "server socket read io"+ e.toString());
        }
        return false;
    }


    private boolean writeOneMessage(byte[] data, int serverType) {
        boolean ret = writeOneMessage(data, 0, data.length, serverType);

        return ret;
    }


    private boolean writeOneMessage(byte[] buffer, int bufferStart, int bufferSize, int serverType) {
        if (serverType == -1) {
            return true;
        }
        PbUtil.intToByteArray(bufferSize, mHeadBuffer, 0, serverType);
        try {
            if (mOs != null) {
                mOs.write(mHeadBuffer);
                if (buffer != null) {
                    //BBLog.e(Constants.TAG, "buffer --> " + new String(buffer));


                    mOs.write(buffer, bufferStart, bufferSize);
                }
                return true;
            }
        } catch (Exception e) {
            BBLog.e(Constants.TAG, "server write error" + e.toString());
            return false;
        }
        return false;
    }

}
