package ar.edu.unahur.obj2.command.comandos;

import ar.edu.unahur.obj2.command.Programable;

public class SWAP extends Command{

	@Override
	protected void doExecute(Programable micro) {
		Integer valA = micro.getAcumuladorA();
		micro.setAcumuladorA(micro.getAcumuladorB());
		micro.setAcumuladorB(valA);
	}

}
