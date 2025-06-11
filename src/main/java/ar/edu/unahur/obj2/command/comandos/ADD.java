package ar.edu.unahur.obj2.command.comandos;

import ar.edu.unahur.obj2.command.Programable;

public class ADD extends Command{

	@Override
	protected void doExecute(Programable micro) {
		Integer suma = micro.getAcumuladorA() + micro.getAcumuladorB();
		micro.setAcumuladorA(suma);
		micro.setAcumuladorB(0);
	}
	
}
