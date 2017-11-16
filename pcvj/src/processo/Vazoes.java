package processo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import comunicacaoJava.ComunicacaoTCP;
import processo.Tanque.relogioUpdate;


public class Vazoes {
	
	static int Nmedidores = 4;
	ArrayList<Vazao> vazoes = new ArrayList<Vazao>();
	ComunicacaoTCP comunicacao = new ComunicacaoTCP(ComunicacaoTCP.ip_default, ComunicacaoTCP.porta_default);
	
	Timer timerUpdate;
	
	public Vazoes() {
		for (int i=0; i<Nmedidores;i++)
			vazoes.add(new Vazao());
		
		timerUpdate = new Timer();
		timerUpdate.scheduleAtFixedRate(new relogioUpdate(), 2000, 1000);
	}
	
	public float getVazao(int indice) {
		return vazoes.get(indice).getInstantaneo();
	}
	public float getAcumulado(int indice) {
		return vazoes.get(indice).getAcumulado();
	}
	
	private void updateVazoes() {
		float[][] Vvazoes = comunicacao.getFlows();
		for (int i=0;i<Nmedidores;i++){
			vazoes.get(i).setInstAcumulado(Vvazoes[i][0], Vvazoes[i][1]);
//			System.out.println("VAzao e acumulado");
//			System.out.println(vazoes.get(i).getInstantaneo());
//			System.out.println(vazoes.get(i).getAcumulado());
		}
	}
	
	class relogioUpdate extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub
			updateVazoes();


	}

	}
}
class Vazao{
	private float instantaneo;
	private float acumulado;
	
	public float getInstantaneo() {
		return instantaneo;
	}
	
	public float getAcumulado() {
		return acumulado;
	}
	
	public void setInstantaneo(float instantaneo) {
		this.instantaneo = instantaneo;
	}
	public void setAcumulado(float acumulado) {
		this.acumulado = acumulado;
	}
	public void setInstAcumulado(float instantaneo, float acumulado) {
		this.instantaneo = instantaneo;
		this.acumulado = acumulado;
	}
}
	
