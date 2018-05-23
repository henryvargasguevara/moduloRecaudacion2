package com.peruviansy.dao;

import java.util.List;

import javax.ejb.Local;

import com.peruviansy.model.Persona;

@Local //Remote
public interface IPersonaDAO extends IDAO<Persona> {
	
	//void registrar(Persona per,String url) throws Exception;
	//void modificar(Persona per) throws Exception;
   // List<Persona> listar() throws Exception;
}
