package sample;

import java.lang.reflect.Array;

public class Pairity {


    /* koduje dane surowe metodą 'kontrola parzystości' */
    public String encodeParity(String input) {
        char[] data = input.toCharArray();
        int bits_number = data.length;
        int bytes_number = bits_number/8;
        char[] coded_data=null;

        if(bits_number!=0) {
            coded_data = new char[bits_number + 1];
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
        int bytes_number = bits_number / 8;
        char[] data = null;
        if (bits_number != 0) {
            data = new char[bits_number + 1];
            int ileJedynek;
            int errors = 0;
            for (int i = 0; i < bytes_number; i++) {
                ileJedynek = 0;
                for (int j = 0; j < 8; j++) {
                    data[i * 8 + j] = coded_data[i * 9 + j + 1];
                    ileJedynek += coded_data[i * 9 + j + 1];
                }
                ileJedynek += coded_data[i * 9];    // bit parzystości
                if (ileJedynek % 2 == 0) {    // nie wykryto błędów
                } else {    // wystąpiły błędy
                    errors++;
                }
            }
        }
        return null;
    }

    /*function fixParity() {
        var n = coded_data.length;
        initTypes(); // zainicjuj tablicę rodzajów bitów
        var bytes = n/9;
        errors = 0;
        var ileJedynek;

        for (var i = 0; i < bytes; i++) {
            ileJedynek = 0;

            for(var j = 0; j < 8; j++) {
                ileJedynek += coded_data[i*9+j+1];  // policz "1"
            }
            ileJedynek+=coded_data[i*9];	// bit parzystości
            if (ileJedynek%2===0)	// nie wykryto błędów
            {
                type[i*9]=3;				// zaznacz poprawny bit kontrolny
                for (var j=1; j<9; j++) type[i*9+j]=0;	// zaznacz poprawne bity danych
            }
            else	// wystąpiły błędy
            {
                errors++;
                type[i*9]=5;				// zaznacz niepewny bit kontrolny
                for (var j=1; j<9; j++) type[i*9+j]=2;	// zaznacz niepewne bity danych
            }
        }
    }*/

}
