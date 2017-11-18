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
import processo.Temperaturas;
import processo.Valvulas;
import processo.Vazoes;

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
	//0
	@FXML
	private Rectangle atuadorV0;
	@FXML
	private Polygon corpoV01;
	@FXML
	private Polygon corpoV02;
	@FXML
	private Line linhaV0;

	//1
	@FXML
	private Rectangle atuadorV1;
	@FXML
	private Polygon corpoV11;
	@FXML
	private Polygon corpoV12;
	@FXML
	private Line linhaV1;

	//2
	@FXML
	private Rectangle atuadorV2;
	@FXML
	private Polygon corpoV21;
	@FXML
	private Polygon corpoV22;
	@FXML
	private Line linhaV2;

	//0
	@FXML
	private Rectangle atuadorV3;
	@FXML
	private Polygon corpoV31;
	@FXML
	private Polygon corpoV32;
	@FXML
	private Line linhaV3;

	//4
	@FXML
	private Rectangle atuadorV4;
	@FXML
	private Polygon corpoV41;
	@FXML
	private Polygon corpoV42;
	@FXML
	private Line linhaV4;

	//5
	@FXML
	private Rectangle atuadorV5;
	@FXML
	private Polygon corpoV51;
	@FXML
	private Polygon corpoV52;
	@FXML
	private Line linhaV5;

	//6
	@FXML
	private Rectangle atuadorV6;
	@FXML
	private Polygon corpoV61;
	@FXML
	private Polygon corpoV62;
	@FXML
	private Line linhaV6;

	//7
	@FXML
	private Rectangle atuadorV7;
	@FXML
	private Polygon corpoV71;
	@FXML
	private Polygon corpoV72;
	@FXML
	private Line linhaV7;
	//8
	@FXML
	private Rectangle atuadorV8;
	@FXML
	private Polygon corpoV81;
	@FXML
	private Polygon corpoV82;
	@FXML
	private Line linhaV8;

	//Declarao Medidores Vazao
	@FXML
	private TextField medidorvazao1;

	@FXML
	private TextField medidorvazao2;
	@FXML
	private TextField medidorvazao3;
	@FXML
	private TextField medidorvazao4;

	private MainApp mainApp;

	public TesteComandos() {}



	@FXML
	private void initialize() {


		valvulas = new Valvulas();
		adicionarValvulas();
		vazoes = new Vazoes();
		temperaturas = new Temperaturas(4);

		tanque1 = new Tanque(vazoes.getVazao(0),vazoes.getVazao(1),temperaturas.getTemperatura(0),0,1,1);

		timerUpdate = new Timer();
		timerUpdate.scheduleAtFixedRate(new Relogio(), 2000, 1000);


	}

	public Tanque tanque1;
	public Valvulas valvulas;
	public Vazoes vazoes;
	public Temperaturas temperaturas;
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
		valvulas.addValvula(0, Valvulas.SOLENOIDE, atuadorV0, corpoV01, corpoV02, linhaV0);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV1, corpoV11, corpoV12, linhaV1);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV2, corpoV21, corpoV22, linhaV2);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV3, corpoV31, corpoV32, linhaV3);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV4, corpoV41, corpoV42, linhaV4);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV5, corpoV51, corpoV52, linhaV5);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV6, corpoV61, corpoV62, linhaV6);
		valvulas.addValvula(0, Valvulas.MOTORIZADA, atuadorV7, corpoV71, corpoV72, linhaV7);
		valvulas.addValvula(0, Valvulas.SOLENOIDE, atuadorV8, corpoV81, corpoV82, linhaV8);
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
			valvulas.updateStatus();
			vazoes.updateVazoes();
			temperaturas.updateTemperaturas();
			volumeTQ1.setText(String.valueOf(tanque1.getLevel()));
			temperaturaTQ1.setText(String.valueOf(tanque1.getTemperatura()));
			medidorvazao1.setText(String.valueOf(vazoes.getVazao(0).getInstantaneo())+"l/m");
			medidorvazao2.setText(String.valueOf(vazoes.getVazao(1).getInstantaneo())+"l/m");
			medidorvazao3.setText(String.valueOf(vazoes.getVazao(2).getInstantaneo())+"l/m");
			//medidorvazao4.setText(String.valueOf(vazoes.getVazao(3))+"l/m");
			//System.out.println(String.valueOf(vazoes.getVazao(0))+"l/m");

		}


	}

}
