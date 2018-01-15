package processo;

import java.util.Timer;
import java.util.TimerTask;

import processo.Valvulas.Valvula;
import processo.Vazoes.Vazao;

public class Etapas{




	public Etapa estapacorrente;


	public void teste(){
		Etapa1 tesste = new Etapa1();

	estapacorrente = tesste;

	}



	public class Etapa{

		public int numero;
		public boolean concluida;
		public boolean executando;

		public boolean verificarConcluida(){

			return concluida;
		}

		public boolean verificarExecutando(){
			return executando;
		}

		public void executarEtapa(){

		}

	}

	public class Etapa1 extends Etapa{


		private Tanque HLT;
		private float volumeEncher;


		public void setTanqueHLT(Tanque HLT){
			this.HLT = HLT;
		}

		public void setVolumeEncher(float volumeEncher){
			this.volumeEncher = volumeEncher;
		}
		public void executarEtapa(){

			executando = true;
			concluida = false;
			HLT.encher(volumeEncher);


		}

		public boolean verificarConcluida(){

			if (HLT.getLevel() >= volumeEncher){
				executando = false;
				concluida = true;
			}

			return concluida;
		}

	}

	public class Etapa2 extends Etapa{

		private Tanque HLT;
		private float temperaturaAquecer;

		public void setTemperaturaAquecer(float temperaturaAquecer){
			this.temperaturaAquecer = temperaturaAquecer;
		}

		public void setHLT(Tanque HLT){
			this.HLT = HLT;
		}

		public void executarEtapa(){
			HLT.addRampaAquecimento(10000, temperaturaAquecer);
			executando = true;
			concluida = false;
		}

		public boolean verificarConcluida(){
			if (HLT.getTemperatura() >= temperaturaAquecer){
				executando = false;
				concluida = true;
			}

			return concluida;
		}

	}

	public class Etapa3 extends Etapa{
		private Tanque HLT, MLT;
		private float volumeTransferir;
		public void setTanques(Tanque HLT,Tanque MLT){
			this.HLT = HLT;
			this.MLT = MLT;
		}

		public void setVolumeTransferirMLT(float volume){
			this.volumeTransferir = volume;
		}

		public void executarEtapa(){
			executando = true;
			concluida = false;
			MLT.encher(volumeTransferir);

		}

		public boolean verificarConcluida(){
			if (MLT.getLevel() >= volumeTransferir){
				executando = false;
				concluida = true;
			}
			return concluida;
		}
	}

	public class Etapa4 extends Etapa{
		private Tanque HLT, MLT;
		private float volumeTransferir;
		private Valvula valvulaSuccao, valvulaDescarga;
		private Bomba bomba;
		private Vazao medidorVazao;
		private float setVazao;

		public void setTanques(Tanque HLT,Tanque MLT){
			this.HLT = HLT;
			this.MLT = MLT;
		}

		public void setValvulas(Valvula valvulaSuccao, Valvula valvulaDescarga){
			this.valvulaSuccao = valvulaSuccao;
			this.valvulaDescarga = valvulaDescarga;
		}

		public void setBomba(Bomba bomba){
			this.bomba = bomba;
		}

		public void setMedidorVazao(Vazao medidorVazao){
			this.medidorVazao = medidorVazao;
		}

		public void setSetVazao(float setVazao){
			this.setVazao = setVazao;
		}

		public void executarEtapa(){
			executando = true;
			concluida = false;
			valvulaSuccao.abrir();
			valvulaDescarga.abrir();

			//Aguardar succao abrir
			while(valvulaSuccao.isClosed());

			bomba.setMedidor(medidorVazao);
			bomba.setVazao(setVazao);
			bomba.ligar();
			MLT.startRampaAquecimento();



		}

		public boolean verificarConcluida(){
			executando =  !MLT.AquecimentoConcluido();
			concluida = MLT.AquecimentoConcluido();
			return concluida;
		}

	}
	public class Etapa5A extends Etapa{
		private Tanque MLT, BK;
		private Bomba bomba;
		private Vazao medidorBK;
		private Valvula saidaMLT, entradaBK;
		private float setVazao, volumeAserTransferido;
		public void setTanques(Tanque MLT, Tanque BK) {
			this.MLT = MLT;
			this.BK = BK;
		}
		public void setBomba(Bomba bomba) {
			this.bomba = bomba;
		}
		
		public void setMedidorVazao(Vazao medidorBK) {
			this.medidorBK = medidorBK;
		}
		
		public void executarEtapa() {
			executando = true;
			concluida = false;
			saidaMLT.abrir();
			entradaBK.abrir();
			while(saidaMLT.isClosed());			
			medidorBK.resetAcumulado();
			bomba.setMedidor(medidorBK);
			bomba.setVazao(setVazao);
			bomba.ligar();			
		}
		
		public boolean verificarConcluida() {
			if (medidorBK.getAcumulado() >= volumeAserTransferido) {
				executando = false;
				concluida = true;
			}
			return concluida;
		}
		
	}
	
