package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		
		String sql = "select * from corso where pd = ?";
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
		
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		String sql = "select c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot " + 
				"from corso as c, iscrizione i " + 
				"where c.codins = i.codins and c.pd = ? " + 
				"group by c.codins, c.nome, c.crediti, c.pd ";
		Map<Corso, Integer> result = new HashMap<Corso,Integer>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot");
				result.put(c, n);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	public Map<Studente, String> getIscrittiByCodiceCorso(String codice) {
		String sql = "SELECT i.codins, s.matricola , s.cognome , s.nome , s.CDS " +
				" FROM iscrizione AS i, studente AS s" +
				" WHERE i.matricola=s.matricola AND i.codins = ? "+
				" GROUP BY i.codins, s.matricola , s.cognome , s.nome , s.CDS " ;
		Map<Studente, String> result = new HashMap<Studente,String>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codice);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("CDS"));
				
				result.put(s, ""+s.getMatricola());
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	

}
