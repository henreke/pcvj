package processo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import comunicacaoJava.ComunicacaoTCP;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;

public class Tanque {

	private int numero;
	private float level;
	private int tempoAquecimento, tempoDecorridoAquecimento;
	private Timer timer;

	public PID pid;
	private boolean Aquecendo = false;
	private boolean AquecimentoConcluido = false;
	private ArrayList<RampaAquecimento> rampa = new ArrayList<RampaAquecimento>();
	private int rampaAtual = 0;

	public void aquecer(int tempo, float temperatura){
		tempoDecorridoAquecimento = 0;
		tempoAquecimento = tempo;
		timer = new Timer();
        timer.scheduleAtFixedRate(new relogio(),0, 1000);

        alternarStatusAquecimento(true);

	}


	private void alternarStatusAquecimento(boolean status){
		Aquecendo = status;
		AquecimentoConcluido = !status;
	}
	public void aquecer(RampaAquecimento rampaquecer){

		tempoDecorridoAquecimento = 0;
		tempoAquecimento = rampaquecer.getTempo();

		timer = new Timer();

		timer.scheduleAtFixedRate(new RelogioRampa(),0, 1000);
		alternarStatusAquecimento(true);
	}

	public void pararAquecer(){

		alternarStatusAquecimento(false);
	}

	public int addRampaAquecimento(int tempo, float temperatura){

		RampaAquecimento novarampa = new RampaAquecimento(tempo,temperatura);

		rampa.add(novarampa);
		return rampa.size()-1;
	}

	public RampaAquecimento getRampa(int i){
		return rampa.get(i);
	}

	public void removeRampaAquecimento(int index){
		rampa.remove(index);
	}

	public boolean startRampaAquecimento(){

		rampaAtual = 0;

		if (rampa.isEmpty())
			return false;
		while (rampa.get(rampaAtual).isFinished()){
			rampaAtual++;
			if (rampaAtual >= rampa.size())
				return false;
		}
		aquecer(rampa.get(rampaAtual));
		return true;
	}

	public ArrayList<RampaAquecimento> getArrayRampaAquecimento(){
		return rampa;
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


	public float getTemperaturaAquecimentoAtual(){
		if (rampaAtual < rampa.size())
			return rampa.get(rampaAtual).getTemperatura();
		else
			return -1;
	}
	public int getTempoAquecimentoAtual(){
		if (rampaAtual < rampa.size())
			return tempoDecorridoAquecimento;
		else
			return -1;
	}

	public int getTempoAquecimentoTotal(){
		if (rampaAtual < rampa.size())
			return tempoAquecimento;
		else
			return -1;
	}
	public boolean Aquecendo(){
		return Aquecendo;
	}
	public boolean AquecimentoConcluido(){
		return AquecimentoConcluido;
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
			if (tempoDecorridoAquecimento >= tempoAquecimento)
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

	public class RampaAquecimento{
		private final IntegerProperty tempo;
		private final FloatProperty temperatura;
		private final BooleanProperty finished;


		//private final IntegerProperty postalCode;

		public RampaAquecimento(int tempo, float temperatura){
			this.tempo = new SimpleIntegerProperty(tempo);
			this.temperatura = new SimpleFloatProperty(temperatura);
			this.finished = new SimpleBooleanProperty(false);
		}

		public RampaAquecimento(){
			this.tempo = new SimpleIntegerProperty(0);
			this.temperatura = new SimpleFloatProperty(0);
			this.finished = new SimpleBooleanProperty(false);
		}


		public void setTempo(int tempo){
			this.tempo.set(tempo);
		}
		public void setTemperatura(float temperatura){
			this.temperatura.set(temperatura);
		}

		public int getTempo(){
			return this.tempo.get();
		}
		public float getTemperatura(){
			return this.temperatura.get();
		}

		public boolean isFinished(){
			return this.finished.get();
		}
		public void finish(){
			this.finished.set(true);
		}

		public IntegerProperty tempoProperty() {
	        return this.tempo;
	    }
		public FloatProperty temperaturaProperty(){
			return this.temperatura;
		}
		public BooleanProperty finishedProperty(){
			return this.finished;
		}
	}



}
