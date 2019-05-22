package it.polito.tdp.porto.db;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		System.out.println(pd.getAutore()+"\n");
		//System.out.println(pd.getArticolo(2293546));
		//System.out.println(pd.getArticolo(1941144));
		System.out.println(pd.getAutoriConnessi(85)+"\n");

	}

}
