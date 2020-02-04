/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.gruppoinit.people.allegatiManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import it.gruppoinit.people.DAO.DbDAO;
import it.gruppoinit.people.oggetti.BaseBean;

public class AllegatiManager {

	private static String fileSeparator = System.getProperty("file.separator");
    
	private DbDAO delegate; 
	
	public AllegatiManager(){
	}
	
	private void init(){
		this.delegate = new DbDAO("java:comp/env/jdbc/FEDB");
	}
	
	public BaseBean[] getListaAllegati(String codiceCommune, String idPratica){
		
		String codSubPratica = "";
		String codPratica = "";
		if (idPratica.indexOf("/")!= -1){
			String[] token = idPratica.split("/");
			codPratica = token[0];
			codSubPratica = token[1];
		} else {
			codPratica = idPratica;
		}
		
		init();
		ArrayList listaFile = new ArrayList();
		// Leggo la root del file system dove sono stati salvati i file
		String rootPathDir = this.delegate.getParametroConfigurazione(codiceCommune, "upload.directory");
		if (rootPathDir!=null && !rootPathDir.equalsIgnoreCase("")){
			File f = new File(rootPathDir);
			if (f.exists()){
				f = new File(rootPathDir+ fileSeparator +codiceCommune);
				if (f.exists()){
					f = new File(rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica);
					if (f.exists()){
						File[] listaF = f.listFiles();
						if (listaF!=null) {
							for (int i = 0; i < listaF.length; i++) {
								if (listaF[i].isFile()){
									BaseBean file = new BaseBean();
									String nomeFile = listaF[i].getName();
									int indexSplit = nomeFile.indexOf("_");
									if (indexSplit!=-1){
										String codice = nomeFile.substring(0, indexSplit);
										String nome = nomeFile.substring(indexSplit+1, nomeFile.length());
										file.setCodice(codice);
										file.setNomeFile(nome);
										listaFile.add(file);
									}
								}
							}
						}
						if (codSubPratica!=null && !codSubPratica.equalsIgnoreCase("")){
							f = new File(rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica+ fileSeparator +codSubPratica);
							if (f.exists()){
								File[] listaF_ = f.listFiles();
								if (listaF_!=null) {
									for (int i = 0; i < listaF_.length; i++) {
										BaseBean file = new BaseBean();
										String nomeFile = listaF_[i].getName();
										int indexSplit = nomeFile.indexOf("_");
										if (indexSplit!=-1){
											String codice = nomeFile.substring(0, indexSplit);
											String nome = nomeFile.substring(indexSplit+1, nomeFile.length());
											file.setCodice(codice);
											file.setNomeFile(nome);
											listaFile.add(file);
										}
									}
								}
							}
						}
					}
				}
			}
		} 
		if (listaFile.size()==0){
			BaseBean[] listaAllegati = new BaseBean[0];
			return listaAllegati;
		} else {
			BaseBean[] listaAllegati = new BaseBean[listaFile.size()];
			listaAllegati = (BaseBean[]) listaFile.toArray(listaAllegati);
			return listaAllegati;
		}
	}
	
	
	public String getFile(String codiceCommune, String idPratica, String idFile){
		
		String codSubPratica = "";
		String codPratica = "";
		if (idPratica.indexOf("/")!= -1){
			String[] token = idPratica.split("/");
			codPratica = token[0];
			codSubPratica = token[1];
		} else {
			codPratica = idPratica;
		}
		
		byte[] res=null;
		List listaFile=null;
		boolean trovato=false;
		File file=null;
		init();
		// Leggo la root del file system dove sono stati salvati i file
		String rootPathDir = this.delegate.getParametroConfigurazione(codiceCommune, "upload.directory");
		if (rootPathDir!=null && !rootPathDir.equalsIgnoreCase("")){
			File f = new File(rootPathDir);
			if (f.exists()){
				f = new File(rootPathDir+ fileSeparator +codiceCommune);
				if (f.exists()){
					f = new File(rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica);
					if (f.exists()){
						listaFile = listAllSubfiles(f);
					}
				}
			}
		}
		if (listaFile!=null){
			Iterator it = listaFile.iterator();
			while (!trovato && it.hasNext()){
				file = (File) it.next();
				if (file.getName().startsWith(idFile+"_")){
					trovato = true;
				}
			}	
		}
		
		
		try {
			if (trovato){
				res = new byte[Integer.parseInt(String.valueOf(file.length()))];
				InputStream in = new FileInputStream (file);
				in.read(res);
				in.close();
			}
		} catch (Exception e){
			return "";
		}
		
		if (res==null){
			return "";
		} else {
			return new String(Base64.encodeBase64(res));
		}
	} 
	
	
	
