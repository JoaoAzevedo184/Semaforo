package model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;

enum LuzSemaforo {
    VERMELHO, AMARELO, VERDE
}

public class Semaforo {
    private final Lock lock = new ReentrantLock();
    private LuzSemaforo luzAtual = LuzSemaforo.VERMELHO;
    private volatile boolean executando = true;
    private final Object estadoAlterado = new Object();

    public void iniciar() {
        // Cria e inicia as threads
        Thread threadCiclo = new Thread(this::cicloSemaforo);
        Thread threadExibicao = new Thread(this::exibirEstado);
        Thread threadContador = new Thread(this::contarTempo);

        threadCiclo.setDaemon(true);
        threadExibicao.setDaemon(true);
        threadContador.setDaemon(true);

        System.out.println("Simulação de Semáforo com Threads");
        System.out.println("Pressione Ctrl+C para encerrar o programa.");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        threadCiclo.start();
        threadExibicao.start();
        threadContador.start();

        // Aguarda indefinidamente (até que o usuário interrompa)
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            parar();
        }
    }

    private void mudarLuz(LuzSemaforo luz, int duracao) {
        lock.lock();
        try {
            luzAtual = luz;
            synchronized (estadoAlterado) {
                estadoAlterado.notifyAll();  // Notifica todas as threads que estão esperando
            }
        } finally {
            lock.unlock();
        }

        try {
            Thread.sleep(duracao * 1000L);  // Mantém esta luz ativa pelo tempo especificado
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void cicloSemaforo() {
        while (executando) {
            // Ciclo completo do semáforo
            mudarLuz(LuzSemaforo.VERMELHO, 5);
            if (!executando) break;

            mudarLuz(LuzSemaforo.AMARELO, 2);
            if (!executando) break;

            mudarLuz(LuzSemaforo.VERDE, 5);
            if (!executando) break;
        }
    }

    private void exibirEstado() {
        LuzSemaforo ultimoEstado = null;

        while (executando) {
            LuzSemaforo estadoAtual;

            lock.lock();
            try {
                estadoAtual = luzAtual;
            } finally {
                lock.unlock();
            }

            if (estadoAtual != ultimoEstado) {
                limparTela();
                System.out.println("\n\n");

                // Exibe o semáforo com a luz atual ativa
                System.out.println((estadoAtual == LuzSemaforo.VERMELHO ? "🔴" : "⚫") + " VERMELHO");
                System.out.println((estadoAtual == LuzSemaforo.AMARELO ? "🟡" : "⚫") + " AMARELO");
                System.out.println((estadoAtual == LuzSemaforo.VERDE ? "🟢" : "⚫") + " VERDE");

                // Mensagem baseada na luz atual
                System.out.println();
                if (estadoAtual == LuzSemaforo.VERMELHO) {
                    System.out.println("Pare! Sinal vermelho.");
                } else if (estadoAtual == LuzSemaforo.AMARELO) {
                    System.out.println("Atenção! Sinal vai mudar.");
                } else {
                    System.out.println("Siga em frente! Sinal verde.");
                }

                ultimoEstado = estadoAtual;
            }

            // Espera pela notificação de mudança de estado ou verifica a cada 0.5 segundos
            try {
                synchronized (estadoAlterado) {
                    estadoAlterado.wait(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void contarTempo() {
        while (executando) {
            LuzSemaforo estadoAtual;

            lock.lock();
            try {
                estadoAtual = luzAtual;
            } finally {
                lock.unlock();
            }

            // Define o tempo baseado na luz atual
            int tempoTotal = (estadoAtual == LuzSemaforo.VERMELHO || estadoAtual == LuzSemaforo.VERDE) ? 5 : 2;

            // Contagem regressiva
            for (int i = tempoTotal; i > 0; i--) {
                lock.lock();
                try {
                    // Verifica se o estado mudou durante a contagem
                    if (estadoAtual != luzAtual) {
                        break;
                    }
                } finally {
                    lock.unlock();
                }

                System.out.print("\rTempo restante: " + i + " segundos");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private void limparTela() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback para limpar a tela com linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void parar() {
        executando = false;
        synchronized (estadoAlterado) {
            estadoAlterado.notifyAll();  // Notifica todas as threads para garantir que não fiquem bloqueadas
        }
    }
}
