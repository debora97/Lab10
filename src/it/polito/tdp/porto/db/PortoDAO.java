package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 *ottengo gli autori e li metto in una mappa.
	 */
	public List<Author> getAutore() {

		final String sql = "SELECT  a.id, a.lastname, a.firstname " + 
				"FROM author AS a ";
		List< Author> autori = new ArrayList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autori.add( autore);
				
			}
			
			conn.close();
			return autori;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(Author a1, Author a2) {

		final String sql = "SELECT  distinct p.eprintid, p.title, p.issn, p.publication, p.`type`, p.types " + 
				"FROM  creator AS c1, creator AS c2, paper AS p " + 
				"WHERE  c1.authorid=? AND c2.authorid=? and c1.eprintid=c2.eprintid AND p.eprintid=c1.eprintid ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	
	
	 public List<Integer> getAutoriConnessi(Integer idA) {

		final String sql = "SELECT DISTINCT a.id " + 
				"FROM  creator AS c1, creator AS c2, author AS a " + 
				"WHERE c1.eprintid=c2.eprintid   AND c1.authorid=? AND c1.authorid!=c2.authorid AND a.id=c2.authorid ";
		List< Integer> coautori = new ArrayList<Integer>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idA);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int  autId =  rs.getInt("id");
				
				
		
				coautori.add( autId);
				
			}
			conn.close();
			

			return coautori;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

}