# 🚦 Simulação de Semáforo com Threads  

Este projeto implementa uma simulação de semáforo utilizando threads em Java. Ele demonstra conceitos como sincronização de threads, uso de locks e comunicação entre threads.

---

## 📂 Estrutura do Projeto  

```  
Semaforo/  
│─── src/  
│    ├── Main.java            # Arquivo principal para execução do programa  
│    ├── model/  
│    │    ├── Semaforo.java   # Classe que implementa a lógica do semáforo  
│─── README.md                # Documentação do projeto  
│─── .gitignore               # Arquivos e pastas ignorados pelo Git  
```  

---

## 📝 Descrição dos Arquivos  

- **`Main.java`**: Contém o ponto de entrada do programa. Inicializa o semáforo e gerencia o encerramento do programa.  
- **`Semaforo.java`**: Implementa a lógica do semáforo, incluindo o ciclo de luzes (vermelho, amarelo, verde), exibição do estado atual e contagem regressiva.  

---

## ⚙️ Funcionalidades  

- Simulação de um semáforo com três estados: vermelho, amarelo e verde.  
- Exibição visual do estado atual do semáforo no console.  
- Contagem regressiva para a troca de estados.  
- Encerramento seguro do programa ao pressionar `Ctrl+C`.  

---

## 🛠️ Tecnologias Utilizadas  

- **Java** - Linguagem de programação principal.  
- **IntelliJ IDEA** - Ambiente de desenvolvimento utilizado.  
- **Git** - Controle de versão para gerenciamento do código-fonte.  

---

## 🎯 Conceitos Utilizados  

- **Threads**: Para gerenciar o ciclo do semáforo, exibição do estado e contagem regressiva.  
- **Locks e Sincronização**: Para garantir consistência entre as threads.  
- **Comunicação entre Threads**: Uso de `wait` e `notify` para sincronizar mudanças de estado.  

---

## 🚀 Como Executar  

1. Certifique-se de ter o **Java** instalado em sua máquina.  
2. Clone o repositório:  
   ```bash
   git clone https://github.com/JoaoAzevedo184/Semaforo.git
   ```  
3. Navegue até o diretório do projeto:  
   ```bash
   cd Semaforo
   ```  
4. Compile os arquivos Java:  
   ```bash
   javac src/**/*.java
   ```  
5. Execute o programa:  
   ```bash
   java src.Main
   ```  
6. Pressione `Ctrl+C` para encerrar o programa.  

---

## 🤝 Contribuição  

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.  

---

## 👤 Autor  

Desenvolvido por João Azevedo. Para mais informações, entre em contato:  
- **Gmail**: jonoffice37@gmail.com  
- **LinkedIn**: [João Victor Azevedo](https://www.linkedin.com/in/joao-victor-azevedo-181-sena)  
- **GitHub**: [JoaoAzevedo184](https://github.com/JoaoAzevedo184)