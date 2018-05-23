package com.peruviansy.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.peruviansy.dao.IPersonaDAO;
import com.peruviansy.model.Persona;

//@Named
//@RequestScoped
@Stateless
public class PersonaDAOImpl implements IPersonaDAO,Serializable {
	
	private EntityManagerFactory emf;
	//@PersistenceContext(unitName="PersonalPU")
	private EntityManager em;
	private List<Persona> lstPersonas;
	private int cont;
	
	

	public PersonaDAOImpl() {
		emf=Persistence.createEntityManagerFactory("PersonalPU");
		em=emf.createEntityManager();
		lstPersonas=new ArrayList<Persona>();
		cont=0;
		
	}
	
	/*@PostConstruct
	public void init() {
		emf=Persistence.createEntityManagerFactory("PersonalPU");
		em=emf.createEntityManager();
		lstPersonas=new ArrayList<Persona>();
		cont=0;
	}*/
	

	public void registrar(Persona per,String url)  throws Exception{
		// TODO Auto-generated method stub
		this.mostrarExcel(url);
		String nom=null;
		try {
			
			for(Persona per3 :lstPersonas)
			  {
				em.getTransaction().begin();
	           if(cont==0) {
	        
	        	     nom=per3.getNombre();
			      em.persist(per3);//PARA INSERT.....MERGE ES PARA ACTUALIZAR
	
			      
	           }else {
	        
				   em.merge(per3);
				nom=per3.getNombre();
			    }
	   		em.getTransaction().commit();
	                cont++;
		   
			 }
	 	 //FacesMessage msg=new FacesMessage("Un ARCHIVO almacendao..."+nom);
	         //FacesContext.getCurrentInstance().addMessage(null,msg);
		
			
		}catch(PersistenceException e) {
			if(em.getTransaction().isActive()) 
			{  
				em.getTransaction().rollback();
			}
			
			System.out.println(e.getMessage()+"    "+e.getCause());
			System.out.println(e.getLocalizedMessage());
			
		}finally {
		
		}
	}

	
	public void modificar(Persona per) throws Exception {
		// TODO Auto-generated method stub
		em.merge(per);
		
	}

	public List<Persona> listar() throws Exception 
	{
		List<Persona> lista=new ArrayList<Persona>();
		Query q=em.createQuery("FROM Persona p order by fecha asc");
		lista=(List<Persona>) q.getResultList();
		return lista;
	}
	
