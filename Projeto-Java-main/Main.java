
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

	static Contrato ct;
	static int qtdParcelas;
	private static List<Parcela> parcelas;
	private static List<Parcela> parcelas_2;
	private static List<Parcela> parcelas_3;
	private static double juros ;
	private static double juros2;
	private static double juros3;
    public static final int DECEMBER = 11;

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");

		Scanner entrada = new Scanner(System.in);

		/* Dados do Contrato */
		System.out.println("Informe os dados do contrato:");

		System.out.print("Número: ");
		Integer numero = entrada.nextInt();

		System.out.print("Data (dd/MM/yyyy): ");
		Date data = date_format.parse(entrada.next());

		System.out.print("Valor do contrato: ");
		Double total = entrada.nextDouble();

		/* Instancia o contrato */
		Contrato ct = new Contrato(numero, data, total);

		/* solicita o n�mero de parcelas */
		System.out.print("Informe a quantidade de parcelas: ");
		int qtdParcelas = entrada.nextInt();

		System.out.println("--------------------------------");
		System.out.println(
				"\nEscolha uma forma de pagamento: \n 1- PayPal \n 2- Mercado Pago \n 3- PagSeguro \n 4- Banco com a menor taxa de juros");
		int opc = entrada.nextInt();
		entrada.close();

		if (opc == 1) {
			ControllerContrato controller;
			if (data.getMonth() == 11) {
				controller = new ControllerContrato(new PayPalPromocao());
				System.out.println("--------------------------------------");
				System.out.println("\nPromoção! 10% de desconto no mês de dezembro!!!");
			} else {
				controller = new ControllerContrato(new PayPal());
			}
			parcelas = controller.calcularContrato(ct, qtdParcelas);
			System.out.println("----------");
			System.out.println("PARCELAS - PAYPAL");
			for (Parcela p : parcelas) {
				System.out.println(date_format.format(p.getDataP()) + " - " + String.format("%.2f", p.getValorP()));
			}
			juros = controller.calculaTotalJuros(parcelas, total);
			System.out.println("---- Total Juros = " + String.format("%.2f", juros));

		}

		else {
			ControllerContrato controller;
			if (opc == 2) {
				controller = new ControllerContrato(new MercadoPago());
				parcelas_2 = controller.calcularContrato(ct, qtdParcelas);

				/* Imprime as parcelas */
				System.out.println("--------------------------------");
				System.out.println("\n\nPARCELAS - MERCADO PAGO");
				for (Parcela p : parcelas_2) {
					System.out.println(date_format.format(p.getDataP()) + " - " + String.format("%.2f", p.getValorP()));
				}
				juros2 = controller.calculaTotalJuros(parcelas_2, total);
				System.out.println("---- Total Juros = " + String.format("%.2f", juros2));

			} else {
				if (opc == 3) {
					controller = new ControllerContrato(new PagSeguro());
					parcelas_3 = controller.calcularContrato(ct, qtdParcelas);

					/* Imprime as parcelas */
					System.out.println("-----------------------------------");
					System.out.println("\n\nPARCELAS - PagSeguro");
					for (Parcela p : parcelas_3) {
						System.out.println(
								date_format.format(p.getDataP()) + " - " + String.format("%.2f", p.getValorP()));
					}
					juros3 = controller.calculaTotalJuros(parcelas_3, total);
					System.out.println("---- Total Juros = " + String.format("%.2f", juros3));

				}
				if (opc == 4) {

					
					if (juros < juros2 && juros < juros3) {
					
						controller = new ControllerContrato(new PayPal());
		 	           juros = controller.calculaTotalJuros(parcelas, total);
			           System.out.println("---- Total Juros = " + String.format("%.2f", juros));

						System.out.println("------------------------------------");
						System.out.println("O Banco com a menor taxa de juros é: PayPal " + juros);
					} 
			    	else {
					
						controller = new ControllerContrato(new MercadoPago());
					  System.out.println("---- Total Juros = " + String.format("%.2f", juros2));
							System.out.println("------------------------------------");
							System.out.println("O Banco com a menor taxa de juros é: Mercado Pago " + juros2);
						}
					}

					else if(juros3 < juros && juros3 < juros2){
						controller = new ControllerContrato(new PagSeguro());
		 	           juros = controller.calculaTotalJuros(parcelas_3, total);
			           System.out.println("---- Total Juros = " + String.format("%.2f", juros3));
						System.out.println("------------------------------------");
						System.out.println("O banco com a menor taxa de juros é: PagSeguro " + juros3);
					}
				}

				}
			
			entrada.close();
		}
	}
		

	


