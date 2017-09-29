 public static final char VALVULA = 10;
 public static final char PID = 11;
 public static final char BOMBA = 12;
 public static final char UPDATE = 13;

 public static final char ABRIR = 'A';
 public static final char FECHAR = 'F';

void tratarComando(char * comando){


	if ((comando[0] != '$') && (comando[sizeof(comando)-1] !='$'))
		return;

	String readString = "";
	for (int i=3; i<(sizeof(comando)-1;i++)
		readString+=comando[i];

	switch (comando[1])
	{
		case VALVULA: break;
		case PID: break;
		case BOMBA: break;
		case UPDATE: break;
	}




}

void comandarValvula(String comando){

	int ind1  = comando.indexOf('#');
	int nValvula = comando.substring(0,ind1),toInt();
	int ind2 = comando.indexOf('#',ind1+1);
	char estado = comando.substring(ind1+1,ind2+1);


}

void comandarPID(String comando){

	int ind1 = comando.indexOf('#');
	float setPoint = comando.substring(0,ind1).toFloat();
	int ind2 = comando.indexOf('#',ind1+1);
	int nPID = comando.substring(ind1+1,ind2+1).toInt();
	int ind3 = comando.indexOf('#',ind2+1);
	float PIDp = comando.substring(ind2+1,ind3+1).toFloat();
	int ind4 = comando.indexOf('#',ind3+1);
	float PIDi = comando.substring(ind3+1,ind4+1).toFloat();
	int ind5 = comando.indexOf('#',ind4+1);
	float PIDd = comando.substring(ind5+1);
	//IniciaPID

}
		  /* ind1 = readString.indexOf(',');  //finds location of first ,
		  angle = readString.substring(0, ind1);   //captures first data String
		  ind2 = readString.indexOf(',', ind1+1 );   //finds location of second ,
		  fuel = readString.substring(ind1+1, ind2+1);   //captures second data String
		  ind3 = readString.indexOf(',', ind2+1 );
		  speed1 = readString.substring(ind2+1, ind3+1);
		  ind4 = readString.indexOf(',', ind3+1 );
		  altidude = readString.substring(ind3+1); //captures remain part of data after last ,
 */