	public void mostrarExcel(String urll) throws IOException, EncryptedDocumentException, InvalidFormatException {
		Date fechaSeleccionada;
		//FacesMessage msg7=new FacesMessage("Empieza henry vargas");
 		//FacesContext.getCurrentInstance().addMessage(null,msg7);
		String nom=null;
		//paso 0. Definir una colección con nombres de las columnas a procesar
		//considera que esto lo puedes leer de un archivo de configuración,
		//input de usuario o cualquier otra fuente
		List<String> columnas = Arrays.asList("MONEDA","DEPENDENCIA","CONCEP","NUMERO","CODIGO","NOMBRE","IMPORTE","FECHA");
		//paso 1.
		Map<String,Integer> mapNombresColumnas = new TreeMap<String,Integer>();
		//paso 2.
		//número de fila donde están los nombres de celda
		//recuerda que POI está basado con índice 0
		//si tus nombres están en la fila 1, entonces deberías iniciar esta
		//variable con 0.
		final int filaNombresColumnas =0;
		//url representa el nombre del archivo excel a subir
	
		//FacesMessage msg8=new FacesMessage("Empieza grecia11111");
 		//FacesContext.getCurrentInstance().addMessage(null,msg8);
		//System.out.println("antes de abrir archivo con poi");
		File archivoExcel = new File(urll);
		//abrir el archivo con POI
		//System.out.println("ABRIR ARCHIVO CON POI");
		try 
		{
		//Workbook workbook = WorkbookFactory.create(archivoExcel);
	        FileInputStream fis = new FileInputStream(archivoExcel);	      
	        HSSFWorkbook workbook = new HSSFWorkbook(fis);
	    
		//System.out.println("se pudo archivo poi");
		//System.out.println("creaste excel woork book");
	        //FacesMessage msg9=new FacesMessage("Empieza henry vargas2222222");
 		//FacesContext.getCurrentInstance().addMessage(null,msg9);
		//ubicarse en la hoja donde vas a procesar
		//si es la primera hoja, debes indicar 0
		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
		//acceder a la fila con los nombres de las columnas
		Row row = (  sheet).getRow(filaNombresColumnas);
		//paso 3.
		//utilizando el poder de Java 8
		 Iterator<Cell> cellIterator = row.cellIterator();
	  
		while ( cellIterator.hasNext()) {	
			HSSFCell cell = (HSSFCell) cellIterator.next();
			  String valorCelda = cell.getStringCellValue().trim();
			   //System.out.println(valorCelda);
			  // System.out.println(cell.getColumnIndex());
			   if (!valorCelda.isEmpty()) 
			    {
			        mapNombresColumnas.put(valorCelda, cell.getColumnIndex());
			    }
		
		}
		//paso 4.
		//se asume que los valores para procesar se encuentran en la fila
		//siguiente a la fila donde están los nombres de las columnas
		int indiceDatos = filaNombresColumnas + 1;
		Row filaDatos = null;
		List<Persona> lstPersonas1=new ArrayList<Persona>();
		//recorrer todas las filas con datos
			System.out.println("VAS  ENTRAR A WHILE");
		while ((filaDatos = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(indiceDatos++)) != null) {
		    //se procesan solo las celdas en base a los "nombres" de esas columnas
		       //el resultado de mapNombresColumnas.get(col) es
		       //el número de columna a leer
		       //en este caso, solo se imprime el resultado
		       //puedes reemplazar esto por la manera en que debas procesar la información
			//System.out.println("LLEGASTE A WHILE");
			 
			   Persona pers=new Persona();
			   LocalDate ff;
			 if(!((filaDatos.getCell(mapNombresColumnas.get("MONEDA"))).toString()).equalsIgnoreCase("")) {  
			   pers.setUrl(urll);
			   String monedaa=(((filaDatos.getCell(mapNombresColumnas.get("MONEDA"))).toString()) );
			  // System.out.println("***"+monedaa);
				
			   pers.setMoneda(monedaa.substring(0,3));
			   //System.out.println(monedaa.substring(0,3));
			   pers.setDependencia(filaDatos.getCell(mapNombresColumnas.get("DEPENDENCIA"))+"");
			   String concep=(filaDatos.getCell(mapNombresColumnas.get("CONCEP"))+"");
			   //System.out.println(concep);
			   pers.setConcepto(concep.substring(0,6)); 
			   String nume=filaDatos.getCell(mapNombresColumnas.get("NUMERO"))+"";
               String numeroo="";
			   
			   if(nume.substring(1,2).equals(".") && nume.length()==3) {
				   
			   }
			   else if(numeroo.length()==9)
                      {   
				       numeroo=nume.substring(0,7);
				      }
				   else  if(nume.length()==11)
                         {   
				           numeroo=nume.substring(0,1)+nume.substring(2,9);
				         }
				         else if(nume.length()==10) 
				              {
				        	    numeroo=nume.substring(0,1)+nume.substring(2,8);
				              }
				              else if(nume.length()==7) 
				                   {
				            	    numeroo=nume.substring(0,5);
				                   }
				                  else if(nume.length()==8) {
				                	  numeroo=nume.substring(0,6);
				                  }
			   pers.setNumero(numeroo);
			   String codi=filaDatos.getCell(mapNombresColumnas.get("CODIGO"))+"";
			   String codigoo="";
			   
			   if(codi.substring(1,2).equals(".")&&codi.length()==3) {
			
				  // pers.setCodigo();
			   }
			   else if(codi.length()==9)
                      {   
				       codigoo=codi.substring(0,7); 
				      }
				   else  if(codi.length()==11)
                         {   
				           codigoo=codi.substring(0,1)+codi.substring(2,9);
				         }
				         else if(codi.length()==10) 
				              {
				        	   codigoo=codi.substring(0,1)+codi.substring(2,8);
				              }
				              else if(codi.length()==7) 
				                   {
				            	    codigoo=codi.substring(0,5);
				                   }
				                  else if(codi.length()==8) {
				                	  codigoo=codi.substring(0,6);
				                  }

			   pers.setCodigo(codigoo);
			   nom=filaDatos.getCell(mapNombresColumnas.get("NOMBRE"))+"";
			   pers.setNombre(filaDatos.getCell(mapNombresColumnas.get("NOMBRE"))+"");
			   pers.setImporte(  Double.parseDouble((filaDatos.getCell(mapNombresColumnas.get("IMPORTE")).toString())));
			   String fechaa=urll;
			   
               ff=LocalDate.of(Integer.parseInt("20"+fechaa.substring(22,24)),Integer.parseInt(fechaa.substring(19,21)),Integer.parseInt((fechaa.substring(16,18))));
			  
			   pers.setFecha(ff);
		       //System.out.println(pers.getId()+"AAA/"+pers.getMoneda()+"/"+pers.getDependencia()+"/"+pers.getConcepto()+"/"+pers.getNumero()+
		    		//   "/"+pers.getCodigo()+"/"+pers.getNombre());	    
			   lstPersonas1.add(pers);
		    }
		  }//fin while	 
		   //FacesMessage msg2=new FacesMessage("Un ARCHIVO ..."+nom);
	          // FacesContext.getCurrentInstance().addMessage(null,msg2);
		   this.lstPersonas=lstPersonas1;
			 //FacesMessage msg3=new FacesMessage(this.lstPersonas.get(1).getNombre());
 		        //FacesContext.getCurrentInstance().addMessage(null,msg3);
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		}

	
	@Override
	public Persona ListarPorId(Persona t) throws Exception {
		List<Persona> lista=new ArrayList<Persona>();
		Query q=em.createQuery("FROM Persona p where p.id = ?");
		q.setParameter(1,t.getId());
		lista=(List<Persona>) q.getResultList();
		
		Persona per=lista != null && !lista.isEmpty() ? lista.get(0) : new Persona();
	
		return per;
	  }


	@Override
	public List<Persona> listarxPersona(Persona t,LocalDate inicio,LocalDate fin) throws Exception 
	 { /* There are two approaches to parameter binding: using positional or using
		named parameters. Hibernate and Java Persistence support both options, but you
		cant use both at the same time for a particular query.
		With named parameters, you can rewrite the query as
		String queryString =
		"from Item item where item.description like :search";
		*/
		
		List<Persona> lista =new ArrayList<Persona>();
		if(t.getNombre().equalsIgnoreCase("")&&t.getDependencia().equalsIgnoreCase("")) {
			
			Query q=em.createQuery("From Persona p where "
				+ " (p.fecha BETWEEN :startDate AND :endDate)");
			//Query q=em.createQuery("From Persona p where p.id = 363 ");
			//q.setParameter("code","%"+t.getNombre()+"%");
			//q.setParameter("code1","%"+t.getDependencia()+"%");
			q.setParameter("startDate",inicio);
			q.setParameter("endDate",fin);
		
			System.out.println("'%"+t.getNombre()+"%'");
			lista=(List<Persona>) q.getResultList();
		 }else if(t.getNombre().equalsIgnoreCase("")&& (!t.getDependencia().equalsIgnoreCase(""))){
			     Query q=em.createQuery("From Persona p where  ( p.nombre LIKE :code1)"
						+ " AND (p.fecha BETWEEN :startDate AND :endDate)");
					//Query q=em.createQuery("From Persona p where p.id = 363 ");
					q.setParameter("code","%"+t.getNombre()+"%");
					q.setParameter("code1","%"+t.getDependencia()+"%");
					q.setParameter("startDate",inicio);
					q.setParameter("endDate",fin);
				
					System.out.println("'%"+t.getNombre()+"%'");
					lista=(List<Persona>) q.getResultList();
	         }
		       else{
			        Query q=em.createQuery("From Persona p where (p.nombre LIKE  :code) AND ( p.nombre LIKE :code1)"
						+ " AND (p.fecha BETWEEN :startDate AND :endDate)");
					//Query q=em.createQuery("From Persona p where p.id = 363 ");
					q.setParameter("code","%"+t.getNombre()+"%");
					q.setParameter("code1","%"+t.getDependencia()+"%");
					q.setParameter("startDate",inicio);
					q.setParameter("endDate",fin);
				
					System.out.println("'%"+t.getNombre()+"%'");
					lista=(List<Persona>) q.getResultList();
		            }
		
		//Persona p2=lista.get(0);
		//System.out.println(p2.getNombre()+" / "+p2.getDependencia());
		return lista;
	  }
	
	}

	

