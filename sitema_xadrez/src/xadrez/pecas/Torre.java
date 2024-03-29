package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "♖";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//acima
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
				p.setLinha(p.getLinha() - 1);
			}
			
			if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		
		//esquerda
			p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
			while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
				mat [p.getLinha()][p.getColuna()] = true;
				p.setColuna(p.getColuna() - 1);
			}
			
			if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
		
		//direita
				p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
					mat [p.getLinha()][p.getColuna()] = true;
					p.setColuna(p.getColuna() + 1);
				}
				
				if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		
		//a baixo
				p.setValores(posicao.getLinha() + 1, posicao.getColuna());
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
					mat [p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() + 1);
				}
				
				if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
						
		return mat;
	
	}
	
	
	
}
