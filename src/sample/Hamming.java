package sample;

public class Hamming {

    int[] errors_bit = new int[100];

    public static String encodeHamming(String input) {
        char[] a = input.toCharArray();
        int bits_number = a.length;
        int n = bits_number / 4;

        String out = "";
        for (int i = 0; i < n; i++) {
            String b = input.substring(i * 4, (i + 1) * 4);
            char[] bb = b.toCharArray();
            out = out + String.valueOf(generateCode(bb));
        }
        return out;
    }


    static char[] generateCode(char a[]) {
        char b[];

        // We find the number of parity bits required:
        int i = 0, parity_count = 0, j = 0, k = 0;
        while (i < a.length) {
            // 2^(parity bits) must equal the current position
            // Current position is (number of bits traversed + number of parity bits + 1).
            // +1 is needed since array indices start from 0 whereas we need to start from 1.
            if (Math.pow(2, parity_count) == i + parity_count + 1) {
                parity_count++;
            } else {
                i++;
            }
        }
        // Length of 'b' is length of original data (a) + number of parity bits.
        b = new char[a.length + parity_count];

        // Initialize this array with '2' to indicate an 'unset' value in parity bit locations:

        for (i = 1; i <= b.length; i++) {
            if (Math.pow(2, j) == i) {
                // Found a parity bit location.
                // Adjusting with (-1) to account for array indices starting from 0 instead of 1.

                b[i - 1] = '2';
                j++;
            } else {
                b[k + j] = a[k++];
            }
        }
        for (i = 0; i < parity_count; i++) {
            // Setting even parity bits at parity bit locations:

            b[((int) Math.pow(2, i)) - 1] = (char) (getParity(b, i) + 48);
        }
        return b;
    }

    static int getParity(char b[], int power) {
        int parity = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] != '2') {

                // If 'i' doesn't contain an unset value,
                // We will save that index value in k, increase it by 1,
                // Then we convert it into binary:

                int k = i + 1;
                String s = Integer.toBinaryString(k);

                //Nw if the bit at the 2^(power) location of the binary value of index is 1
                //Then we need to check the value stored at that location.
                //Checking if that value is 1 or 0, we will calculate the parity value.

                int x = ((Integer.parseInt(s)) / ((int) Math.pow(10, power))) % 10;
                if (x == 1) {
                    if (b[i] == '1') {
                        parity = (parity + 1) % 2;
                    }
                }
            }
        }
        return parity;
    }

    public String receive(String input) {
        // This is the receiver code. It receives a Hamming code in array 'a'.
        // We also require the number of parity bits added to the original data.
        // Now it must detect the error and correct it, if any.
        char[] a = input.toCharArray();
        int bits_number = a.length;
        int n = bits_number / 7;
        String error_location = "";
        for (int i = 0; i < n; i++) {
            String b = input.substring(i * 7, (i + 1) * 7);
            char[] bb = b.toCharArray();
            int parity_count = 3;
            int power;
            // We shall use the value stored in 'power' to find the correct bits to check for parity.

            int parity[] = new int[parity_count];
            // 'parity' array will store the values of the parity checks.

            String syndrome = new String();
            // 'syndrome' string will be used to store the integer value of error location.

            for (power = 0; power < parity_count; power++) {
                // We need to check the parities, the same no of times as the no of parity bits added.

                for (int j = 0; j < bb.length; j++) {
                    // Extracting the bit from 2^(power):

                    int k = j + 1;
                    String s = Integer.toBinaryString(k);
                    int bit = ((Integer.parseInt(s)) / ((int) Math.pow(10, power))) % 10;
                    if (bit == 1) {
                        if (bb[j] == '1') {
                            parity[power] = (parity[power] + 1) % 2;
                        }
                    }
                }
                syndrome = parity[power] + syndrome;
            }
            // This gives us the parity check equation values.
            // Using these values, we will now check if there is a single bit error and then correct it.
            errors_bit[i] = Integer.parseInt(syndrome, 2);
            if (Integer.parseInt(syndrome, 2) != 0) {
                error_location = error_location + String.valueOf(Integer.parseInt(syndrome, 2) + i * 7) + ", ";
            }


        }
        if (!error_location.equals("")) {
            return "Blędny " + error_location + " bit";
        } else {
            return ("Nie wykryto blędów");
        }
    }

    public String decode(String input) {
        char[] a = input.toCharArray();
        int power = 2;
        int bits_number = a.length;
        int n = bits_number / 7;
        String bits = "";
        for (int i = 0; i < n; i++) {
            String b = input.substring(i * 7, (i + 1) * 7);
            char[] bb = b.toCharArray();
            if(errors_bit[i]!=0){
                if(bb[errors_bit[i]-1]=='1'){
                    bb[errors_bit[i]-1]='0';
                }else{
                    bb[errors_bit[i]-1]='1';
                }
            }
            bits = bits + bb[2] + bb[4] + bb[5] + bb[6];
        }
        return bits;
    }

}


