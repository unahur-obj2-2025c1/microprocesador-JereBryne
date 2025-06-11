package ar.edu.unahur.obj2.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.command.comandos.Operable;
import ar.edu.unahur.obj2.command.excepctions.MicroException;

public class MicroprocesadorTest {
	private Microprocesador micro;
    private ProgramBuilder programBuilder;

    // Se ejecuta antes de cada test para asegurar un microprocesador "limpio"
    @BeforeEach
    void setUp() {
        micro = new Microprocesador();
        programBuilder = new ProgramBuilder();
    }

    // --- Tests para el PC ---

    @Test
    @DisplayName("El PC avanza 3 posiciones con 3 NOPs")
    void pcAvanzaConNops() {
        // Construye el programa: NOP, NOP, NOP
        List<Operable> programa = programBuilder
                .nop()
                .nop()
                .nop()
                .build(); // Asumo que ProgramBuilder tiene un método build() para retornar la lista

        micro.run(programa);

        // El PC debe estar en 3 (0 -> 1, 1 -> 2, 2 -> 3)
        assertEquals(3, micro.getProgramCounter(), "El PC debe avanzar 3 posiciones después de 3 NOPs.");
    }
    
    @Test
    @DisplayName("Suma 2 + 8 + 5 correctamente, acumulador A=15, B=0")
    void sumaTresNumeros() {
        // Asume que el microprocesador inicia con PC=0
        List<Operable> programa = programBuilder
        		.lodv(2)   // A=2, B=0, PC=1
                .swap()    // A=0, B=2, PC=2
                .lodv(8)   // A=8, B=0, PC=3
                .add()     // A=10, B=0, PC=4 (2+8)
                .swap()    // A=0, B=10, PC=5
                .lodv(5)   // A=5, B=0, PC=6
                .add()     // A=15, B=0, PC=7 (5+10)
                .build();

        micro.run(programa);

        assertEquals(15, micro.getAcumuladorA(), "Acumulador A debe ser 15 después de la suma de 2+8+5.");
        assertEquals(0, micro.getAcumuladorB(), "Acumulador B debe ser 0 al final.");
        assertEquals(7, micro.getProgramCounter(), "El PC debe estar en 7 después de 7 operaciones.");
    }
    
    @Test
    @DisplayName("LOD a dirección fuera de rango detiene el programa y mantiene PC")
    void lodFueraDeRangoDetienePrograma() {
        // Pre-carga un valor en una dirección válida para que no sea null
        micro.setAcumuladorA(100);
        micro.setAddr(500); // Guardo 100 en mem[500]
        micro.reset(); // Reseteo para que el PC inicie en 0 y acumuladores en 0

        List<Operable> programa = programBuilder
                .lodv(5)   // PC=0 -> PC=1
                .str(50)   // PC=1 -> PC=2
                .lod(1024) // PC=2 -> ESTA FALLA. PC debe quedar en 2
                .nop()     // Esto no se ejecuta
                .build();

        // Si se lanza MicroException, la captura
        assertThrows(MicroException.class, () -> micro.run(programa),
                "Debe lanzar MicroException al intentar LOD fuera de rango.");

        assertEquals(2, micro.getProgramCounter(), "El PC debe quedar en la dirección de la instrucción fallida (2).");
        // Los valores de acumuladores no se verifican porque la ejecución se detuvo
        // y no se garantiza el estado final más allá del PC.
    }
    
    @Test
    @DisplayName("STR a dirección fuera de rango detiene el programa y mantiene PC")
    void strFueraDeRangoDetienePrograma() {
        List<Operable> programa = programBuilder
                .lodv(10)  // PC=0 -> PC=1
                .str(-1)   // PC=1 -> ESTA FALLA. PC debe quedar en 1
                .nop()     // Esto no se ejecuta
                .build();

        assertThrows(MicroException.class, () -> micro.run(programa),
                "Debe lanzar MicroException al intentar STR fuera de rango.");

        assertEquals(1, micro.getProgramCounter(), "El PC debe quedar en la dirección de la instrucción fallida (1).");
    }
    
    @Test
    @DisplayName("getAddr de posición no escrita devuelve null y no lanza excepcion (si el rango es valido)")
    void getAddrPosicionNoEscrita() {
        // No hay un test directo para getAddr(addr) solo, ya que es un método interno
        // y LOD lo llama. Si LOD falla por null, es un NullPointerException, no MicroException.
        // Pero la consigna no dice que debe lanzar excepción por leer memoria no inicializada.
        // Solo por rango.
        // Si quisieras que lanzara error, tendrías que modificar getAddr.

        // Ejemplo: Si cargas una posición no escrita (ej. 100) en A, A debería ser null
        List<Operable> programa = programBuilder
                .lod(100) // Mem[100] no ha sido escrita, debería devolver null
                .nop()
                .build();

        micro.run(programa);
        assertNull(micro.getAcumuladorA(), "Acumulador A debe ser null si se lee una posición de memoria no inicializada.");
        assertEquals(2, micro.getProgramCounter(), "El PC debe avanzar normalmente si se lee una posición válida pero no escrita.");
    }
    
    @Test
    @DisplayName("Lanzar excepcion al intentar construir un programa sin operaciones")
    void buildThrowsExceptionWhenNoOperations() {
        // Ejecutar el método build() sin haber añadido ninguna operación
        // Se espera que lance una MicroException
        MicroException thrown = assertThrows(MicroException.class, () -> {
            programBuilder.build();
        }, "Debe lanzar una MicroException si no hay operaciones.");

        // Verificar el mensaje de la excepción
        assertEquals("No se puede builder un progama sin operaciones", thrown.getMessage(),
                     "El mensaje de la excepción debe ser el esperado.");
    }
    
}
