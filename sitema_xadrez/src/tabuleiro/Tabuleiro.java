package tabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("ERRO: Pelo Menos 1 Linha e 1 Coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if (!posicaoExistente(linha, coluna)) {
			throw new TabuleiroExcecao("Posição Não Está no Tabuleiro");
		}
		return pecas[linha][coluna];
		
	}
	
	public Peca peca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posição Não Está no Tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	
	public void lugarPeca(Peca peca,Posicao posicao) {
		if (temPeca(posicao)) {
			throw new TabuleiroExcecao("Já existe uma peça nessa posição" + posicao);
		}
		
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
		}
	
	
	public Peca removePeca(Posicao posicao) {
				if (!posicaoExistente(posicao)) {
					throw new TabuleiroExcecao("Posição Não Está no Tabuleiro");
				}
	
		if (peca(posicao) == null) {
			return null;
		}
	
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	

	private boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}
	
	
	public boolean temPeca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Posição Não Está no Tabuleiro");
		}
		return peca(posicao) != null;
	}
}
