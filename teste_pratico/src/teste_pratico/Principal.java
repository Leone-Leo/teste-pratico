package teste_pratico;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // Inserindo funcionários na lista
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), ""));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), ""));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Diretor"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Removendo funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // Imprimindo todos os funcionários
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        funcionarios.forEach(f -> extracted(formatter, f));
     // Aplicando aumento de salário
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal(1.1))));

     // Agrupando funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .filter(f -> f.getFuncao() != null && !f.getFuncao().isEmpty())
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprimindo funcionários agrupados por função
        funcionariosPorFuncao.forEach((funcao, funcionariosDaFuncao) -> {
            System.out.println("Função: " + funcao);
            funcionariosDaFuncao.forEach(f -> System.out.println("- " + f.getNome()));
            System.out.println();
        });

        // Imprimindo funcionários que fazem aniversário nos meses 10 e 12
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome()));

        // Imprimindo funcionário com maior idade
        Funcionario funcionarioMaisVelho = funcionarios.stream().max(Comparator.comparing(f -> f.getDataNascimento())).get();
        StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Funcionário com maior idade: ");
		stringBuilder.append(funcionarioMaisVelho.getNome());
		stringBuilder.append(" (");
		stringBuilder.append(LocalDate.now().getYear());
		System.out.println(stringBuilder.toString() - funcionarioMaisVelho.getDataNascimento().getYear() + " anos)");

        // Imprimindo funcionários ordenados por nome
        funcionarios.forEach(f -> System.out.println(f.getNome()));

        // Imprimindo total dos salários
        BigDecimal totalSalarios = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + totalSalarios.setScale(2, BigDecimal.ROUND_HALF_UP).toString().replace(".", ","));

        // Imprimindo quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> System.out.println(f.getNome() + " ganha " + f.getSalario().divide(salarioMinimo, BigDecimal.ROUND_HALF_UP) + " salários mínimos"));
    }

	@SuppressWarnings("deprecation")
	private static void extracted(DateTimeFormatter formatter, Funcionario f) {
		System.out.println("Nome: " + f.getNome());
		System.out.println("Data de Nascimento: " + f.getDataNascimento().format(formatter));
		System.out.println("Salário: " + f.getSalario().setScale(2, ROUND_HALF_UP).toString().replace(".", ","));
		System.out.println("Função: " + f.getFuncao());
		System.out.println();
	}
}

