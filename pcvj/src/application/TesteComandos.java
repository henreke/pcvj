package application;
import java.io.IOException;

import comunicacaoJava.ComunicacaoTCP;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import processo.Tanque;

public class TesteComandos {

	@FXML
	private TextField temp1;

	@FXML
	private Rectangle tq1;

	private MainApp mainApp;

	public TesteComandos() {}

	@FXML
	private void initialize() {}


	public void setMainApp(MainApp mainApp) {

		this.mainApp = mainApp;
	}

	@FXML
	private void enviarPergunta() {
		ComunicacaoTCP comunicacao = new ComunicacaoTCP("192.168.25.117", 1188);
		try {
			temp1.setText(comunicacao.getUpdate(1, ""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void encherTq(){
		
		Tanque.encher(tq1,tq1.getHeight()+10);
	}

}
