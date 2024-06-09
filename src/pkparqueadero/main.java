package pkparqueadero;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;


import javax.swing.*;

public class main {
    public static void main(String[] args){
        parqueadero parqueadero = new parqueadero();
        Scanner sc = new Scanner(System.in);
        String placa;
        int horaEntrada;
        int horas;
        int nuevaTarifa;
        System.out.println("Bienvenidos a el parqueadero");
        boolean b =  true;
        do{
            System.out.println("Menu\n1. Parquear auto\n2.Sacar auto\n3. Ingresos parqueadero\n4. Cantidad puestos disponibles"+
                    "\n5.Cambiar tarifa parqueadero\n6. Avanzar reloj parqueadero\n7. Mostrar Otros detalles\n0. Salir\nIngrese la opcion deseada: ");
            int op = sc.nextInt();
            switch(op){
                case 1:
                    System.out.print("Ingrese la placa del carro: ");
                    placa = sc.next();
                    System.out.print("Ingrese la hora de entrada (hora entre 6 y 21, solo valores enteros): ");
                    horaEntrada = sc.nextInt();
                    parqueadero.entrarCarro(placa, horaEntrada);
                    if(parqueadero.hayCarrosPlacaIgual()){
                        System.out.println("Hay carros con la misma placa: " + parqueadero.hayCarrosPlacaIgual());
                    }
                    break;
                case 2:
                    System.out.print("Ingrese la placa del carro: ");
                    placa = sc.next();
                    int resultado = parqueadero.sacarCarro(placa);
                    if (resultado>0) {
                        System.out.println("Carro con placa " + placa + " salió del parqueadero.");
                    } else {
                        System.out.println("No se encontró el carro con placa " + placa + ". Verifique la placa ingresada.");
                    }
                    break;
                case 3:
                    System.out.println("Ingresos del parqueadero: " + parqueadero.darMontoCaja()+"\n");
                    break;
                case 4:
                    System.out.println("Puestos disponibles: " + parqueadero.calcularPuestosLibres()+"\n");
                    break;
                case 5:
                    System.out.print("Ingrese la nueva tarifa por hora (solo valores enteros): ");
                    nuevaTarifa = sc.nextInt();
                    parqueadero.cambiarTarifa(nuevaTarifa);
                    break;
                case 6:
                    parqueadero.avanzarHora();
                    System.out.print("Se ha avanzado la hora\n");
                    break;
                case 7:
                    System.out.println(parqueadero.metodo1());
                    //System.out.println(parqueadero.metodo2());
                    System.out.println("El tiempo promedio de uso del parqueadero es "+parqueadero.dartiempoPromedio());
                    break;
                case 0:
                    System.out.println("Saliendo del programa");
                    b = false;
                    return;
            }
        }while(b==true);

    sc.close();
    }
}

