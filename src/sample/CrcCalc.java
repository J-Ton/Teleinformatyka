package sample;

public class CrcCalc {

    public String CRC16_USB(byte[] source) {
        int wCRCin = 0xFFFF;
        // Integer.reverse(0x8005) >>> 16
        int wCPoly = 0xA001;
        for (int i = 0; i < source.length; i++) {
            wCRCin ^= ((int) source[i] & 0x00FF);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        int res = wCRCin ^= 0xFFFF;
        return Integer.toHexString(res);
    }
}


