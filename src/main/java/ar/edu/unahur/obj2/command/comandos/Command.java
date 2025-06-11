package ar.edu.unahur.obj2.command.comandos;

import ar.edu.unahur.obj2.command.Programable;

public abstract class Command implements Operable {

	@Override
	public void execute(Programable micro) {
		doExecute(micro);
		micro.incProgramCounter();
	}

	protected abstract void doExecute(Programable micro);

/*	@Override
	public void undo(Programable micro) {

	}
*/
}
