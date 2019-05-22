package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	public Graph<Author, DefaultEdge> grafo;
	private Map<Integer, Author> mapAuthor;
	public List<Author> listAuthor;
	PortoDAO dao = new PortoDAO();
	
	public void createGraph() {
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		//this.listAuthor= new ArrayList<Author>();
		this.mapAuthor= new HashMap<Integer,Author>();
		
		
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
				if(au!=null) {
					
					this.grafo.addEdge(aut, this.mapAuthor.get(au));
				}
			}
		}
		System.out.println("Grafo creato");
	}
	public List<Author> cercaCoautori(Author autore){
		List<Author> risultati= new ArrayList();
		risultati.addAll(Graphs.neighborListOf(this.grafo, autore));
		return risultati ;
	}
	public Collection<Author> getAuthor() {
		List<Author> res= new ArrayList();
		for(Author a : this.mapAuthor.values()) {
			res.add(a);
			Collections.sort(res);
		}
		return res;
	}
	public Collection<Author> cercaNONcoautori(Author autore){
		List<Author> res= new ArrayList();
		for(Author a :this.mapAuthor.values()) {
			if(!this.cercaCoautori(autore).contains(a)) {
				res.add(a);
				
			}
		}
		Collections.sort(res);
		return res;
	}
	public List<Paper> trovaCamminoMinimo(Author a1, Author a2){
		DijkstraShortestPath<Author, DefaultEdge> dijstra = new DijkstraShortestPath<>(this.grafo) ;
		GraphPath<Author, DefaultEdge> path = dijstra.getPath(a1, a2) ;
		List<Paper> articoli= new ArrayList<Paper>();
		for(int i=1; i<path.getVertexList().size(); i++) {
			Author aut1= path.getVertexList().get(i);
			Author aut2= path.getVertexList().get(i-1);
			Paper p= dao.getArticolo(aut1, aut2);
			articoli.add(p);
		}
		return articoli;
	}
	
}
