package com.renren.wawa.utils;

public class PbUtil {
    public static final int AUTH = 0X0000;
    public static final int PING = 0x0001;
    public static final int PONG = 0x0002;
    public static final int RESPONSE = 0x0003;
    public static final int QUIT = 0x0004;
    public static final int STOP = 0x0005; //app抓取结果通知娃娃机
    public static final int CONTROL = 0x0006;
    public static final int STATE = 0x0007;//娃娃机通知app
    public static final int NEXT = 0X0008;

    /**
     * 后台新架构中根据前4位来判断命令类型
     *
     * @param header
     * @return
     */
    public static int getCommandType(byte[] header) {
        switch (header[3]) {
            case 0:
                return AUTH;
            case 1:
                return PING;
            case 2:
                return PONG;
            case 3:
                return RESPONSE;
            case 4:
                return QUIT;
            case 5:
                return STOP;
            case 6:
                return CONTROL;
            case 7:
                return STATE;
            case 8:
                return NEXT;
        }
        return 0;
    }

    /**
     * @param length
     * @param buffer
     * @param start      起始位置
     * @param serverType
     */
    public static void intToByteArray(int length, byte[] buffer, int start, int serverType) {
        buffer[start] = (byte) 0;
        buffer[start + 1] = (byte) 0;
        buffer[start + 2] = (byte) 0;
        if (serverType == AUTH) {
            buffer[start + 3] = (byte) 0;
        } else if (serverType == PING) {
            buffer[start + 3] = (byte) 1;
        } else if (serverType == PONG) {
            buffer[start + 3] = (byte) 2;
        } else if (serverType == RESPONSE) {
            buffer[start + 3] = (byte) 3;
        } else if (serverType == QUIT) {
            buffer[start + 3] = (byte) 4;
        } else if (serverType == STOP) {
            buffer[start + 3] = (byte) 5;
        } else if (serverType == CONTROL) {
            buffer[start + 3] = (byte) 6;
        } else if (serverType == STATE) {
            buffer[start + 3] = (byte) 7;
        } else if (serverType == NEXT) {
            buffer[start + 3] = (byte) 8;
        }
        buffer[start + 4] = (byte) ((length >> 24) & 0xFF);
        buffer[start + 5] = (byte) ((length >> 16) & 0xFF);
        buffer[start + 6] = (byte) ((length >> 8) & 0xFF);
        buffer[start + 7] = (byte) (length & 0xFF);

    }



    /**
     * @param length
     * @param buffer
     * @param start      起始位置
     */
    public static void intToByteArray(int length, byte[] buffer, int start) {
        buffer[start + 0] = (byte) ((length >> 24) & 0xFF);
        buffer[start + 1] = (byte) ((length >> 16) & 0xFF);
        buffer[start + 2] = (byte) ((length >> 8) & 0xFF);
        buffer[start + 3] = (byte) (length & 0xFF);

    }

    /**
     * 数据 编码  pb 格式
     *
     * @param data
     * @param head
     * @return
     */
    public static byte[] encode(byte[] data, byte[] head) {
        int length = data.length;
        byte[] ret = new byte[length + head.length];
        System.arraycopy(head, 0, ret, 0, head.length);
        System.arraycopy(data, 0, ret, head.length, length);
        return ret;
    }


    //   / * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
//            * @param src byte[] data
// * @return hex string
// */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    public static int byteArrayToIntNew(byte[] b, int start) {
        return b[start + 5] & 0xFF |
                (b[start + 4] & 0xFF) << 8;

    }


    public static int byteArrayToInt(byte[] b, int start) {
        return b[start + 7] & 0xFF |
                (b[start + 6] & 0xFF) << 8 |
                (b[start + 5] & 0xFF) << 16 |
                (b[start + 4] & 0xFF) << 24;

    }


    // TODO: 2017/9/15   好多要合并

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;

    }
}
