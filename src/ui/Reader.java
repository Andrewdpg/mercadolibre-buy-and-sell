package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.CategoryProduct;

public class Reader {

    private static Scanner reader = new Scanner(System.in);

    public static int readInt(int def) {
        if (reader.hasNextInt()) {
            return reader.nextInt();
        }
        reader.next();
        System.out.println("Entrada inválida. Valor tomado: " + def);
        return def;
    }

    public static int readInt(int def, int min) {
        if (reader.hasNextInt()) {
            int value = reader.nextInt();
            if (value >= min) {
                return value;
            }
            System.out.println("Valor por debajo del mínimo. Valor tomado: " + min);
            return min;
        }
        reader.next();
        System.out.println("Entrada inválida. Valor tomado: " + def);
        return def;
    }

    public static int readInt(int def, int min, int max) {
        if (reader.hasNextInt()) {
            int value = reader.nextInt();
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Valor por fuera del rango. Valor tomado: " + (max + min) / 2);
            return (max + min) / 2;
        }
        reader.next();
        System.out.println("Entrada inválida. Valor tomado: " + def);
        return def;
    }

    public static String readString() {
        return reader.next();
    }

    public static Double readDouble() {
        return reader.nextDouble();
    }

    // Lee un caracter
    public static char readChar(char def) {
        String value = reader.next();
        // En caso de no introducir nada, retorna un valor por defecto
        if (!value.isEmpty()) {
            return value.charAt(0);
        }
        return def;
    }

    public static int randInt(int start, int end) {
        if (end > start) {
            return (int) ((Math.random() * (1 + end - start)) + start);
        }
        return start;
    }

    public static int randInt(int end) {
        return (int) (Math.random() * (end + 1));
    }

    public static Object readValue(String atribute) throws ParseException {
        switch (atribute) {
            case "name":
                return readString();
            case "total price":
                return readDouble();
            case "date":
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateFormat.parse(readString());
                return date;
            case "id":
                return readString();
            case "price":
                return readDouble();
            case "quantity":
                return readInt(0, 0);
            case "category":
                return CategoryProduct.chooseCategory(readInt(0, 1, 8) - 1);
            default:
                break;
        }
        return null;
    }
}
