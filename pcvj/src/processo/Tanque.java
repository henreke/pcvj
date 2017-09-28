package processo;

import java.util.Timer;
import java.util.TimerTask;

public class Tanque {

	private int numero;
	private float level;
	private float tempoAquecimento, tempoDecorridoAquecimento;
	private Timer timer;
	public void aquecer(int tempo, float temperatura){
		timer = new Timer();
        timer.schedule(new relogio(this), tempo*1000);


	}

	public void pararAquecer(){}

	public void drenar(){}

	public void encher(){}

	public float getLevel(){
		return 0;
	}



	class relogio extends TimerTask{

		Tanque tq;

		public relogio(Tanque tq){
			this.tq = tq;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub

			tq.pararAquecer();

		}


	}



}
