package it.polito.tdp.corsi.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO dao;
	
	public Model() {
		dao = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		return dao.getCorsiByPeriodo(pd);
	}
	

	public Map<Corso,Integer> getIscrittiByPeriodo(Integer pd){
		return dao.getIscrittiByPeriodo(pd);
	}
	public Map<Studente,String> getIscrittiByCodiceCorso(String codice){
		return dao.getIscrittiByCodiceCorso(codice);
	}
}
