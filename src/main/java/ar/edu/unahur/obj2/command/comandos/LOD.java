package ar.edu.unahur.obj2.command.comandos;

import ar.edu.unahur.obj2.command.Programable;

public class LOD extends Command{
	private Integer addr;
	
	public LOD(Integer addr){
		this.addr = addr;
	}

	@Override
	protected void doExecute(Programable micro) {
		micro.setAcumuladorA(micro.getAddr(addr));
	}

}
