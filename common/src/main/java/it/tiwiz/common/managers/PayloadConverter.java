package it.tiwiz.common.managers;


public class PayloadConverter {

    private static final int SIZE =  4;

    public static byte[] intToByteArray(int value){
        byte[] converted = new byte[SIZE];

        int shift = 0;

        for (int i = 0; i < SIZE; i++) {
            shift = i * 8;
            converted[i] = (byte) (value >>> shift);
        }

        return converted;
    }

    public static int byteArrayToInt(byte[] value){

        if (value.length != SIZE) {
            return 0;
        }

        int result = 0;
        int shift = 0;

        for (int i = 0; i < SIZE; i++) {
            shift = i * 8;
            result = (value[i] & 0x000000FF) << shift | result;
        }

        return result;
    }
}
