package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rainha extends PecaDeXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "♕";
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
		
				//Noroeste
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() -1);
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
					mat [p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() - 1);
				}
				
				if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
			
			//Nordeste
				p.setValores(posicao.getLinha() -1 , posicao.getColuna() + 1);
				while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
					mat [p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() - 1, p.getColuna() + 1);
				}
				
				if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
			
			//Sudeste
					p.setValores(posicao.getLinha() + 1 , posicao.getColuna() + 1);
					while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
						mat [p.getLinha()][p.getColuna()] = true;
						p.setValores(p.getLinha() - 1, p.getColuna() + 1);
					}
					
					if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
			
			
			//Sudoeste
					p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
					while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
						mat [p.getLinha()][p.getColuna()] = true;
						p.setValores(p.getLinha() + 1, p.getColuna() - 1);
					}
					
					if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}		
					
		return mat;
	
		
		
		
		
		
	}

}
