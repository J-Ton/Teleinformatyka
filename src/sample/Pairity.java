package sample;

import java.lang.reflect.Array;

public class Pairity {

    public String encodeParity(String input) {
        char[] data = input.toCharArray();
        int bits_number = data.length;
        int bytes_number = bits_number/8;
        char[] coded_data=null;

        if(bits_number!=0) {
            coded_data = new char[bits_number + bytes_number];
            int ileJedynek;

            for (int i = 0; i < bytes_number; i++) {
                ileJedynek = 0;
                for (int j = 0; j < 8; j++) {
                    coded_data[i * 9 + j + 1] = data[i * 8 + j]; // przepisz dane
                    if (data[i * 8 + j] == '1') {
                        ileJedynek++;// zliczaj jedynki
                    }
                }
                if (ileJedynek % 2 == 1)  coded_data[i * 9] = '1';    // zapisz bit parzystości
                else coded_data[i * 9] = '0';
            }
            return String.valueOf(coded_data);
        }
        return null;
    }

    public String decodeParity(String input) {
        char[] coded_data = input.toCharArray();
        int bits_number = coded_data.length;
        int bytes_number = bits_number / 9;
        if (bits_number != 0) {
            int ileJedynek;
            int errors = 0;
            for (int i = 0; i < bytes_number; i++) {
                ileJedynek = 0;
                for (int j = 0; j < 9; j++) {
                    int n = i * 9 + j;
                    ileJedynek += coded_data[n];
                }
                if (ileJedynek % 2 == 0) {    // nie wykryto błędów
                } else {    // wystąpiły błędy
                    errors++;
                }
            }
            if(errors == 0)
                return "Nie wykryto blędów";
            else return "Przeklamano " + String.valueOf(errors) + " bajtów";
        }
        return"";
    }

}
