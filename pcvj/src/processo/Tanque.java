package processo;

import java.util.Timer;
import java.util.TimerTask;

public class Tanque {

	private int numero;
	private float level;
	private int tempoAquecimento, tempoDecorridoAquecimento;
	private Timer timer;
	public void aquecer(int tempo, float temperatura){
		timer = new Timer();
        timer.schedule(new relogio(), 1000);

	}

	public void pararAquecer(){}

	public void drenar(){}

	public void encher(){}

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



}
