package SPOJ;

import java.util.*;

public class Exercicio3 {
    private static class No <Tipo> {
	private No ant;
	private Tipo conteudo;
	private No prox;
	
	public No(){
		setProx(null);
	}

	public Tipo getConteudo() {
		return conteudo;
	}

	public void setConteudo(Tipo conteudo) {
		this.conteudo = conteudo;
	}

	public No getProx() {
		return prox;
	}

	public void setProx(No prox) {
		this.prox = prox;
	}

	public No getAnt() {
		return ant;
	}

	public void setAnt(No ant) {
		this.ant = ant;
	}

    }
    
    private static class LDE <Tipo> {

	
	
	private No<Tipo> inicio;
	private No<Tipo> fim;
	private int tamanho;
	
	public LDE(){
		inicio = null;
		fim = null;		
		tamanho = 0;
	}
	
	/** Verifica se a Lista está vazia */
	public boolean vazia() {
	    if (tamanho == 0)
	        return true;
	    else
	        return false;
	}

	/**Obtém o tamanho da Lista*/
	public int tamanho() {
	    return tamanho;
	}

	/** Obtém o i-ésimo elemento de uma lista
	    Retorna o valor encontrado. */
	public Tipo elemento (int pos) {
	    No aux = inicio;
	    int cont = 1;

	    if (vazia()) {
	        return null; // Consulta falhou 
	    }

	    if ((pos < 1) || (pos > tamanho())){
	        return null; // Posicao invalida 
	    }

	    // Percorre a lista do 1o elemento até pos 
	    while (cont < pos){
	        // modifica "aux" para apontar para o proximo elemento da lista 
	        aux = aux.getProx();
	        cont++;
	    }

	    return (Tipo) aux.getConteudo();
	}

	/**Retorna a posição de um elemento pesquisado.
	    Retorna 0 caso não seja encontrado */
	public int posicao (Tipo dado) {
	    int cont = 1;
	    No aux;

	    /* Lista vazia */
	    if (vazia()) {
	        return -1;
	    }

	    /* Percorre a lista do inicio ao fim até encontrar o elemento*/
	    aux = inicio;
		while (aux != null) {
	        /* Se encontrar o elemento, retorna sua posicao n;*/
	        if (aux.getConteudo() == dado){
	            return cont;
	        }

	        /* modifica "aux" para apontar para o proximo elemento da lista */
	        aux = aux.getProx();
	        cont++;
	    }

	    return -1;
	}

	/** Insere nó em lista vazia */
	public boolean insereInicioLista(Tipo valor) {
		// Aloca memoria para novo no 
	    No novoNo = new No();
	    
	    // Insere novo elemento na cabeca da lista
	    novoNo.setConteudo(valor);
	    novoNo.setProx(inicio);
	    
	    novoNo.setAnt(null); // Nova instrucao
	    if (vazia())
    			fim = novoNo; // Nova instrucao
	    else
    			inicio.setAnt(novoNo); // Nova instrucao	    
	    
	    inicio = novoNo;
	    tamanho++;
	    return true;
	}

	/** Insere nó no meio da lista */
	private boolean insereMeioLista(int pos, Tipo dado){
	    int cont = 1;

	    // Aloca memoria para novo no
	    No novoNo = new No();
	    novoNo.setConteudo(dado);

	    // Localiza a pos. onde será inserido o novo nó
	    No aux = inicio;
	    while ((cont < pos-1) && (aux != null)){
	          aux = aux.getProx();
	          cont++;
	    }

	    if (aux == null) {  // pos. inválida 
	    		return false;
	    }

	    // Insere novo elemento apos aux
	    novoNo.setAnt(aux); // Nova instrucao
	    novoNo.setProx(aux.getProx());
	    
	    aux.getProx().setAnt(novoNo); // Nova instrucao
	    
	    aux.setProx(novoNo);

	    tamanho++;
	    return true;
	}

