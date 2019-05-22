package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	public Graph<Author, DefaultEdge> grafo;
	private Map<Integer, Author> mapAuthor;
	private List<Author> listAuthor;
	public void createGraph(int numeroLettere) {
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		//this.listAuthor= new ArrayList<Author>();
		this.mapAuthor= new HashMap<Integer,Author>();
		
		PortoDAO dao = new PortoDAO();
		this.listAuthor=new ArrayList<Author>(dao.getAutore());
		for(Author a: this.listAuthor) {
			this.mapAuthor.put(a.getId(), a);
		}
		//aggiungo tutti gli autori al 
		Graphs.addAllVertices(this.grafo, this.listAuthor);
		for(Author aut: this.grafo.vertexSet()) {
			// faccio una lista con tutti i coautori
			List<Integer> autoriConnessi= dao.getAutoriConnessi(aut.getId());
			
			//faccio il for per aggiungere tutti gli arichi a quell'autore (lista che ho fatto prima)
			for(Integer au: autoriConnessi) {
				this.grafo.addEdge(aut, this.mapAuthor.get(au));
			}
		

		
	}
System.err.println("Grafo creato");
}
}
