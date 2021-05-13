package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {
	
	private Partida partida;
	
	

	public Rei(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
}

	
	@Override
	public String toString() {
		return "♔";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();	
		
	}

	
	private boolean torreRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorMovimento() == 0;
	}
	
	@Override
	public boolean[][] movimentosPossiveis()  {
		
			boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
			
			Posicao p = new Posicao(0,0);
			
			//acima
			p.setValores(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//abaixo
			p.setValores(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
				
			//esquerda
			p.setValores(posicao.getLinha(), posicao.getColuna() -1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//direita
			p.setValores(posicao.getLinha(), posicao.getColuna() +1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			//noroeste 
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			
			//nordeste 
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			//sudoeste 
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() -1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			
			//sudeste 
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;	
			}
			
			//Movimento Especial Roque
			if (getContadorMovimento() == 0 && !partida.getXeque()) {
				//Lado do Rei
				Posicao pTorre1 = new Posicao(posicao.getLinha(),posicao.getColuna() + 3);
					if (torreRoque(pTorre1)) {
						Posicao p1 = new Posicao(posicao.getLinha(),posicao.getColuna() + 1);
						Posicao p2 = new Posicao(posicao.getLinha(),posicao.getColuna() + 2);
						if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
							mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
						}
					}
				//Lado do Rainha
				Posicao pTorre2 = new Posicao(posicao.getLinha(),posicao.getColuna() - 4);
					if (torreRoque(pTorre2)) {
							Posicao p1 = new Posicao(posicao.getLinha(),posicao.getColuna() - 1);
							Posicao p2 = new Posicao(posicao.getLinha(),posicao.getColuna() - 2 );
							Posicao p3 = new Posicao(posicao.getLinha(),posicao.getColuna() - 3 );
							if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
								mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
							}
						}
			
			}
			
		return mat;
			}
			

}
