package app;

import model.Evento;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// leitura segura do teclado
public class Entrada {

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

    public static boolean confirmar(String mensagem) {
        while (true) {
            String resposta = texto(mensagem + " (S/N): ").toUpperCase();
            if (resposta.equals("S")) {
                return true;
            }
            if (resposta.equals("N")) {
                return false;
            }
            System.out.println("Responda com S ou N.");
        }
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
                return LocalDateTime.parse(scanner.nextLine().trim(), Evento.FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Exemplo: 10/10/2026 19:30");
            }
        }
    }
}
