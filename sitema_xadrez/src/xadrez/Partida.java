package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {
	
	
	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaDeXadrez passanteVulneravel;
	private PecaDeXadrez promovida;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	
	
	public Partida() {	
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		jogadorAtual = Cor.BRANCO;
		configuracaoInicial();

	}
		
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	
	public int getVez() {
		return vez;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
	}
	
	
	public PecaDeXadrez getpassanteVulneravel() {
		return passanteVulneravel;
	}
	
	public PecaDeXadrez getPromovida() {
		return promovida;
	}
	
	public boolean[][] movimentosPossiveis(XadrezPosicao origemPosicao) {
		Posicao posicao = origemPosicao.toPosition();
		validacaoOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	
	public PecaDeXadrez movimentoXadrez(XadrezPosicao origemPosicao, XadrezPosicao destinoPosicao ) {
		Posicao origem = origemPosicao.toPosition();
		Posicao destino = destinoPosicao.toPosition();
		validacaoOrigemPosicao(origem);
		validacaoDestinoPosicao(origem, destino);
		Peca pecaCapturada = movimento(origem, destino);
		
		if (testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino,pecaCapturada);
			throw new XadrezExcecao("Você não pode se colocar em Xeque, bebê!");
		}
		
		
		PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);
		
		// Especial Promocao
		promovida = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) ||
			(pecaMovida.getCor()) == Cor.PRETO && destino.getLinha() == 7) {
				promovida = (PecaDeXadrez)tabuleiro.peca(destino);
				promovida = trocaPomovida("Q"); 
			}
		}
		
		
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		
		
		if (testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {	
			proximaVez();	
		}
		
		
		// Movimento Passante
		
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2) ) {
			passanteVulneravel = pecaMovida;
		}
		else {
			passanteVulneravel = null;
		}
		
		return (PecaDeXadrez)pecaCapturada;
	}
	
	public PecaDeXadrez trocaPomovida(String tipo) {
		if (promovida == null ) {
			throw new IllegalStateException("Não há nada para promover");
		}
			   if (!tipo.equals("B") 
				&& !tipo.equals("C")
				&& !tipo.equals("T")
				&& !tipo.equals("R")) {
			throw new InvalidParameterException("Errado!");
		}
		Posicao pos = promovida.getXadrezPosicao().toPosition();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p) ;
		
		PecaDeXadrez newPeca = newPeca(tipo, promovida.getCor());
		tabuleiro.lugarPeca(newPeca, pos);
		
		return newPeca;
	}
	
	private PecaDeXadrez newPeca(String tipo, Cor cor) {
			if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
			if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
			if (tipo.equals("T")) return new Torre(tabuleiro, cor);
			return new Rainha(tabuleiro, cor);
	}



	
	private Peca movimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(origem);
		p.adicionaContadorMovimento();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
			
		// Movimento Roque Rei
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(origemTorre);
			tabuleiro.lugarPeca(torre, destinoTorre);
			torre.adicionaContadorMovimento();
		}
		
		// Movimento Roque Raina
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(origemTorre);
			tabuleiro.lugarPeca(torre, destinoTorre);
			torre.adicionaContadorMovimento();
		}
				
		// passante
		
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao (destino.getLinha() + 1, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao (destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}	
		}
		
		return pecaCapturada;
	}
	
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removePeca(destino);
		p.subtraiContadorMovimento();
		tabuleiro.lugarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	
		// Movimento Roque Rei
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(destinoTorre);
			tabuleiro.lugarPeca(torre, origemTorre);
			torre.subtraiContadorMovimento();
		}
				
		// Movimento Roque Raina
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre = (PecaDeXadrez)tabuleiro.removePeca(destinoTorre);
			tabuleiro.lugarPeca(torre, origemTorre);
			torre.subtraiContadorMovimento();
		}
		
		// passante
		
				if (p instanceof Peao) {
					if (origem.getColuna() != destino.getColuna() && pecaCapturada == passanteVulneravel) {
						PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removePeca(destino);
						
						
						Posicao peaoPosicao;
						if (p.getCor() == Cor.BRANCO) {
							peaoPosicao = new Posicao (3, destino.getColuna());
						}
						else {
							peaoPosicao = new Posicao (4, destino.getColuna());
						}
						tabuleiro.lugarPeca(peao, peaoPosicao);
						
					}	
				}
		
	}
	
	
	
	private void validacaoOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezExcecao("Não há peça na posição de origem");
		}
		
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A Peça Escolhida não é sua");
		}
		
		
		if (!tabuleiro.peca(posicao).algumMovimentoPossivel(posicao)) {
			throw new XadrezExcecao("Não há movimentos possíveis para a peça escolhida");
		}
	}
	
	
	private void validacaoDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentosPossives(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode ser movida para o destino solicitado");
		}
	}
	
	private void proximaVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	
	private Cor oponente(Cor cor) {
		return (cor == cor.BRANCO) ? cor.PRETO : cor.BRANCO;
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Não há REI " + cor + " no Tabuleiro");
	}
	
	
	private boolean testeXeque (Cor cor) {
		Posicao posicaoRei = rei(cor).getXadrezPosicao().toPosition();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean testeXequeMate (Cor cor) {
		if (!testeXeque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaDeXadrez)p).getXadrezPosicao().toPosition();
						Posicao destino = new Posicao(i,j);
						Peca pecaCapturada = movimento(origem, destino);
						boolean testeXeque = testeXeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	
	private void lugarNovaPeca( char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosition());
		pecasNoTabuleiro.add(peca);
	}
	
	private void configuracaoInicial() {
		
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		
		lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		

		lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		
		lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
		
	
	}
	
}
