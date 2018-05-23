package com.peruviansy.service.Impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.peruviansy.dao.IPersonaDAO;
import com.peruviansy.dao.impl.PersonaDAOImpl;
import com.peruviansy.model.Persona;
import com.peruviansy.service.IPersonaService;

@Named
//@RequestScoped
public class PersonaServiceImpl implements IPersonaService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Inject
	@EJB
	private IPersonaDAO dao;
	
	/*public PersonaServiceImpl() {
		dao=new PersonaDAOImpl();
	}*/
	
	
	@Override
	public void registrar(Persona per,String url) throws Exception {
		// llamamos a la capa DAO,
		dao.registrar(per,url);
	}


	@Override
	public List<Persona> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}
	
	@Override
	public Persona ListarPorId(Persona t) throws Exception {
		// TODO Auto-generated method stub
	
 		return dao.ListarPorId(t);
	}


	@Override
	public void modificar(Persona t) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Persona> listarxPersona(Persona t,LocalDate inicio,LocalDate fin) throws Exception {
		// TODO Auto-generated method stub
		return dao.listarxPersona(t,inicio,fin);
	}
    
	
}
