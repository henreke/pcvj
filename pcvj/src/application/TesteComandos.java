package application;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import comunicacaoJava.ComunicacaoTCP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import processo.Tanque;
import processo.Valvulas;

public class TesteComandos {

	@FXML
	private TextField temp1;

	@FXML
	private Rectangle tq1;

	@FXML
	private TextField statusAquecimento;
	
	@FXML
	private TextField volumeTQ1;
	
	@FXML
	private TextField temperaturaTQ1;
	
	//Declaracao das valvulas
	//1
	@FXML
	private Rectangle atuadorV1;
	@FXML
	private Polygon corpoV11;
	@FXML
	private Polygon corpoV12;
	@FXML
	private Line linhaV1;

	private MainApp mainApp;

	public TesteComandos() {}
	
	

	@FXML
	private void initialize() {

		tanque1 = new Tanque(1,1,0,1);
		valvulas = new Valvulas();
		adicionarValvulas();
		timerUpdate = new Timer();
		timerUpdate.scheduleAtFixedRate(new Relogio(), 0, 1000);
	}

	public Tanque tanque1;
	public Valvulas valvulas;
	Timer timer, timerUpdate;
	int teste = 0;
	public void setMainApp(MainApp mainApp) {

		this.mainApp = mainApp;
	}

	@FXML
	private void enviarPergunta() {
		ComunicacaoTCP comunicacao = new ComunicacaoTCP("192.168.25.177", 1188);
		try {
			temp1.setText(comunicacao.getUpdate(1, ""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void encherTq(){

		tanque1.encher(tq1,11);
		
	}

	@FXML
	private void abrirRampa1(){

		try {


			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditRampa.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Velocidade X");
            stage.setScene(new Scene(root1));
            stage.show();


			// Dï¿½ ao controlador acesso ï¿½ the main app.
			EditRampa controller = fxmlLoader.getController();
			controller.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void startAquecimentoTQ1(){
		if (tanque1.startRampaAquecimento()) {

			timer = new Timer();
			timer.scheduleAtFixedRate(new RelogioRampa2(), 0, 500);
		}
		else
			statusAquecimento.setText("Rampa de Aquecimento já foi executada");

	}
	
	@FXML
	private void testeStatusValvula()
	{
		valvulas.updateStatus();
		if (valvulas.getStatusValvula(0) == Valvulas.ABRINDO)
			System.out.println("abrindp");
	}

	private void adicionarValvulas() {
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV1, corpoV11, corpoV12, linhaV1);
	}
	class RelogioRampa2 extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub
			String status = "Aguardando....";
			if (tanque1.Aquecendo())
				 status = "Aquecendo em "+tanque1.getTemperaturaAquecimentoAtual()+", "+tanque1.getTempoAquecimentoAtual()+"s de "+" "+tanque1.getTempoAquecimentoTotal()+"s ";
			else
				timer.cancel();

			statusAquecimento.setText(status);

		}


	}
	
	class Relogio extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub			
			volumeTQ1.setText(String.valueOf(tanque1.getLevel()));
			temperaturaTQ1.setText(String.valueOf(tanque1.getTemperatura()));

		}


	}

}
