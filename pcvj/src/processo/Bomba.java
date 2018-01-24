package processo;

import java.io.IOException;


import comunicacaoJava.ComunicacaoTCP;
import javafx.scene.paint.Color;
import processo.Resistencias.Resistencia;
import processo.Vazoes.Vazao;

public class Bomba{


	private float setVazao;
	private Vazao medidor;
	int potencia;
	char status;
	PID pid = new PID(4,10,10,5,0);
	ComunicacaoTCP comunicacao = new ComunicacaoTCP(ComunicacaoTCP.ip_default,ComunicacaoTCP.porta_default);
	public void ligar()
	{
		try {
			comunicacao.ligarBomba();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void desligar(){

		try {
			comunicacao.desligarBomba();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setMedidor(Vazao medidor){
		this.medidor = medidor;
	}
	public void setVazao(float setVazao){
		this.setVazao = setVazao;
		pid.setSetPoint(setVazao);
		try {
			comunicacao.sendPID(pid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateStatus() {
		int[] stat = comunicacao.getStatusBomba();

		status = (char) stat[0];
		potencia = stat[1];
	}

	public char getStatus() {
		return status;
	}
	public int getPotencia() {
		return potencia;
	}

	public Color getColorStatus() {

		if (status == Resistencia.LIGADA)
			return Color.GREEN;
		else
			return Color.RED;

	}

}
