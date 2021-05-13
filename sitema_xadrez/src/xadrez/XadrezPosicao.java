package xadrez;

import tabuleiro.Posicao;

public class XadrezPosicao {
	
	private char coluna;
	private int linha;

	
	public XadrezPosicao(char coluna, int linha) {
		
		if ( coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro! Valor válido a1 até h8");
		}	
		this.linha = linha;
		this.coluna = coluna;
	}

	public int getLinha() {
		return linha;
	}

	public char getColuna() {
		return coluna;
	}

	protected Posicao toPosition() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static XadrezPosicao fromPosition(Posicao posicao) {
		return new XadrezPosicao((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha;
	}

	
	

	
	
}
