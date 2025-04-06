import model.Semaforo;

public class Main {
    public static void main(String[] args) {
        Semaforo semaforo = new Semaforo();

        // Adiciona um hook de desligamento para encerrar corretamente
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nPrograma encerrado pelo usuário.");
            semaforo.parar();
        }));

        semaforo.iniciar();
    }
}