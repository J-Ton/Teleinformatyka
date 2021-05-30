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

    public String decodePair (String input){
        String str = "";
        int n = input.length();
        int bytes = n/9;
        for (int i = 0; i < bytes; i++) {

            int a = Integer.parseInt(input.substring(9*i+1,(i+1)*9),2);
            str += (char)(a);
        }

        return str;
    }

    /* kodowanie metodą Hamminga */
    String encodeHamming(String random_data) { // parametr - tablica INT
        int bits_number = random_data.length();
        int i = 0, redundancy = 0, sum = 0;

        while(i < bits_number) {
            if(Math.pow(2,redundancy) - 1 == sum)
                redundancy++;
            else
                i++;
            sum++;
        }

        Character coded_data []= new Character[sum];
        Integer type []= new Integer[sum];

        int mask = 0;
        redundancy = 0;
        int d = 0;
        i = 0;
        sum = 0;

        while (i < bits_number) {
            if (Math.pow(2,redundancy) - 1 == sum)
                redundancy++;
            else {
                coded_data[sum]=random_data.charAt(i);
                if (random_data.charAt(i)==1)
                    mask ^= sum+1;
                i++;
            }
            sum++;
        }

        redundancy = 0;
        for (i = 0; i < sum; i++) {
            if (Math.pow(2,redundancy) - 1 == i) {
                if ((mask & (1 << redundancy))==0)
                    coded_data[i]=0;
                else
                    coded_data[i]=1;
                redundancy++;
            }
        }
        String data="";
        for(i=0; i<coded_data.length; i++){
            data +=coded_data[i];
        }

        return data;
    }

}
