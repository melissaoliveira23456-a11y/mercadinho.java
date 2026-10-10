import java.util.ArrayList;
import java.util.Scanner;


public class MercadinhoJava {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Produto> produtos = new ArrayList<>();
    static int proximoCodigo = 1;

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n===== MERCADINHO JAVA =====");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto");
            System.out.println("4 - Registrar venda");
            System.out.println("5 - Relatorio de estoque");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    buscarProduto();
                    break;
                case 4:
                    registrarVenda();
                    break;
                case 5:
                    relatorioEstoque();
                    break;
                case 0:
                    System.out.println("Sistema encerrado!");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    static void cadastrarProduto() {
        System.out.println("\n--- Cadastro de produto ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("O nome nao pode ficar vazio.");
            return;
        }

        System.out.print("Preco: R$ ");
        double preco = lerPreco();

        if (preco <= 0) {
            System.out.println("O preco deve ser maior que zero.");
            return;
        }

        System.out.print("Quantidade em estoque: ");
        int estoque = lerInteiro();

        if (estoque < 0) {
            System.out.println("O estoque nao pode ser negativo.");
            return;
        }

        Produto produto = new Produto(
            proximoCodigo, nome, preco, estoque
        );

        produtos.add(produto);
        proximoCodigo++;

        System.out.println("Produto cadastrado!");
        System.out.println("Codigo: " + produto.codigo);
    }

    static void listarProdutos() {
        System.out.println("\n--- Lista de produtos ---");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    static void buscarProduto() {
        System.out.print("Digite o codigo: ");
        int codigo = lerInteiro();

        Produto produto = encontrarProduto(codigo);

        if (produto == null) {
            System.out.println("Produto nao encontrado.");
        } else {
            System.out.println(produto);
        }
    }

    static void registrarVenda() {
        System.out.print("Codigo do produto: ");
        int codigo = lerInteiro();

        Produto produto = encontrarProduto(codigo);

        if (produto == null) {
            System.out.println("Produto nao encontrado.");
            return;
        }

        System.out.print("Quantidade vendida: ");
        int quantidade = lerInteiro();

        if (quantidade <= 0) {
            System.out.println("Quantidade invalida.");
            return;
        }

        if (quantidade > produto.estoque) {
            System.out.println("Estoque insuficiente!");
            return;
        }

        produto.estoque -= quantidade;
        double total = produto.preco * quantidade;

        System.out.printf("Total da venda: R$ %.2f%n", total);
        System.out.println("Venda registrada!");
        System.out.println("Estoque restante: " + produto.estoque);
    }

    static void relatorioEstoque() {
        int unidades = 0;
        double valorTotal = 0;

        for (Produto produto : produtos) {
            unidades += produto.estoque;
            valorTotal += produto.preco * produto.estoque;
        }

        System.out.println("\n--- Relatorio de estoque ---");
        System.out.println("Tipos de produtos: " + produtos.size());
        System.out.println("Unidades em estoque: " + unidades);
        System.out.printf("Valor do estoque: R$ %.2f%n", valorTotal);
    }

    static Produto encontrarProduto(int codigo) {
        for (Produto produto : produtos) {
            if (produto.codigo == codigo) {
                return produto;
            }
        }

        return null;
    }

    static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Digite um numero inteiro valido.");
            return -1;
        }
    }

    static double lerPreco() {
        try {
            String entrada = scanner.nextLine()
                .trim().replace(",", ".");
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static class Produto {
        int codigo;
        String nome;
        double preco;
        int estoque;

        Produto(int codigo, String nome, double preco, int estoque) {
            this.codigo = codigo;
            this.nome = nome;
            this.preco = preco;
            this.estoque = estoque;
        }

        public String toString() {
            return String.format(
                "Codigo: %d | Produto: %s | Preco: R$ %.2f | Estoque: %d",
                codigo, nome, preco, estoque
            );
        }
    }
}

