package application;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import comunicacaoJava.ComunicacaoSerial;
import comunicacaoJava.ComunicacaoTCP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import processo.Bomba;
import processo.Etapas;
import processo.Etapas.Etapa;
import processo.Resistencias;
import processo.Resistencias.Resistencia;
import processo.Tanque;
import processo.Temperaturas;
import processo.Valvulas;
import processo.Valvulas.Valvula;
import processo.Vazoes;

public class TesteComandos {

	@FXML
	private TextField tempo;



	@FXML
	private TextField statusAquecimento;

	@FXML
	private TextField volumeTQ1;

	@FXML
	private TextField temperaturaTQ1;

	@FXML
	private TextField temperaturaTQ2;

	@FXML
	private TextField temperaturaTQ3;

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


	//Resistencias
	@FXML
	private Line resistencia1;
	@FXML
	private Line resistencia2;
	@FXML
	private Line resistencia3;

	@FXML
	private TextField valorresistencia1;
	@FXML
	private TextField valorresistencia2;
	@FXML
	private TextField valorresistencia3;

	//Declarao Medidores Vazao
	@FXML
	private TextField medidorvazao1;

	@FXML
	private TextField medidorvazao2;
	@FXML
	private TextField medidorvazao3;
	@FXML
	private TextField medidorvazao4;

	//Bomba
	@FXML
	private Circle bombaCirculoExterno;
	@FXML
	private Circle bombaCirculoInterno;

	//Tanques
	@FXML
	private Rectangle tq1_externo;
	@FXML
	private Rectangle tq1_interno;
	
	@FXML
	private Rectangle tq2_externo;
	@FXML
	private Rectangle tq2_interno;
	
	@FXML
	private Rectangle tq3_externo;
	@FXML
	private Rectangle tq3_interno;

	private MainApp mainApp;

	public TesteComandos() {}



