package ar.edu.unahur.obj2.command.comandos;

import ar.edu.unahur.obj2.command.Microprocesador;
import ar.edu.unahur.obj2.command.Programable;

public abstract class Command implements Operable {
	protected Programable programableUndo= new Microprocesador();

	@Override
	public void execute(Programable micro) {
		programableUndo = micro.copy();
		doExecute(micro);
		micro.incProgramCounter();
	}

	protected abstract void doExecute(Programable micro);

	@Override
	public void undo(Programable micro) {
		micro.copyFrom(programableUndo);
	}

}
