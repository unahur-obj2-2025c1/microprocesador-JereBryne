package ar.edu.unahur.obj2.command.comandos;

import ar.edu.unahur.obj2.command.Programable;

public class STR extends Command{
	private Integer addr;
	
	public STR(Integer addr){
		this.addr = addr;
	}

	@Override
	protected void doExecute(Programable micro) {
		micro.setAddr(addr);
	}	
}
