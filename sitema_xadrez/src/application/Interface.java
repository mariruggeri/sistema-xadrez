package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class Interface {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

		public static final String ANSI_RESET = "\u001B[0m";
		public static final String ANSI_BLACK = "\u001B[30m";
		public static final String ANSI_RED = "\u001B[31m";
		public static final String ANSI_GREEN = "\u001B[32m";
		public static final String ANSI_YELLOW = "\u001B[33m";
		public static final String ANSI_BLUE = "\u001B[34m";
		public static final String ANSI_PURPLE = "\u001B[35m";
		public static final String ANSI_CYAN = "\u001B[36m";
		public static final String ANSI_WHITE = "\u001B[37m";

		public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
		public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
		public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
		public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
		public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
		public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
		public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
		public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
		// https://stackoverflow.com/questions/2979383/java-clear-the-console
		
		public static void clearScreen() {
		//	System.out.print("\033[H\033[2J");
			System.out.flush();
		}		
		
		
		
		
	public static XadrezPosicao leiaXadrezPosicao(Scanner sc) {
		
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new XadrezPosicao(coluna, linha);
		}
		
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro leitura de posição. Valores válidos a1 - h8");
		}
		
	}
	
	public static void imprimePartida(Partida partida, List<PecaDeXadrez> capturada) {
		imprimeTabuleiro(partida.getPecas());
		System.out.println();
		imprimePecasCapturadas(capturada);
		System.out.println();
		System.out.println("○ Número da Rodada: __ " + partida.getVez());
		
		if (!partida.getXequeMate()) {
			
			System.out.println("○ Aguardando Jogador: __" + partida.getJogadorAtual());
			System.out.println();
			
				if (partida.getXeque()) {
					System.out.println("Partida em Xeque!");
				}
		}
		else {
			System.out.println("Xeque Mate!");
			System.out.println("Vencedor : " + partida.getJogadorAtual());

		}
	}
	
		
	public static void imprimeTabuleiro(PecaDeXadrez[][] pecas) {
		
		for (int i=0; i< pecas.length; i++) {
			System.out.print((8 - i) + " ");
	
				for (int j=0; j< pecas.length; j++) {
					imprimePeca(pecas[i][j], false);
				}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	public static void imprimeTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		
		for (int i=0; i< pecas.length; i++) {
			System.out.print((8 - i) + " ");
	
				for (int j=0; j< pecas.length; j++) {
					imprimePeca(pecas[i][j], movimentosPossiveis[i][j]);
				}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	private static void imprimePeca(PecaDeXadrez peca, boolean fundo) {
		
		if (fundo) {
			System.out.print("'");
		}
		
		if (peca == null) {
			System.out.print("_");
		}
		else {
			
			if (peca.getCor() == Cor.BRANCO){
				System.out.print( peca );
			} 
			else {
				System.out.print( peca );
			}

		}
		System.out.print(" ");
	}
	
	private static void imprimePecasCapturadas(List<PecaDeXadrez> capturada){
		List<PecaDeXadrez> branca = capturada.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<PecaDeXadrez> preta = capturada.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());	
		System.out.println("Peças Capturadas : " );
		System.out.println("BRANCAS : " );
		System.out.println(Arrays.deepToString(branca.toArray()));
		System.out.println("-----------------------------------" );
		System.out.println("PRETAS : " );
		System.out.println(Arrays.deepToString(preta.toArray()));
	
		
	}


}
