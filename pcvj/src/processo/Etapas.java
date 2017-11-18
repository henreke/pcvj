package processo;

import processo.Valvulas.Valvula;

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

			HLT.encher(volumeEncher);
			executando = true;
			concluida = false;

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
			MLT.encher(volumeTransferir);
			executando = true;
			concluida = false;
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
		public void setTanques(Tanque HLT,Tanque MLT){
			this.HLT = HLT;
			this.MLT = MLT;
		}

		public void setValvulas(Valvula valvulaSuccao, Valvula valvulaDescarga){
			this.valvulaSuccao = valvulaSuccao;
			this.valvulaDescarga = valvulaDescarga;
		}


		public void executarEtapa(){

			valvulaSuccao.abrir();
			valvulaDescarga.abrir();

			//Aguardar succao abrir
			while(valvulaSuccao.isClosed());




		}

	}
}
