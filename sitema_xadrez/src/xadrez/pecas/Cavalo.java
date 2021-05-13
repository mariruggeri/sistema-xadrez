package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "♘";
	}
		
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();	
		
	}
	
	
	@Override
	public boolean[][] movimentosPossiveis() {

		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		// 1) - 1, - 2
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// 2) - 2, - 1
		p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
			
		// 3) - 2, + 1
		p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// 4) - 1, +2
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		// 5) +1, +2
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		
		// 6) +2, +1
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		// 7) +2, -1 
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;	
		}
		
		
		// 8)  +1 , -2 
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2 );
		if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
		mat[p.getLinha()][p.getColuna()] = true;	
		}
		
					
	return mat;
		}

}
