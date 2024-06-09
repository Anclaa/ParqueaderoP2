package pkparqueadero;

public class carro {
    private String placa;
    private int hllegada;

    public carro(String pPlaca, int pHora){
        placa = pPlaca;
        hllegada = pHora;
    }

    public String darPlaca(){
        return placa;
    }

    public int darHoraLlegada(){
        return hllegada;
    }

    public boolean tienePlaca(String pPlaca){
        boolean tienePlaca = true;
        if(placa.equalsIgnoreCase(pPlaca)){
            tienePlaca=true;
        }else{
            tienePlaca=false;
        }
        return tienePlaca;
    }

    public int darTiempoEnParqueadero(int pHoraSalida){
        int tiempoParqueadero = pHoraSalida-hllegada+1;
        return tiempoParqueadero;
    }


}
