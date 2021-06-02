/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String s = this.boxPorzioni.getValue();
    	if(s == null) {
    		txtResult.appendText("ERRORE: selezionare un tipo di porzione");
    		return;
    	}
    	Map<String,Integer> result = model.getCorrelate(s);
    	if(result.isEmpty()) {
    		txtResult.appendText("NON VI SONO PORZIONI CORRELATE A " + s);
    	}else {
    		txtResult.appendText("PORZIONI CORRELATE A " + s + ":\n");
    		for(String str: result.keySet()) {
    			txtResult.appendText("\n" + str + " - " + result.get(str));
    		}
    	}    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	if(!isValidGrafo()) {
    		return;
    	}
    	double cal = Double.parseDouble(txtCalorie.getText());
    	model.creaGrafo(cal);
    	this.boxPorzioni.getItems().setAll(model.getVertici());
    	
    	txtResult.appendText("GRAFO CREATO:\n");
    	txtResult.appendText("#vertci: " + model.getVertici().size());
    	txtResult.appendText("\n#archi: " + model.getEdgeSize());
    	
    	this.boxPorzioni.setDisable(false);
    	this.btnCorrelate.setDisable(false);
    	this.txtPassi.setDisable(false);
    	this.btnCammino.setDisable(false);
    }

    private boolean isValidGrafo() {
		String cal = this.txtCalorie.getText();
		if(cal.equals("")) {
			txtResult.appendText("ERRORE: inserire un valore per le calorie");
			return false;
		}else{
			try {
				double num = Double.parseDouble(cal);
				if(num <= 0) {
					txtResult.appendText("ERRORE: calorie devono essere un numero maggiore di 0");
					return false;
				}
			}catch(NumberFormatException nfe) {
				txtResult.appendText("ERRORE: calorie devono essere un numero.");
				return false;
			}
		}
		return true;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
