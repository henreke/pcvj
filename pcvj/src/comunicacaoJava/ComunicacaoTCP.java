package comunicacaoJava;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import processo.PID;

/**
 * Created by henreke on 7/24/2017.
 */

public class ComunicacaoTCP {

    Socket conexao;
    OutputStream canal;
    private String ip;
    private int porta;

    public void setIp(String ip){
        this.ip = ip;
    }
    public String getIp(){
        return this.ip;
    }

    public void setPorta(int porta){
        this.porta = porta;
    }
    public int getPorta(){
        return this.porta;
    }
    public OutputStream conectar(){

        try {
            if (conexao == null){
                conexao = new Socket(this.ip,this.porta);
               // conexao.connect(conexao.getRemoteSocketAddress(), 3000);
            }
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
    public ComunicacaoTCP (String ip, int porta)
    {
        this.ip = ip;
        this.porta = porta;
    }

    public void sendMessage(String msg) throws IOException {
        canal = conectar();
        if (canal != null) {
            String msg2 ='$'+msg+'$';
            canal.write(msg2.getBytes());
            desconectar();
        }
    }

    public String sendMessageUpdate(String msg) throws IOException {
        canal = conectar();
        String resposta ="";
        if (canal != null) {
        	DataInputStream aws = new DataInputStream(conexao.getInputStream());
            String msg2 ='$'+msg+'$';
            canal.write(msg2.getBytes());
            byte[] respostaB = new byte[50];

            aws.read(respostaB);
            resposta = new String(respostaB,StandardCharsets.UTF_8);
            aws.close();
            desconectar();
        }

        return resposta;
    }

    public void abrirValvula(int nValvula) throws IOException {
            String msg = TipoMSG.VALVULA+"#" + String.valueOf(nValvula)+"#" + String.valueOf(Comandos.ABRIR);
            sendMessage(msg);
    }

    public void fecharValvula(int nValvula) throws IOException {
        String msg = TipoMSG.VALVULA+"#" + String.valueOf(nValvula)+"#" + String.valueOf(Comandos.FECHAR);
        sendMessage(msg);
    }

    public void sendPID(PID pid) throws IOException {
        String msg = TipoMSG.PID+'#'+String.valueOf(pid.setPoint) +'#'
                +String.valueOf(pid.nPID)+'#'+String.valueOf(pid.P)
                +'#'+String.valueOf(pid.I)+'#'+String.valueOf(pid.D)+'#';
        sendMessage(msg);
    }

    public String getUpdate(int tipo, String msg) throws IOException
    {
    	String msgenvio = TipoMSG.UPDATE+'#'+msg;
    	return sendMessageUpdate(msgenvio);
    }
}
class TipoMSG{

    public static final char VALVULA = 10;
    public static final char PID = 11;
    public static final char BOMBA = 12;
    public static final char UPDATE = 13;
}
class Comandos{

    public static final char ABRIR = 'A';
    public static final char FECHAR = 'F';

}