	public String putFile(String codiceCommune, String idPratica,String nomeFile,String contentFile,boolean isCertificato){
		boolean error = false;
		String codSubPratica = "";
		String codPratica = "";
		if (idPratica.indexOf("/")!= -1){
			String[] token = idPratica.split("/");
			codPratica = token[0];
			codSubPratica = token[1];
		} else {
			codPratica = idPratica;
		}
		
		init();
		// Leggo la root del file system dove sono stati salvati i file
		String rootPathDir = this.delegate.getParametroConfigurazione(codiceCommune, "upload.directory");
		if (rootPathDir!=null && !rootPathDir.equalsIgnoreCase("")){
			File f = new File(rootPathDir);
			if (f.exists()){
				f = new File(rootPathDir+ fileSeparator +codiceCommune);
				if (!f.exists()) {
					f.mkdir();
				}
				f = new File(rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica);
				if (!f.exists()){
					f.mkdir();
				}
				String path = rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica;
				if (codSubPratica!=null && !codSubPratica.equalsIgnoreCase("")){
					f = new File(rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica+ fileSeparator +codSubPratica);
					if (!f.exists()){
						f.mkdir();
					}
					path = rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica+ fileSeparator +codSubPratica;
				} 
				if (isCertificato){
					f=new File(path+ fileSeparator +"cert");
					if (!f.exists()){
						f.mkdir();
					}
					path = path+ fileSeparator +"cert";
				}
				try {
					String id = Long.toString((new Date()).getTime());
					byte[] ret = Base64.decodeBase64(contentFile.getBytes());
					FileOutputStream fileoutputstream = new FileOutputStream(path+ fileSeparator +id+"_"+nomeFile);
					fileoutputstream.write(ret);
					fileoutputstream.close();
				} catch (Exception e) {
					error = true;
				}
			}  else {error=true;}
		}  else {error=true;}
		
		if (error) 
			return "KO";
		else 
			return "OK";
	}
	
	
	public BaseBean[] getListaCertificati(String codiceCommune, String idPratica){
		String codSubPratica = "";
		String codPratica = "";
		if (idPratica.indexOf("/")!= -1){
			String[] token = idPratica.split("/");
			codPratica = token[0];
			codSubPratica = token[1];
		} else {
			codPratica = idPratica;
		}
		String path = "";
		File[] listaF = null;
		init();
		ArrayList listaFile = new ArrayList();
		// Leggo la root del file system dove sono stati salvati i file
		String rootPathDir = this.delegate.getParametroConfigurazione(codiceCommune, "upload.directory");
		if (rootPathDir!=null && !rootPathDir.equalsIgnoreCase("")){
			File f = new File(rootPathDir);
			if (f.exists()){
				f = new File(rootPathDir+ fileSeparator +codiceCommune);
				if (f.exists()){
					f = new File(rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica);
					if (f.exists()){
						path = rootPathDir+ fileSeparator +codiceCommune+ fileSeparator +codPratica;
						if (codSubPratica!=null && !codSubPratica.equalsIgnoreCase("")){
							f = new File(path+ fileSeparator +codSubPratica);
							if (f.exists()){
								path = path+ fileSeparator +codSubPratica;
								f = new File(path+ fileSeparator +codSubPratica+ fileSeparator +"cert");
								if (f.exists()){
									listaF = f.listFiles();
									if (listaF!=null) {
										for (int i = 0; i < listaF.length; i++) {
											if (listaF[i].isFile()){
												BaseBean file = new BaseBean();
												String nomeFile = listaF[i].getName();
												int indexSplit = nomeFile.indexOf("_");
												if (indexSplit!=-1){
													String codice = nomeFile.substring(0, indexSplit);
													String nome = nomeFile.substring(indexSplit+1, nomeFile.length());
													file.setCodice(codice);
													file.setNomeFile(nome);
													listaFile.add(file);
												}
											}
										}
									}
								}
							}
						} else {
							f = new File(path+ fileSeparator +"cert");
							if (f.exists()){
								listaF = f.listFiles();
								if (listaF!=null) {
									for (int i = 0; i < listaF.length; i++) {
										if (listaF[i].isFile()){
											BaseBean file = new BaseBean();
											String nomeFile = listaF[i].getName();
											int indexSplit = nomeFile.indexOf("_");
											if (indexSplit!=-1){
												String codice = nomeFile.substring(0, indexSplit);
												String nome = nomeFile.substring(indexSplit+1, nomeFile.length());
												file.setCodice(codice);
												file.setNomeFile(nome);
												listaFile.add(file);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (listaFile.size()==0){
			BaseBean[] listaAllegati = new BaseBean[0];
			return listaAllegati;
		} else {
			BaseBean[] listaAllegati = new BaseBean[listaFile.size()];
			listaAllegati = (BaseBean[]) listaFile.toArray(listaAllegati);
			return listaAllegati;
		}
	}
	

	private List listAllSubfiles(File source) {
		//First decline a list, All sub files will add to this list.
		List lists = new ArrayList();
		addSubFilesToList(lists, source);
		return lists;
	}
			 
	private void addSubFilesToList(List list, File source) {
		//List first top sub files
		File[] files = source.listFiles();
		if (files!=null){
			for (File file : files) {
				if (file.isFile()){
					list.add(file);
				}
				addSubFilesToList(list, file);
			}
		}
	}
	
	
	
}
