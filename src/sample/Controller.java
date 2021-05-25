package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Pairity;

public class Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField text_do_wyslania;

    @FXML
    private TextField bity_do_przeklamania;

    @FXML
    private Button button_sent_text;

    @FXML
    private Button encode_pairity;

    @FXML
    private Button crc_koduj;

    @FXML
    private Tab tab_controla;

    @FXML
    private Tab tab_hamming;

    @FXML
    private Tab tab_crc;

    @FXML
    private ComboBox<String> comboBox_metoda;

    @FXML
    private TextField input_data1;

    @FXML
    private TextField input_data3;

    @FXML
    private TextField coded_par;

    @FXML
    private TextField coded_crc;

    @FXML
    void initialize() {

        Pairity Pair = new Pairity();
        CrcCalc Crc = new CrcCalc();

        button_sent_text.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = text_do_wyslania.getText();
                String bin_data = convertStringToBinary(text);
                input_data1.setText(bin_data);
                input_data3.setText(bin_data);
            }
        });

        encode_pairity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            String input = input_data1.getText();
            coded_par.setText(Pair.encodeParity(input));
            }
        });

        crc_koduj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String input;
                input = text_do_wyslania.getText();
                byte[] bytes = input.getBytes();

                switch(comboBox_metoda.getValue()){
                    case "CRC12":

                        break;
                    case "CRC16":
                        coded_crc.setText(Crc.CRC16_USB(bytes)); //poprawnie wylicza sumę kontrolną
                        break;
                    case "CRC16 REVERSE":


                        break;
                    case "CRC32":

                        break;
                    case "SDLC":

                        break;
                    case "SDLC REVERSE":

                        break;
                    case "CRC-ITU":

                        break;
                    case "AMT":

                        break;
                }

            }
        });

        comboBox_metoda.getItems().addAll("CRC12", "CRC16", "CRC16 REVERSE", "CRC32",
                "SDLC", "SDLC REVERSE", "CRC-ITU", "AMT");
        comboBox_metoda.getSelectionModel().selectFirst();;
    }

    public static String convertStringToBinary(String input) {

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
}



