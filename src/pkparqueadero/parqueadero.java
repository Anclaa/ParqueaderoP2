package pkparqueadero;

import java.time.LocalTime;
import java.util.ArrayList;

public class parqueadero{
    public static final int TAMANO=40;
    public static final int NO_HAY_PUESTO=-1;
    public static final int PARQUEADERO_CERRADO = -2;
    public static final int CARRO_NO_EXISTE = -3;
    public static final int CARRO_YA_EXISTE = -4;
    public static final int HORA_INICIAL = 6;
    public static final int HORA_CIERRE = 20;
    public static final int TARIFA_INICIAL = 1200;
    private puesto puestos[];
    private int tarifa;
    private int caja;
    private int horaActual;
    private boolean abierto;
    public parqueadero( )
    {
        horaActual = HORA_INICIAL;
        abierto = true;
        tarifa = TARIFA_INICIAL;
        caja = 0;
        // Crea el arreglo de puestos e inicializa cada uno de ellos
        puestos = new puesto[TAMANO];
        for( int i = 0; i < TAMANO; i++ )
            puestos[ i ] = new puesto( i );

    }
    public String darPlacaCarro( int pPosicion )
    {
        String respuesta = "";
        if(estaOcupado(pPosicion))
        {
            respuesta = "Placa: " + puestos[ pPosicion ].darCarro( ).darPlaca( );
        }
        else
        {
            respuesta = "No hay un carro en esta posici�n";
        }

        return respuesta;
    }
    public int entrarCarro( String pPlaca, int lugar )
    {
        int resultado = 0;
        if( !abierto )
        {
            resultado = PARQUEADERO_CERRADO;
        }
        else
        {
            // Buscar en el parqueadero un carro con la placa indicada
            int numPuestoCarro = buscarPuestoCarro( pPlaca.toUpperCase( ) );
            if( numPuestoCarro != CARRO_NO_EXISTE )
            {
                resultado = CARRO_YA_EXISTE;
            }

            // Buscar un puesto libre para el carro y agregarlo
            resultado = buscarPuestoLibre( );
            if( resultado != NO_HAY_PUESTO )
            {
                carro carroEntrando = new carro( pPlaca, horaActual );
                puestos[ resultado ].parquearCarro( carroEntrando );
            }
        }

        return resultado;
    }
    public int sacarCarro( String pPlaca )
    {
        int resultado = 0;
        if( !abierto )
        {
            resultado = PARQUEADERO_CERRADO;
        }
        else
        {
            int numPuesto = buscarPuestoCarro( pPlaca.toUpperCase( ) );
            if( numPuesto == CARRO_NO_EXISTE )
            {
                resultado = CARRO_NO_EXISTE;
            }
            else
            {
                carro carro = puestos[ numPuesto ].darCarro( );
                int nHoras = carro.darTiempoEnParqueadero( horaActual );
                int porPagar = nHoras * tarifa;
                caja = caja + porPagar;
                puestos[ numPuesto ].sacarCarro( );
                resultado = porPagar;
            }
        }

        return resultado;
    }
    public int darMontoCaja( )
    {
        return caja;
    }
    public int calcularPuestosLibres( )
    {
        int puestosLibres = 0;
        for( puesto puesto : puestos )
        {
            if( !puesto.estaOcupado( ) )
            {
                puestosLibres = puestosLibres + 1;
            }
        }
        return puestosLibres;
    }
    public void cambiarTarifa( int pTarifa )
    {
        tarifa = pTarifa;
    }
    private int buscarPuestoLibre( )
    {
        int puesto = NO_HAY_PUESTO;
        for( int i = 0; i < TAMANO && puesto == NO_HAY_PUESTO; i++ )
        {
            if( !puestos[ i ].estaOcupado( ) )
            {
                puesto = i;
            }
        }
        return puesto;
    }
    private int buscarPuestoCarro( String pPlaca )
    {
        int puesto = CARRO_NO_EXISTE;
        for( int i = 0; i < TAMANO && puesto == CARRO_NO_EXISTE; i++ )
        {
            if( puestos[ i ].tieneCarroConPlaca( pPlaca ) )
            {
                puesto = i;
            }
        }
        return puesto;
    }
    public void avanzarHora( )
    {
        if( horaActual <= HORA_CIERRE )
        {
            horaActual = ( horaActual + 1 );
        }
        if( horaActual == HORA_CIERRE )
        {
            abierto = false;
        }
    }
    public int darHoraActual( )
    {
        return horaActual;
    }
    public boolean estaAbierto( )
    {
        return abierto;
    }
    public int darTarifa( )
    {
        return tarifa;
    }
    public boolean estaOcupado( int pPuesto )
    {
        boolean ocupado = puestos[ pPuesto ].estaOcupado( );
        return ocupado;
    }
    public String metodo1( )
    {
        int cantidadCarrosPB = contarCarrosQueComienzanConPlacaPB();
        boolean hayCarro24Horas = hayCarroCon24Horas();
        String mensaje = "Cantidad de carros con placa PB: " + cantidadCarrosPB;
        mensaje += " – Hay carro parqueado por 24 o más horas: ";
        mensaje += hayCarro24Horas ? "Sí" : "No";
        return mensaje;
    }
    public String metodo2( )
    {
        int cantidadCarrosSacados = desocuparParqueadero();
        return "Cantidad de carros sacados: " + cantidadCarrosSacados;

    }
    public double dartiempoPromedio(){
        int totalTiempo = 0;
        int totalCarros = 0;
        for (puesto p : puestos) {
            if (p.estaOcupado()) {
                carro c = p.darCarro();
                int tiempoEnParqueadero = c.darTiempoEnParqueadero(horaActual);
                totalTiempo += tiempoEnParqueadero;
                totalCarros++;
            }
        }
        if (totalCarros == 0) {
            return 0; // No hay carros en el parqueadero
        }
        return (double) totalTiempo / totalCarros;
    }
    public carro carroMasTiempoEnParqueadero() {
        carro carroMasTiempo = null;
        int maxTiempo = 0;
        for (puesto p : puestos) {
            if (p.estaOcupado()) {
                carro c = p.darCarro();
                int tiempoEnParqueadero = c.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > maxTiempo) {
                    maxTiempo = tiempoEnParqueadero;
                    carroMasTiempo = c;
                }
            }
        }
        return carroMasTiempo;
    }

    public boolean hayCarroMasDeOchoHoras() {
        carro carroMasTiempo = carroMasTiempoEnParqueadero();
        if (carroMasTiempo == null) {
            return false;
        }
        int tiempoEnParqueadero = carroMasTiempo.darTiempoEnParqueadero(horaActual);
        return tiempoEnParqueadero > 8;
    }

    public ArrayList<carro> darCarrosMasDeTresHorasParqueados() {
        ArrayList<carro> carrosMasDeTresHoras = new ArrayList<>();
        for (puesto p : puestos) {
            if (p.estaOcupado()) {
                carro c = p.darCarro();
                int tiempoEnParqueadero = c.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > 3) {
                    carrosMasDeTresHoras.add(c);
                }
            }
        }
        return carrosMasDeTresHoras;
    }

    public boolean hayCarrosPlacaIgual() {
        for (puesto p1 : puestos) {
            if (p1.estaOcupado()) {
                carro c1 = p1.darCarro();
                for (puesto p2 : puestos) {
                    if (p2!= p1 && p2.estaOcupado()) {
                        carro c2 = p2.darCarro();
                        if (c1.darPlaca().equals(c2.darPlaca())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int contarCarrosQueComienzanConPlacaPB() {
        int contador = 0;
        for (puesto p : puestos) {
            if (p.estaOcupado()) {
                carro c = p.darCarro();
                String placa = c.darPlaca();
                if (placa.startsWith("PB")) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public boolean hayCarroCon24Horas() {
        for (puesto p : puestos) {
            if (p.estaOcupado()) {
                carro c = p.darCarro();
                int tiempoEnParqueadero = c.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero >= 24) {
                    return true;
                }
            }
        }
        return false;
    }

    public int desocuparParqueadero() {
        int cantidadCarrosSacados = 0;
        for (puesto p : puestos) {
            if (p.estaOcupado()) {
                p.sacarCarro();
                cantidadCarrosSacados++;
            }
        }
        return cantidadCarrosSacados;
    }



}
