package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.Partida;
import xadrez.PecaDeXadrez;
import xadrez.XadrezExcecao;
import xadrez.XadrezPosicao;

public class Programa {
 
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		List<PecaDeXadrez> capturada = new ArrayList<>();
		
		while (!partida.getXequeMate()) {
		
			try {
			
				Interface.clearScreen();
						
				System.out.println();
				Interface.imprimePartida(partida, capturada);
				System.out.println();
				System.out.print("Origem: ► ");
				XadrezPosicao origem = Interface.leiaXadrezPosicao(sc);
				
				
				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				Interface.imprimeTabuleiro(partida.getPecas(), movimentosPossiveis);
				
				
				System.out.println();
				System.out.print("Destino: ► ");
				XadrezPosicao destino = Interface.leiaXadrezPosicao(sc);
				
				PecaDeXadrez pecaCapturada = partida.movimentoXadrez(origem, destino);
				if (pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
			
				if (partida.getPromovida() != null) {
					System.out.println("Peão promovido (B/C/R/T) ");
					String tipo = sc.nextLine();
					partida.trocaPomovida(tipo);
				}
			}
			
			catch(XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}	
		}
		Interface.imprimePartida(partida,capturada);
		
		
	}
}