	/** Insere nó no fim da lista */
	public boolean insereFimLista(Tipo dado){
	    // Aloca memoria para novo no 
	    No novoNo = new No();
	    novoNo.setConteudo(dado);

	    // Procura o final da lista 
	    No aux = inicio;
	    while(aux.getProx() != null){
	        aux = aux.getProx();
	    }

	    novoNo.setProx(null);
	    aux.setProx(novoNo);
	    
	    novoNo.setAnt(fim);  // Nova instrucao
	    fim.setProx(novoNo); // Nova instrucao
	    fim = novoNo; 		// Nova instrucao
	    
	    this.tamanho++;
	    return true;
	}

	/**Insere um elemento em uma determinada posição
	    Retorna true se conseguir inserir e 
	    false caso contrario */
	public boolean insere(int pos, Tipo dado) {
		if ((vazia()) && (pos != 1)){
	        return false; /* lista vazia mas posicao inv*/
	    }

	 	/* inserção no início da lista (ou lista vazia)*/
	    if (pos == 1){
	        return insereInicioLista(dado);
	    }
	    /* inserção no fim da lista */
	    else if (pos == tamanho+1){
	        return insereFimLista(dado);
	   }
	   /* inserção no meio da lista */
	   else{
	        return insereMeioLista(pos, dado);
	   }
	}

	// Remove elemento do início de uma lista unitária
	private Tipo removeInicioListaUnitaria(){          
	    Tipo dado = inicio.getConteudo();
	    inicio = null;
	    fim = null; 
	    tamanho--;
	    return dado;
	}
	
	/** Remove elemento do início da lista */
	private Tipo removeInicioLista(){
	    No p = inicio;

	    // Dado recebe o dado removido
	    Tipo dado = (Tipo) p.getConteudo();

	    // Retira o 1o elemento da lista (p)
	    inicio = p.getProx();
	    p.getProx().setAnt(null);  // Nova instrucao
	    
	    tamanho--;

	    // Sugere ao garbage collector que libere a memoria
	    //  da regiao apontada por p
	    p = null;

	    return dado;
	}

	/** Remove elemento no meio da lista */
	private Tipo removeMeioLista(int pos){
	     No p = inicio;
	     int n = 1;
	     
	     // Localiza o nó que será removido
	     while((n <= pos-1) && (p != null)){ 
	    	 	p = p.getProx();
	        n++;
	     }
	     
	     if (p == null) {
	    	 	return null; // pos. inválida
	     }
	     
	    	 Tipo dado = (Tipo) p.getConteudo();
	    	 p.getAnt().setProx(p.getProx());
	    	 p.getProx().setAnt(p.getAnt());
			 
	     tamanho--;
	     
	     /* sugere ao garbage collector que libere a memoria
	     *  da regiao apontada por p*/
	    p = null;
	    return dado;
	}
	
	/** Remove elemento do início da lista */
	private Tipo removeFimLista(){          
	     No p = fim;
	     Tipo dado = (Tipo) p.getConteudo();
	     
	     fim.getAnt().setProx(null);
	     fim = fim.getAnt();
	     tamanho--;
	     
	     p = null; 
	     return dado;
	}

	
	/**Remove um elemento de uma determinada posição
	    Retorna o valor a ser removido. 
	    -1 se a posição for inválida ou a lista estiver vazia*/
	public Tipo remove(int pos) {
		// Lista vazia 
	    if (vazia()) {
                return null;
	    }

	    // Remoção do elemento da cabeça da lista 
	    if ((pos == 1) && (tamanho() == 1)){ 
	 	return removeInicioListaUnitaria();
	    }
	    else if (pos == 1){
	        return removeInicioLista();
	    }
	    // Remocao no fim da lista
	    else if (pos == tamanho()){ 
	 		 return removeFimLista();
	    }
	    // Remoção em outro lugar da lista
	    else{
	        return removeMeioLista(pos);
	    }
	}
    }
    
    public static void main(String[] arguments){
        Scanner scan = new Scanner(System.in);
        String com_espacos;
        String[] sem_espacos;
        LDE<int[]> posicoes = new LDE();
        int[] numeros_decimais = new int[3];
        
        No<String> ptr1 = new No();
        No<String> ptr2 = new No();
        
        boolean sair = false;
        
        com_espacos = scan.nextLine();
        sem_espacos = com_espacos.split(" ");
        
        
        
        
        while(!sair){
            
            
        }
    }
}
