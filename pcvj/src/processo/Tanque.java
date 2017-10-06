package processo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.shape.Rectangle;

public class Tanque {

	private int numero;
	private float level;
	private int tempoAquecimento, tempoDecorridoAquecimento;
	private Timer timer;

	private ArrayList<RampaAquecimento> rampa;
	private int rampaAtual = 0;

	public void aquecer(int tempo, float temperatura){
		tempoDecorridoAquecimento = 0;
		tempoAquecimento = tempo;
		timer = new Timer();
        timer.schedule(new relogio(), 1000);

	}

	public void aquecer(RampaAquecimento rampaquecer){

		tempoDecorridoAquecimento = 0;
		tempoAquecimento = rampaquecer.getTempo();
		timer = new Timer();
		timer.schedule(new RelogioRampa(), 1000);
	}

	public void pararAquecer(){}

	public void addRampaAquecimento(int tempo, float temperatura){

		rampa.add(new RampaAquecimento(tempo,temperatura));
	}

	public void removeRampaAquecimento(int index){
		rampa.remove(index);
	}

	public void startRampaAquecimento(){

		if (rampa.isEmpty())
			return;
		while (rampa.get(0).isFinished())
			rampaAtual++;



	}

	public void drenar(){}

	public static void encher(Rectangle tq1,double tempo){

		tq1.setHeight(tempo);
		tq1.setY(tq1.getY()-10);
	}

	public float getLevel(){
		return 0;
	}

	public int getTempoDecorridoAquecimento(){
		return tempoDecorridoAquecimento;
	}


	class relogio extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub
			tempoDecorridoAquecimento++;
			if (tempoDecorridoAquecimento > tempoAquecimento)
			{
				pararAquecer();
				timer.cancel();
			}
		}


	}

	class RelogioRampa extends TimerTask{


		@Override
		public void run() {
			// TODO Auto-generated method stub
			tempoDecorridoAquecimento++;
			if (tempoDecorridoAquecimento > tempoAquecimento)
			{
				timer.cancel();
				rampa.get(rampaAtual).finish();
				rampaAtual++;
				if (rampaAtual >= rampa.size())
					pararAquecer();

				else
					aquecer(rampa.get(rampaAtual));

			}
		}


	}

	class RampaAquecimento{
		private int tempo;
		private float temperatura;
		private boolean finished;


		public RampaAquecimento(int tempo, float temperatura){
			this.tempo = tempo;
			this.temperatura = temperatura;
		}

		public RampaAquecimento(){}


		public void setTempo(int tempo){
			this.tempo = tempo;
		}
		public void setTemperatura(float temperatura){
			this.temperatura = temperatura;
		}

		public int getTempo(){
			return tempo;
		}
		public float getTemperatura(){
			return temperatura;
		}

		public boolean isFinished(){
			return finished;
		}
		public void finish(){
			finished = true;
		}
	}



}