	public class Etapa5B extends Etapa{
		private Tanque HLT, MLT, BK;
		private Bomba bomba;
		private Vazao medidorBK, medidorHLT;
		private Valvula saidaMLT, entradaBK;
		private float setVazao, volumeAserTransferido1;
		public void setTanques(Tanque HLT, Tanque MLT, Tanque BK) {
			this.HLT = HLT;
			this.MLT = MLT;
			this.BK = BK;
		}
		public void setBomba(Bomba bomba) {
			this.bomba = bomba;
		}
		
		public void setMedidorVazao(Vazao medidorBK, Vazao medidorHLT) {
			this.medidorBK = medidorBK;
			this.medidorHLT = medidorHLT;
		}
		
		public void executarEtapa() {
			executando = true;
			concluida = false;
			saidaMLT.abrir();
			entradaBK.abrir();
			while(saidaMLT.isClosed());			
			medidorBK.resetAcumulado();
			bomba.setMedidor(medidorBK);
			bomba.setVazao(setVazao);
			bomba.ligar();
			
			
		}
		
	}
	
	public class Etapa6 extends Etapa{

		private Tanque BK;
		private float temperaturaAquecer;
		private float tempoFervura;
		private boolean tempAtigida;
		private long tempoInicial = 0;
		public void setTemperaturaAquecer(float temperaturaAquecer){
			this.temperaturaAquecer = temperaturaAquecer;
		}
		
		public void setTempoFervura(float tempoFervura) {
			this.tempoFervura = tempoFervura*1000;
		}

		public void setBK(Tanque BK){
			this.BK = BK;
		}

		public void executarEtapa(){
			BK.addRampaAquecimento(10000, temperaturaAquecer);
			executando = true;
			concluida = false;
			tempAtigida = false;
		}

		public boolean verificarConcluida(){
			if ((BK.getTemperatura() >= temperaturaAquecer) || (tempAtigida)){
				if (!tempAtigida) {
					tempoInicial = System.currentTimeMillis();
					tempAtigida = true;
				}
				
				if ((System.currentTimeMillis() - tempoInicial) >= tempoFervura) {
					executando = false;
					concluida = true;
				}
			}

			return concluida;
		}

	}
	public class Etapa7 extends Etapa{
		private Tanque BK;
		private Bomba bomba;
		private Vazao medidorBK;
		private Valvula saidaBk, entradaBK;
		private float setVazao;
		private long tempo=180000, tempoInical = 0;
		public void setTanques(Tanque BK) {

			this.BK = BK;
		}
		public void setBomba(Bomba bomba) {
			this.bomba = bomba;
		}
		public void setValvulas(Valvula saidaBK, Valvula entradaBK) {
			this.saidaBk = saidaBK;
			this.entradaBK = entradaBK;
		}
		public void setMedidorVazao(Vazao medidorBK) {
			this.medidorBK = medidorBK;
		}
		
		public void setTempo(long tempo) {
			this.tempo = tempo*1000;
		}
		
		public void executarEtapa() {
			executando = true;
			concluida = false;
			saidaBk.abrir();
			entradaBK.abrir();
			while(saidaBk.isClosed());			
			medidorBK.resetAcumulado();
			bomba.setMedidor(medidorBK);
			bomba.setVazao(setVazao);
			tempoInical = System.currentTimeMillis();
			bomba.ligar();			
		}
		
		public boolean verificarConcluida() {
			if ((System.currentTimeMillis()-tempoInical) >= tempo) {
				executando = false;
				concluida = true;
			}
			return concluida;
		}
		
	}
	
	public class Etapa8 extends Etapa{
		private Tanque BK;
		private Bomba bomba;
		private Vazao medidorBK, medidorChiller;
		private Valvula saidaBk, entradaChiller, entradaAguaChiller;
		private float nivelMinimo;
		private float setVazao;
		public void setTanques(Tanque BK) {
			this.BK = BK;
		}
		public void setBomba(Bomba bomba) {
			this.bomba = bomba;
		}
		public void setValvulas(Valvula saidaBK, Valvula entradaChiller, Valvula entradaAguaChiller) {
			this.saidaBk = saidaBK;
			this.entradaChiller = entradaChiller;
			this.entradaAguaChiller = entradaAguaChiller;
		}
		public void setMedidorVazao(Vazao medidorBK, Vazao medidorChiller) {
			this.medidorBK = medidorBK;
			this.medidorChiller = medidorChiller;
		}
		
		
		public void executarEtapa() {
			executando = true;
			concluida = false;
			saidaBk.abrir();
			entradaChiller.abrir();
			entradaAguaChiller.abrir();
			while(saidaBk.isClosed());			
			medidorBK.resetAcumulado();
			medidorChiller.resetAcumulado();
			bomba.setMedidor(medidorBK);
			bomba.setVazao(setVazao);
			bomba.ligar();			
		}
		
		public boolean verificarConcluida() {
			if (BK.getLevel() <= nivelMinimo) {
				executando = false;
				concluida = true;
			}
			return concluida;
		}
		
	}

}
