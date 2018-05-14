package comunicacaoJava;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import processo.PID;
import processo.Resistencias.Resistencia;

/**
 * Created by henreke on 7/24/2017.
 */

public class ComunicacaoSerial {

    

    
	String porta;
    
    public void setPortaCOM(String porta){
        this.porta = porta;
    }
    public int getPortaCOM(){
        return this.porta;
    }
    public OutputStream conectar(){

        try {
            //if (conexao == null){
                conexao = new Socket(this.ip,this.porta);

               // conexao.connect(conexao.getRemoteSocketAddress(), 3000);
          //  }
            return conexao.getOutputStream();
        } catch (IOException e) {
            return null;
        }
    }
    public boolean desconectar(){
        try {
            conexao.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public ComunicacaoSerial(String  porta)
    {
        this.porta = porta;
    }

    public void sendMessage(String msg) throws IOException {
        canal = conectar();
        if (canal != null) {
            String msg2 ='$'+msg+'$';
            canal.write(msg2.getBytes());
        }
    }


    public void abrirValvula(int nValvula) throws IOException {
            String msg = TipoMSG.VALVULA+"#" + String.valueOf(nValvula)+"#" + String.valueOf(Comandos.ABRIR)+"#";
            sendMessage(msg);
    }

    public void fecharValvula(int nValvula) throws IOException {
        String msg = TipoMSG.VALVULA+"#" + String.valueOf(nValvula)+"#" + String.valueOf(Comandos.FECHAR)+"#";
        sendMessage(msg);
    }
    
    public void ligarResistencia(char nResistencia) throws IOException {
    	String msg =  TipoMSG.RESISTENCIA+"#"+nResistencia+"#"+Resistencia.LIGADA+"#";
    	sendMessage(msg);
    }
    
    public void desligarResistencia(char nResistencia) throws IOException {
    	String msg =  TipoMSG.RESISTENCIA+"#"+nResistencia+"#"+Resistencia.DESLIGADA+"#";
    	sendMessage(msg);
    }

    public void ligarBomba() throws IOException {
    	String msg = TipoMSG.BOMBA+"#"+Comandos.LIGAR+"#";
    	sendMessage(msg);
    }

    public void desligarBomba() throws IOException {
    	String msg = TipoMSG.BOMBA +"#"+Comandos.DESLIGAR+"#";
    	sendMessage(msg);
    }
    public void sendPID(PID pid) throws IOException {
        String msg = TipoMSG.PID+"#"+String.valueOf(pid.setPoint) +"#"
                +String.valueOf(pid.nPID)+"#"+String.valueOf(pid.P)
                +"#"+String.valueOf(pid.I)+"#"+String.valueOf(pid.D)+"#";
        sendMessage(msg);
    }

    public String getUpdate(int tipo, String msg) throws IOException
    {
    	String msgenvio = TipoMSG.UPDATE+"#"+msg;
    	return sendMessageUpdate(msgenvio);
    }

    public void sendEncher(int Nsensor, float quantidade, int Nvalvula) throws IOException{
    	String msgenvio = TipoMSG.ENCHERTANQUE+"#"+quantidade+"#"+Nvalvula +"#"+Nsensor+"#";
    	sendMessage(msgenvio);
    }
    
    public void resetAcumulado(int Nsensor) throws IOException {
    	String msg = TipoMSG.RESET_ACUMULADO+"#"+Nsensor+"#";
    	sendMessage(msg);
    }



    public String getStatusValvulas(String valvulas) {


    	try {

			if (valvulas.length() < 2)
				return "";
			if (valvulas.charAt(0) == '$' && valvulas.charAt(valvulas.length() -1) =='$') {
				valvulas =  valvulas.substring(2, valvulas.length()-1);
				return valvulas;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    	return "";
    }

    public float[][] getFlows(String flows) {
    	
    	try {
    		String retorno = flows;
    		if (retorno.length() > 1) {
	    		if (retorno.charAt(0)=='$' && retorno.charAt(retorno.length() - 1)=='$') {
	    			retorno = retorno.substring(2, retorno.length()-2);
	    			String[] valores = retorno.split("#");
	    			float[][] valoresretorno = new float[valores.length/2][2];
	    			for (int i =0; i< valores.length/2; i++) {
	    				valoresretorno[i][0] = Float.parseFloat(valores[2*i]);
	    				valoresretorno[i][1] = Float.parseFloat(valores[2*i+1]);
	    			}
	    			return valoresretorno;
	    		}
    		}
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


    	return null;
    }
    public float[] getTemperaturas(String temperaturas){
    	
    	try {
			String retorno = temperaturas;
			if (retorno.charAt(0)=='$' && retorno.charAt(retorno.length() - 1)=='$') {

				retorno = retorno.substring(2, retorno.length()-2);
				String[] valores = retorno.split("#");
				float[] valoresretorno = new float[valores.length];
				for (int i=0; i< valoresretorno.length;i++)
					valoresretorno[i] = Float.parseFloat(valores[i]);

				return valoresretorno;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return null;
    }
    
    public int[] getResistencias(String resistencias) {
    	
    	
    	try {
			String retorno = resistencias;
			if (retorno.charAt(0)=='$' && retorno.charAt(retorno.length() - 1)=='$') {
				retorno = retorno.substring(2, retorno.length()-2);
				String[] valores = retorno.split("#");
				int[] valoresretorno = new int[valores.length];
				for (int i=0; i< valoresretorno.length;i++)
					valoresretorno[i] = Integer.parseInt(valores[i]);

				return valoresretorno;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	
    	return null;
    }
    public int[] getStatusBomba(String bombas) {
    	
    	
    	try {
			String retorno = bombas;
			if (retorno.charAt(0)=='$' && retorno.charAt(retorno.length() - 1)=='$') {
				retorno = retorno.substring(2, retorno.length()-2);
				String[] valores = retorno.split("#");
				int[] valoresretorno = new int[valores.length];
				for (int i=0; i< valoresretorno.length;i++)
					valoresretorno[i] = Integer.parseInt(valores[i]);

				return valoresretorno;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return null;
    }
    
    
   
}
class TipoMSG{

    public static final char VALVULA = 10;
    public static final char PID = 11;
    public static final char BOMBA = 12;
    public static final char ENCHERTANQUE = 14;
    public static final char UPDATE = 16;
    public static final char RESISTENCIA = 17;
    public static final char RESET_ACUMULADO = 18;
}
class TipoUpdate{
	public static final char LEVEL = 33;
	public static final char FLOW = 36;
	public static final char LEVEL_TEMPERATURE = 38;
	public static final char VALVULAS = 39;
	public static final char TEMPERATURES = 40;
	public static final char UPDATE_STATUS_BOMBA = 41;
	public static final char UPDATE_STATUS_RESISTENCIAS = 42;
}
class Comandos{

    public static final char ABRIR = 'A';
    public static final char FECHAR = 'F';
    public static final char LIGAR = 'L';
    public static final char DESLIGAR = 'D';

}