	@FXML
	private void initialize() {

		try {
			comunicacao.conectar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		valvulas = new Valvulas();
		valvulas.setComunicacao(comunicacao);
		adicionarValvulas();
		vazoes = new Vazoes();
		temperaturas = new Temperaturas(3);
		temperaturas.setComunicacao(comunicacao);
		resistencias = new Resistencias(3);
		resistencias.setComunicacao(comunicacao);
		bomba = new Bomba();
		bomba.setComunicacao(comunicacao);
		HLT = new Tanque(vazoes.getVazao(0),vazoes.getVazao(1),temperaturas.getTemperatura(0),0,1,1);
		HLT.setResistencia(resistencias.getResistencia(0));
		MLT = new Tanque(vazoes.getVazao(1),vazoes.getVazao(2),temperaturas.getTemperatura(1),1,2,2);
		MLT.setResistencia(resistencias.getResistencia(1));
		MLT.setPIdNumber(2);
		BK = new Tanque(vazoes.getVazao(3),vazoes.getVazao(2),temperaturas.getTemperatura(2),5,6,3);
		BK.setResistencia(resistencias.getResistencia(2));
		BK.setPIdNumber(3);
		timerUpdate = new Timer();
		timerUpdate.scheduleAtFixedRate(new RelogioUpdate(), 2000, 1000);

		etapas = new Etapas();


	}

	public Tanque HLT,MLT,BK;
	public Valvulas valvulas;
	public Vazoes vazoes;
	public Temperaturas temperaturas;
	public Resistencias resistencias;
	public Bomba bomba;
	Timer timer;
	public Timer timerUpdate;
	int teste = 0;
	public Etapas etapas;
	ComunicacaoSerial comunicacao = new ComunicacaoSerial(Util.Configuracoes.portaSerial);
	public void setMainApp(MainApp mainApp) {

		this.mainApp = mainApp;
	}

	@FXML
	private void enviarPergunta() {
		
	}

	@FXML
	private void encherTq(){

		HLT.encher(11);

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
		if (HLT.startRampaAquecimento()) {

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
			System.out.println("abrindo");
	}


	@FXML
	private void ligarResistencia1() {
		if (resistencias.getResistencia(0).getStatus() == Resistencia.LIGADA)
			resistencias.getResistencia(0).desligar();
		else
			resistencias.getResistencia(0).ligar();
	}

	@FXML
	private void ligarResistencia2() {
		if (resistencias.getResistencia(1).getStatus() == Resistencia.LIGADA)
			resistencias.getResistencia(1).desligar();
		else
			resistencias.getResistencia(1).ligar();
	}

	@FXML
	private void ligarResistencia3() {
		if (resistencias.getResistencia(2).getStatus() == Resistencia.LIGADA)
			resistencias.getResistencia(2).desligar();
		else
			resistencias.getResistencia(2).ligar();
	}
	@FXML
	private void ligarBomba() {
		if (bomba.getStatus() == Resistencia.LIGADA)
			bomba.desligar();
		else
			bomba.ligar();
	}

	private void manipularValvula(Valvula valvula){
		//System.out.println("Manipulando");
		if (valvula.isOpen())
			valvula.fechar();
		else
			valvula.abrir();
	}
	@FXML
	private void resetMedidor() {
		vazoes.getVazao(0).resetAcumulado();
	}

	@FXML
	private void abrirValvula0()
	{
		 manipularValvula(valvulas.getValvula(0));
	}

	@FXML
	private void abrirValvula1()
	{
		manipularValvula(valvulas.getValvula(1));

	}

	@FXML
	private void abrirValvula2()
	{
		manipularValvula(valvulas.getValvula(2));

	}

	@FXML
	private void abrirValvula3()
	{
		manipularValvula(valvulas.getValvula(3));

	}

	@FXML
	private void abrirValvula4()
	{
		manipularValvula(valvulas.getValvula(4));

	}

	@FXML
	private void abrirValvula5()
	{
		manipularValvula(valvulas.getValvula(5));

	}

	@FXML
	private void abrirValvula6()
	{
		manipularValvula(valvulas.getValvula(6));

	}

	@FXML
	private void abrirValvula7()
	{
		manipularValvula(valvulas.getValvula(7));

	}

	@FXML
	private void abrirValvula8()
	{
		manipularValvula(valvulas.getValvula(8));

	}

	@FXML
	private void inciarEtapa1(){
		etapas.iniciaEtapa1(HLT,20);

	}

	@FXML
	private void iniciarEtapa2() {
		etapas.iniciaEtapa2(HLT, 33);
	}
	
	@FXML
	private void iniciarEtapa3() {
		etapas.iniciaEtapa3(HLT, MLT, 20);
	}
	
	@FXML
	private void iniciarEtapa4() {
		//etapas.iniciaEtapa4(HLT, MLT, 20, valvulas.getValvula(1), valvulas.getValvula(2), bomba, vazoes.getVazao(2), 2);
		MLT.addRampaAquecimento(10000, 60);
		MLT.aquecer(MLT.getRampa(0));
	}
	private void adicionarValvulas() {
		valvulas.addValvula(0, Valvulas.SOLENOIDE, atuadorV0, corpoV01, corpoV02, linhaV0);
		valvulas.addValvula(1, Valvulas.MOTORIZADA, atuadorV1, corpoV11, corpoV12, linhaV1);
		valvulas.addValvula(2, Valvulas.MOTORIZADA, atuadorV2, corpoV21, corpoV22, linhaV2);
		valvulas.addValvula(3, Valvulas.MOTORIZADA, atuadorV3, corpoV31, corpoV32, linhaV3);
		valvulas.addValvula(4, Valvulas.MOTORIZADA, atuadorV4, corpoV41, corpoV42, linhaV4);
		valvulas.addValvula(5, Valvulas.MOTORIZADA, atuadorV5, corpoV51, corpoV52, linhaV5);
		valvulas.addValvula(6, Valvulas.MOTORIZADA, atuadorV6, corpoV61, corpoV62, linhaV6);
		valvulas.addValvula(7, Valvulas.MOTORIZADA, atuadorV7, corpoV71, corpoV72, linhaV7);
		valvulas.addValvula(8, Valvulas.SOLENOIDE, atuadorV8, corpoV81, corpoV82, linhaV8);
	}
	private void calcAlturaTanques(Rectangle externo, Rectangle interno, Tanque tanque) {
		interno.setHeight((externo.getHeight()/tanque.CapacidadeTanque)*tanque.getLevelMedidorVazao());

		if (interno.getHeight() > (externo.getHeight()-2))
			interno.setHeight(externo.getHeight() - 2);
		interno.setLayoutY(externo.getLayoutY()+externo.getHeight()-2-interno.getHeight());
	}
	private void calcAlturaTanques2(Rectangle externo, Rectangle interno, Tanque tanque) {
		double a = 0.134234871;
		double b = -4.34684987;
		interno.setHeight((externo.getHeight()/tanque.CapacidadeTanque)*(a*tanque.getLevel()+b));

		if (interno.getHeight() > (externo.getHeight()-2))
			interno.setHeight(externo.getHeight() - 2);
		interno.setLayoutY(externo.getLayoutY()+externo.getHeight()-2-interno.getHeight());
	}
	class RelogioRampa2 extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub
			String status = "Aguardando....";
			if (HLT.Aquecendo())
				 status = "Aquecendo em "+HLT.getTemperaturaAquecimentoAtual()+", "+HLT.getTempoAquecimentoAtual()+"s de "+" "+HLT.getTempoAquecimentoTotal()+"s ";
			else
				timer.cancel();

			statusAquecimento.setText(status);

		}


	}

