package sample;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    private TextField text_do_wyslania;

    @FXML
    private TextField bity_do_przeklamania;

    @FXML
    private Button button_sent_text;

    @FXML
    private Button encode_pairity;

    @FXML
    private Button check3;

    @FXML
    private ComboBox<String> comboBox_metoda;

    @FXML
    private TextField input_data1;

    @FXML
    private TextArea input_data3;

    @FXML
    private TextArea coded_data3;

    @FXML
    private TextArea bad_data3;

    @FXML
    private TextField output3;

    @FXML
    private TextField coded_par;

    @FXML
    void initialize() {

        Pairity Pair = new Pairity();
        CrcCalc Crc = new CrcCalc();

        button_sent_text.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = text_do_wyslania.getText();
                String bin_data = charToBinary(text);
                //input_data1.setText(bin_data);
                input_data3.setText(bin_data);
                String code, bad;
                switch(comboBox_metoda.getValue()){
                    case "CRC12":
                        code = Crc.CRC12_(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,1);
                        break;
                    case "CRC16":
                        code = Crc.CRC16_(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,2);
                        break;
                    case "CRC16 REVERSE":
                        code = Crc.CRC16_reversed(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,3);
                        break;
                    case "CRC32":
                        code = Crc.CRC32_(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,4);
                        break;
                    case "SDLC":
                        code = Crc.SDLC_(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,5);
                        break;
                    case "SDLC REVERSE":
                        code = Crc.SDLC_reverse(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,6);
                        break;
                    case "CRC-ITU":
                        code = Crc.CRC_ITU(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,7);
                        break;
                    case "AMT":
                        code = Crc.CRC_ATM(binaryToChar(bin_data).getBytes());
                        coded_data3.setText(code+bin_data);
                        bad = zakloc(coded_data3.getText(), bity_do_przeklamania.getText());
                        bad_data3.setText(bad);
                        sprawdz(bad,8);
                        break;
                }

            }
        });

        encode_pairity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            String input = input_data1.getText();
            coded_par.setText(Pair.encodeParity(input));
            }
        });

        check3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch(comboBox_metoda.getValue()){
                    case "CRC12":
                        sprawdz(bad_data3.getText(),1);
                        break;
                    case "CRC16":
                        sprawdz(bad_data3.getText(),2);
                        break;
                    case "CRC16 REVERSE":
                        sprawdz(bad_data3.getText(),3);
                        break;
                    case "CRC32":
                        sprawdz(bad_data3.getText(),4);
                        break;
                    case "SDLC":
                        sprawdz(bad_data3.getText(),5);
                        break;
                    case "SDLC REVERSE":
                        sprawdz(bad_data3.getText(),6);
                        break;
                    case "CRC-ITU":
                        sprawdz(bad_data3.getText(),7);
                        break;
                    case "AMT":
                        sprawdz(bad_data3.getText(),8);
                        break;
                };
            }
        });



        comboBox_metoda.getItems().addAll("CRC12", "CRC16", "CRC16 REVERSE", "CRC32",
                "SDLC", "SDLC REVERSE", "CRC-ITU", "AMT");
        comboBox_metoda.getSelectionModel().selectFirst();;
    }

    public static String charToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();
    }

    public String binaryToChar(String binary) {
        String str = "";

        for (int i = 0; i < binary.length()/8; i++) {

            int a = Integer.parseInt(binary.substring(8*i,(i+1)*8),2);
            str += (char)(a);
        }

        return str;
    }

    public String zakloc(String input, String bits){
        char[] dane = input.toCharArray();
        if(bits.length()>0){
            int bity = Integer.parseInt(bits);
            for(int i=0;i<bity;i++){
                Random rand = new Random();
                int number = rand.nextInt(dane.length);
                if(dane[number]=='1'){
                    dane[number]='0';
                }else if (dane[number]=='0'){
                    dane[number]='1';
                }
            }
        }
        return String.valueOf(dane);
    }

    public void sprawdz(String input_str, int type){
        CrcCalc Crc = new CrcCalc();
        int n  = input_str.length();
        String code, new_code, data;
        switch (type){
            case 1:
                code = input_str.substring(0, 12);
                System.out.println(code);
                data = input_str.substring(12,n);
                new_code = Crc.CRC12_(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
            case 2:
                code = input_str.substring(0, 16);
                System.out.println(code);
                data = input_str.substring(16,n);
                new_code = Crc.CRC16_(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                    else output3.setText("Dane zostaly zaklocone!");
                break;
            case 3:
                code = input_str.substring(0, 16);
                System.out.println(code);
                data = input_str.substring(16,n);
                new_code = Crc.CRC16_reversed(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
            case 4:
                code = input_str.substring(0, 32);
                System.out.println(code);
                data = input_str.substring(32,n);
                new_code = Crc.CRC32_(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
            case 5:
                code = input_str.substring(0, 16);
                System.out.println(code);
                data = input_str.substring(16,n);
                new_code = Crc.SDLC_(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
            case 6:
                code = input_str.substring(0, 16);
                System.out.println(code);
                data = input_str.substring(16,n);
                new_code = Crc.SDLC_reverse(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
            case 7:
                code = input_str.substring(0, 16);
                System.out.println(code);
                data = input_str.substring(16,n);
                new_code = Crc.CRC_ITU(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
            case 8:
                code = input_str.substring(0, 8);
                System.out.println(code);
                data = input_str.substring(8,n);
                new_code = Crc.CRC_ATM(binaryToChar(data).getBytes());
                System.out.println(new_code);
                System.out.println(code.equals(new_code));
                if (code.equals(new_code)) output3.setText("Dane są poprawne");
                else output3.setText("Dane zostaly zaklocone!");
                break;
        }

        //output3.setText("Dane zostaly zaklocone!");

        //output3.setText("Dane poprawne");
    }

}



