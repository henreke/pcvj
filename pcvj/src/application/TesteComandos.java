package application;
import java.io.IOException;

import comunicacaoJava.ComunicacaoTCP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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

	@FXML
	private void abrirRampa1(){

		try {

//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("EditRampa.fxml"));
//			AnchorPane EditRampaview = (AnchorPane) loader.load();
//
//			//rootLayout.setCenter(testecomandosview);
//
//			EditRampa editrampa = loader.getController();
//			//testecomandos.setMainApp(this);


			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditRampa.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Velocidade X");
            stage.setScene(new Scene(root1));
            stage.show();


			// D� ao controlador acesso � the main app.
			EditRampa controller = fxmlLoader.getController();
			//controller.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