	class RelogioUpdate extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			valvulas.updateStatus();

			//vazoes.updateVazoes();
			
			temperaturas.updateTemperaturas();
			resistencias.updateResistencias();
			bomba.updateStatus();
			volumeTQ1.setText(String.valueOf(HLT.getLevelMedidorVazao()));
			temperaturaTQ1.setText(String.valueOf(HLT.getTemperatura()));
			temperaturaTQ2.setText(String.valueOf(temperaturas.getTemperatura(1).getTemperatura()));
			temperaturaTQ3.setText(String.valueOf(temperaturas.getTemperatura(2).getTemperatura()));
			medidorvazao1.setText(String.valueOf(vazoes.getVazao(0).getInstantaneo())+"l/m");
			medidorvazao2.setText(String.valueOf(vazoes.getVazao(1).getInstantaneo())+"l/m");
			medidorvazao3.setText(String.valueOf(vazoes.getVazao(2).getInstantaneo())+"l/m");

			resistencia1.setStroke(resistencias.getResistencia(0).getColorStatus());
			resistencia2.setStroke(resistencias.getResistencia(1).getColorStatus());
			resistencia3.setStroke(resistencias.getResistencia(2).getColorStatus());

			valorresistencia1.setText(String.valueOf((resistencias.getResistencia(0).getPotencia()/255)*100));
			valorresistencia2.setText(String.valueOf((resistencias.getResistencia(1).getPotencia()/255)*100));
			bombaCirculoInterno.setFill(bomba.getColorStatus());
			calcAlturaTanques(tq1_externo, tq1_interno, HLT);
			calcAlturaTanques2(tq2_externo, tq2_interno, MLT);
			calcAlturaTanques2(tq3_externo, tq3_interno, BK);
			if (etapas.etapacorrente != null)
				etapas.etapacorrente.verificarExecutando();
			
			//Status do sistema
			if (HLT.Aquecendo()) {
				statusAquecimento.setText(HLT.getMsgStatus());
				tempo.setText(String.valueOf(HLT.getTempoDecorrido()));
			
			}
			//medidorvazao4.setText(String.valueOf(vazoes.getVazao(3))+"l/m");
			//System.out.println(String.valueOf(vazoes.getVazao(0))+"l/m");
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}


	}

}
