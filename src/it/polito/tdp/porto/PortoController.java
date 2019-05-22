package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	private Model model= new Model();
	

   
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    

    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	Author autore=boxPrimo.getValue();
    	List<Author> coautori= new ArrayList<Author>(model.cercaCoautori(autore));
    	boxSecondo.getItems().addAll(model.cercaNONcoautori(autore));
    	txtResult.appendText("I coautori di "+autore.toString()+ "sono : "+coautori.toString()+"\n");
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	Author autore1=boxPrimo.getValue();
    	Author autore2=boxSecondo.getValue();
    	List<Paper> articoli = new ArrayList<Paper>(model.trovaCamminoMinimo(autore1, autore2));
    	txtResult.appendText("Gli articoli che legano i due autori selezionati sono: "+articoli.toString()+"\n");

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        model.createGraph();
    	boxPrimo.getItems().addAll(this.model.getAuthor());
    }
    public void setModel() {
    	this.model=model;
    }
}
