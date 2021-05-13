package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {
	
	private Partida partida;

	public Peao(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "♙";
	}
	
	
	@Override
	public boolean[][] movimentosPossiveis() {

	boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
	
		if (getCor() == Cor.BRANCO) {
				p.setValores(posicao.getLinha() - 1, posicao.getColuna());
					if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
				p.setValores(posicao.getLinha() - 2, posicao.getColuna());
				Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
					if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2) && getContadorMovimento() == 0) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
					if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
				p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
					if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
				
				// PASSANTE BRANCO
					if (posicao.getLinha() == 3) {
						Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
						if (getTabuleiro().posicaoExistente(esquerda) 
								&& temPecaOponente(esquerda) 
								&& getTabuleiro().peca(esquerda) == partida.getpassanteVulneravel()) {
							mat[esquerda.getLinha() -1][esquerda.getColuna()] = true;		
						}
						Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
						if (getTabuleiro().posicaoExistente(direita) 
								&& temPecaOponente(direita) 
								&& getTabuleiro().peca(direita) == partida.getpassanteVulneravel()) {
							mat[direita.getLinha() - 1][direita.getColuna()] = true;		
						}
					}
					
			}
		
			else {
			
					p.setValores(posicao.getLinha() + 1, posicao.getColuna());
					if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
						}
					
					p.setValores(posicao.getLinha() + 2, posicao.getColuna());
						Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
						if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2) && getContadorMovimento() == 0) {
							mat[p.getLinha()][p.getColuna()] = true;
						}
				
					p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
						if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
						}
					p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
						if (getTabuleiro().posicaoExistente(p) && temPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
						}
				
				// PASSANTE PRETO
						if (posicao.getLinha() == 4) {
							Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
							if (getTabuleiro().posicaoExistente(esquerda) 
									&& temPecaOponente(esquerda) 
									&& getTabuleiro().peca(esquerda) == partida.getpassanteVulneravel()) {
								mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;		
							}
							Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
							if (getTabuleiro().posicaoExistente(direita) 
									&& temPecaOponente(direita) 
									&& getTabuleiro().peca(direita) == partida.getpassanteVulneravel()) {
								mat[direita.getLinha() + 1][direita.getColuna()] = true;		
							}
						}
			
			
			} // fecha o else
			
		return mat;

		}
		
}


