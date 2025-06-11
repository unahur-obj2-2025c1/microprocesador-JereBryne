package ar.edu.unahur.obj2.command;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unahur.obj2.command.comandos.ADD;
import ar.edu.unahur.obj2.command.comandos.LOD;
import ar.edu.unahur.obj2.command.comandos.LODV;
import ar.edu.unahur.obj2.command.comandos.NOP;
import ar.edu.unahur.obj2.command.comandos.Operable;
import ar.edu.unahur.obj2.command.comandos.STR;
import ar.edu.unahur.obj2.command.comandos.SWAP;
import ar.edu.unahur.obj2.command.excepctions.MicroException;

public class ProgramBuilder {
	private List<Operable> operaciones = new ArrayList<>();
	
	public ProgramBuilder nop() {
		operaciones.add(new NOP());
		return this;
	}
	
	public ProgramBuilder add() {
		operaciones.add(new ADD());
		return this;
	}
	
	public ProgramBuilder swap() {
		operaciones.add(new SWAP());
		return this;
	}
	
	public ProgramBuilder lod(Integer addr) {
		operaciones.add(new LOD(addr));
		return this;
	}
	
	public ProgramBuilder str(Integer addr) {
		operaciones.add(new STR(addr));
		return this;
	}
	
	public ProgramBuilder lodv(Integer val) {
		operaciones.add(new LODV(val));
		return this;
	}
	
	public List<Operable> build() {
        if (operaciones.isEmpty())
            throw new MicroException("No se puede builder un progama sin operaciones");
        return operaciones;
    }
}
