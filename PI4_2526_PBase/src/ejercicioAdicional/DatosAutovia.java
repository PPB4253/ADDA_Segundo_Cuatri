package ejercicioAdicional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DatosAutovia {
	private static double longitudAutovia;
    private static double presupuestoMax;
    private static double umbralKm;
    
    private static int n;
    private static List<Double> posiciones;
    private static List<Double> beneficios;
    private static List<Double> costesConstruccion;
    private static List<Double> intensidad;

    public static void iniDatos(String rutaFichero) {
        posiciones = new ArrayList<>();
        beneficios = new ArrayList<>();
        costesConstruccion = new ArrayList<>();
        intensidad = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(rutaFichero));
            
            // Primera línea: L B
            String[] primera = lineas.get(0).trim().split("\\s+");
            longitudAutovia = Double.parseDouble(primera[0]);
            presupuestoMax = Double.parseDouble(primera[1]);
            umbralKm = Double.parseDouble(primera[2]);

            // Segunda línea: N
            n = Integer.parseInt(lineas.get(1).trim());

            // Datos de los puntos (x, b, c, cop)
            for (int i = 2; i < 2 + n; i++) {
                String[] partes = lineas.get(i).trim().split("\\s+");
                posiciones.add(Double.parseDouble(partes[0]));
                beneficios.add(Double.parseDouble(partes[1]));
                intensidad.add(Double.parseDouble(partes[2]));
                costesConstruccion.add(Double.parseDouble(partes[3]));           
            }
        } catch (IOException e) {
            System.err.println("Error crítico: No se pudo leer el archivo de datos: " + e.getMessage());
        }
    }

    // --- MÉTODOS OBSERVADORES ---

    public static Double 	getLongitudAutovia() { return longitudAutovia; }
    public static Double 	getPresupuestoMax() { return presupuestoMax; }
    public static Double 	getUmbralKm() { return umbralKm; }
    public static Integer 	getNumAreasServicio() { return n; }

    public static Double 	getPosicion(Integer i) { 
    	return (i < 0? 0.0: (i== getNumAreasServicio()? longitudAutovia: posiciones.get(i))); }
    public static Double 	getBeneficio(Integer i) { return beneficios.get(i); }
    public static Double 	getCosteConstruccion(Integer i) { return costesConstruccion.get(i); }
    
    public static Double 	getDistancia(int i, int j) {
        double posI = getPosicion(i);
        double posJ = getPosicion(j);
        return Math.abs(posJ - posI);
    }
    
    public static Double 	getIntensidad(int i) {
        if (i < 0 || i >= getNumAreasServicio()) return 0.0;
        return intensidad.get(i);
    }
 
}
