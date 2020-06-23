package it.polito.tdp.food;

import java.net.URL;
import javafx.scene.control.TextArea;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Condimento;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<?> boxIngrediente;

    @FXML
    private Button btnDietaEquilibrata;
    
    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaDieta(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
	    	Double calorie = Double.valueOf(this.txtCalorie.getText());
	    	
	    	this.model.creaGrafo(calorie);
	    	this.txtResult.appendText(String.format("Grafo creato!\n#vertici: %d\n#archi: %d\n", model.nVertici(), model.nArchi()));
	    	
	    	this.txtResult.appendText("\nIngredienti: \n");
	    	
	    	for(Condimento c : model.listaVertici()) {
	    		this.txtResult.appendText("Condimento: "+c.getCodice()+" -- calorie: "+c.getCalorie()+" -- cibi:  "+model.nCibi(c)+"\n");
	    	}
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserisci valore corretto");
    	}

    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
	}
}
