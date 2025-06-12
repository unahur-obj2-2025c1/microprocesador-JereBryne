package ar.edu.unahur.obj2.command;

import java.util.Arrays;
import java.util.List;

import ar.edu.unahur.obj2.command.comandos.Operable;
import ar.edu.unahur.obj2.command.excepctions.MicroException;

public class Microprocesador implements Programable{
	private static final Integer MIN_ADDR_VALUE = 0;
    private static final Integer MAX_ADDR_VALUE = 1023;
	private Integer acumA = 0;
	private Integer acumB = 0;
	private Integer programCounter = 0;
	List<Integer> memoria = Arrays.asList(new Integer[1024]);
	
	@Override
	public void run(List<Operable> operaciones) {
		operaciones.stream().forEach(o -> o.execute(this));
	}
	@Override
	public void incProgramCounter(){
		programCounter++;
	}
	@Override
	public Integer getProgramCounter() {
		return programCounter;
	}
	@Override
	public void setAcumuladorA(Integer value) {
		this.acumA = value;		
	}
	@Override
	public Integer getAcumuladorA() {
		return acumA;
	}
	@Override
	public void setAcumuladorB(Integer value) {
		this.acumB = value;	
	}
	@Override
	public Integer getAcumuladorB() {
		return acumB;
	}
	@Override
	public void copyFrom(Programable programable) {
		this.acumA = programable.getAcumuladorA();
		this.acumB = programable.getAcumuladorB();
		this.programCounter = programable.getProgramCounter();
	}
	@Override
	public Programable copy() {
		Programable copia = new Microprocesador();
		copia.setAcumuladorA(this.acumA);
		copia.setAcumuladorB(this.acumB);
		Integer i;
		for (i=0; i<this.programCounter; i++) {
			copia.incProgramCounter();
		}
		return copia; 
	}
	
	@Override
	public void reset() {
		this.acumA = 0;
		this.acumB = 0;
		this.programCounter = 0;
		memoria = Arrays.asList(new Integer[1024]);
	}
	@Override
	public void setAddr(Integer addr) {
		if (!estaEnRango(addr))
			throw new MicroException("La posicion de memoria esta fuera de rango, debe estar entre 0 y 1023");
		memoria.set(addr, acumA);		
	}
	
	@Override
	public Integer getAddr(Integer addr) {
		if (!estaEnRango(addr))
			throw new MicroException("La posicion de memoria esta fuera de rango, debe estar entre 0 y 1023");
		return memoria.get(addr);
	}
	
	private boolean estaEnRango(Integer addr) {
		return addr >= MIN_ADDR_VALUE && addr <= MAX_ADDR_VALUE;
	}
	
}