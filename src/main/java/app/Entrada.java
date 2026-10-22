package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Entrada {

    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final Scanner scanner = new Scanner(System.in);

    private Entrada() {
    }

    public static String texto(String mensagem) {
        String texto;
        do {
            System.out.print(mensagem);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Campo obrigatório.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    public static int inteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números inteiros.");
            }
        }
    }

    public static LocalDateTime dataHora(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return LocalDateTime.parse(scanner.nextLine().trim(), FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Exemplo: 10/10/2026 19:30");
            }
        }
    }
}
