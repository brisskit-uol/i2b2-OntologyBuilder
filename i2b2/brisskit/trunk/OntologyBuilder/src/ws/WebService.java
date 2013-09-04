package ws;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.minidev.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.JsonPath;
//import com.jayway.restassured.path.json.JsonPath;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * 
 * <brisskit:licence> Copyright University of Leicester, 2012 This software is
 * made available under the terms & conditions of brisskit
 * 
 * @author Saj Issa This resource represents the WebServices to send pdo files
 *         to i2b2 and send an acknowledgement to civi on receipt of patient
 *         data from civi </brisskit:licence>
 * 
 */

@Path("/service")
public class WebService {

	final static Logger logger = LogManager.getLogger(WebService.class);
	static Properties prop = new Properties();
	static String db_name;
	static String db_url;
	static String sourcesystem;
	static String civiurl;
	static String name;
	static String pass;
	static String key;
	static String url = "192.168.0.210";
	
	static String currentproject = "i16project";
	static int currentprojectcount;
	
	static String rest_location = "/civicrm/sites/all/modules/civicrm/extern/rest.php";
	//static String rest_location_orig = "/civicrm/civicrm/ajax/rest";
	/*
	static 
	{
		try {
			prop.load(new FileInputStream("/local/webservice.properties"));
			String env = prop.getProperty("env");
						
			db_name = prop.getProperty(env + "." + "db_name");
			db_url  = prop.getProperty(env + "." + "db_url");
			sourcesystem = prop.getProperty(env + "." + "sourcesystem");
			civiurl  = prop.getProperty(env + "." + "civiurl");
			name  = prop.getProperty(env + "." + "name");
			pass  = prop.getProperty(env + "." + "pass");
			key  = prop.getProperty(env + "." + "key");
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	*/
	
	static List<Ontology> ontology = new ArrayList<Ontology>();
	
	
	static 
	{
		
		 
		try {
		URL objectGet = new URL("http://rest.bioontology.org/bioportal/ontologies?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");

        URLConnection yc = objectGet.openConnection();
        yc.setRequestProperty("Accept", "application/json");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                yc.getInputStream()));
        
        
        
        StringBuilder sb = new StringBuilder();
        String line;
        String json = "";
        while ((line = in.readLine()) != null) {
        	//System.out.println(line);
        	json = line;
        	logger.info(line);
			sb.append(line);
		}
        
        //String contact_id = JsonPath.read(json, "$.values[0].contact_id");
        
       
                
        List<String> displayLabel = JsonPath.read(json, "$.success.data[0].list[0].ontologyBean[*].displayLabel");       
        List<Integer> id = JsonPath.read(json, "$.success.data[0].list[0].ontologyBean[*].id");   
        List<Integer> ontologyId = JsonPath.read(json, "$.success.data[0].list[0].ontologyBean[*].ontologyId");  
        //Collections.sort(displayLabel);
        
        logger.info(id.size() + " " + displayLabel.size());
        
       /* for(String s: displayLabel)
        {
        	logger.info(s);
        }*/
        
        int count = 0;
        for(int s: id)
        {
        	Ontology o = new Ontology();
        	o.setId(s);
        	o.setName(displayLabel.get(count));
        	o.setOntologyId(ontologyId.get(count));
        	System.out.println(s + "gggggggggggg " + displayLabel.get(count));
        	
        	if ((displayLabel.get(count)).equals("Systematized Nomenclature of Medicine, International Version")
        			||
        		(displayLabel.get(count)).equals("International Classification of Diseases, Version 10")
        		
        		)
        	{
        		ontology.add(o);
        	}
        	
        	
        	count++;
        	
        	
        	
        	        	
        }
        
        Collections.sort(ontology, Ontology.OntologyNameComparator);
        
        //Arrays.sort(ontology, Ontology.OntologyNameComparator);
         
        
		}catch (Exception e) {
            e.printStackTrace();
        }
		

        
	}
	
	
	
	@GET
	@Path("ontologies/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String ontologies(@PathParam("query") String query, @Context UriInfo ui) {
		
		
		String uri = ui.getRequestUri().toString();
        int i = uri.indexOf('?'); 
                
        if (i != -1)
        {
        	uri = uri.substring(i);        
        }
        else
        {
        	uri = "";
        }
        	
        System.out.println(uri);
        
        
        
        String uri2 = ui.getRequestUri().toString();
        int i2 = uri2.indexOf('=');
        
        if (i2 != -1)
        {
        	uri2 = uri2.substring(i2+1);
        }
        else
        {
        	uri2 = "";
        }
        	
        System.out.println(uri2);
        
        
        
		System.out.println(query);
				
		String o_json = "";
		
		List<Ontology> ontology_subset = new ArrayList<Ontology>();
		
		for(Ontology ontc: ontology)
        {

			//uri2.toUpperCase()
			if (uri2.length() <= ontc.getName().length() 
				&& 
				uri2.toUpperCase().equals(ontc.getName().substring(0,uri2.length()).toUpperCase()))
			{
				System.out.println(uri2 + " " + ontc.getName());
				System.out.println(uri2.length() + " " + ontc.getName().length());
				System.out.println(uri2 + " " + ontc.getName().substring(0,uri2.length()));
				
				ontology_subset.add(ontc);
			}
			
        }
		
        Gson gson = new Gson();
        
        Type listType = new TypeToken<List<Ontology>>(){}.getType();
        
        o_json = gson.toJson(ontology_subset,listType);
        System.out.println(o_json);
		
		return o_json;
	}
	
	public static Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}  
	
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	@GET
	@Path("terms/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String terms(@PathParam("query") String query, @Context UriInfo ui) {
		
		String q = "";
		String ontologyid = "";
		List<SearchBean> searchbean = new ArrayList<SearchBean>();
    	
		String uri = ui.getRequestUri().toString();
		int i = uri.indexOf('?'); 
        
		
		Map<String, String> map = getQueryMap(uri.substring(i+1));  
		Set<String> keys = map.keySet();  
		for (String key : keys)  
		{  
		   System.out.println("Name=" + key);  
		   System.out.println("Value=" + map.get(key));  
		   
		   if (key.equals("q")) { q = map.get(key); }
		   if (key.equals("ontologyid")) { ontologyid = map.get(key); }
		}  
		
		/*********************************************/
		
		//http://rest.bioontology.org/bioportal/search/?query=p&ontologyids=1335&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339
		
		System.out.println("1");
		
		System.out.println("http://rest.bioontology.org/bioportal/search/?query=" + q + "&ontologyids=" + ontologyid + "&maxnumhits=20&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");
		
		try {
		URL objectGet = new URL("http://rest.bioontology.org/bioportal/search/?query=" + q + "&ontologyids=" + ontologyid + "&maxnumhits=20&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");
		// http://rest.bioontology.org/bioportal/search/?query=heart&ontologyids=1526&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339
		System.out.println("2");
        URLConnection yc = objectGet.openConnection();
        yc.setRequestProperty("Accept", "application/json");
        System.out.println("3");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                yc.getInputStream()));
        
        System.out.println("4");        
        StringBuilder sb = new StringBuilder();
        String line;
        String json = "";
        while ((line = in.readLine()) != null) {
        	System.out.println(line);
        	json = line;
        	logger.info(line);
			sb.append(line);
		}
        
        System.out.println("5");
        //String contact_id = JsonPath.read(json, "$.values[0].contact_id");
        
        boolean flag = false;
        
        try {
          Integer s_ontologyVersionId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean.ontologyVersionId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
          System.out.println(s_ontologyVersionId);
          flag = true;
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
                
        if (flag)
        {
        	int ontologyVersionId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].ontologyVersionId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            String preferredName = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].preferredName");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            
            int ontologyId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].ontologyId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            String ontologyDisplayLabel = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].ontologyDisplayLabel");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            String recordType = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].recordType");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            String conceptId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].conceptId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            String conceptIdShort = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].conceptIdShort");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId            
            String contents = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].contents");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
            
            SearchBean o = new SearchBean();
        	o.setOntologyVersionId(ontologyVersionId);
        	o.setPreferredName(preferredName);
        	o.setOntologyId(ontologyId);
        	o.setOntologyDisplayLabel(ontologyDisplayLabel);
        	o.setRecordType(recordType);
        	o.setConceptId(conceptId);
        	o.setConceptIdShort(conceptIdShort);
        	o.setContents(contents);
        	//o.setOntologyId(ontologyId);
        	System.out.println(ontologyVersionId /*+ " " + displayLabel*/);       	
        	searchbean.add(o);
        	
        }
        else
        {
        List<Integer> ontologyVersionId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].ontologyVersionId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        List<String> preferredName = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].preferredName");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        
        List<Integer> ontologyId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].ontologyId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        List<String> ontologyDisplayLabel = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].ontologyDisplayLabel");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        List<String> recordType = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].recordType");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        List<String> conceptId = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].conceptId");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        List<String> conceptIdShort = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].conceptIdShort");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId            
        List<String> contents = JsonPath.read(json, "$.success.data[0].page.contents.searchResultList.searchBean[*].contents");  //ontologyDisplayLabel conceptIdShort preferredName contents ontologyVersionId     
        
        System.out.println("6");
        

        logger.info(ontologyVersionId.size());
        
        for (String s : preferredName)
        {
        	System.out.println("vvvvvvvvvvvv " + s);
            	
        }
    
        
        
        int count = 0;
        for(int s: ontologyVersionId)
        {
        	SearchBean o = new SearchBean();
        	o.setOntologyVersionId(s);
        	o.setPreferredName(preferredName.get(count).toString());
        	o.setOntologyId(ontologyId.get(count));
        	o.setOntologyDisplayLabel(ontologyDisplayLabel.get(count));
        	o.setRecordType(recordType.get(count));
        	o.setConceptId(conceptId.get(count));
        	o.setConceptIdShort(conceptIdShort.get(count));
        	o.setContents(contents.get(count));
        	//o.setOntologyId(ontologyId.get(count));
        	System.out.println(s /*+ " " + displayLabel.get(count)*/);
        	
        	count++;
        	searchbean.add(o);
        	
        	        	
        }
        } // else
        
        System.out.println("7");
        //Collections.sort(ontology, Ontology.OntologyNameComparator);
        
              
        
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		/*********************************************/
		
		
		System.out.println("query " + query);
				
		String o_json = "";
		
        Gson gson = new Gson();
        
        Type listType = new TypeToken<List<SearchBean>>(){}.getType();
        
        o_json = gson.toJson(searchbean,listType);
        System.out.println(o_json);
		
        
        
        
		return o_json;
		
		//return "";
	}
	
	
	int c = 0;
	int le = 0;
	PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10);
	Stack<Integer> lifo = new Stack<Integer>();
	Stack<String> s_path = new Stack<String>();
	//Stack<String> s_label = new Stack<String>();
	List<String> a_label = new ArrayList<String>();

	String p = "";
	
	public void nodestack(List<JSONObject> nodes) {
		
		
		for (JSONObject a_root : nodes)
		{
			String n_path = "";
			String n_id = "";
			String n_label = "";
			
			
			Set<String> keys = a_root.keySet();

			for(String k : keys)
			{
				//System.out.println("k " + k + " " + a_root.get(k));
				if (k.equals("attr"))
				{
					JSONObject idj = (JSONObject) a_root.get(k);
					String id = (String) idj.get("id");
					n_id = id;
					//System.out.println("id " + id);
					n_path = n_path + ":" + id;		
					
					s_path.push(id);
					//System.out.println("s_path " + s_path);
					   
				}
				
				if (k.equals("data"))
				{
				   n_label = (String) a_root.get(k);
				   //System.out.println("n_label " + n_label);
				   
				   //s_label.push(n_label);
				   
				   for (int idx = a_label.size() - 1; idx >= le; idx--)
					{
						a_label.remove(idx);
					}
				   
				   a_label.add(n_label);
				   
				}
				
			}
				
			
			
			System.out.println("L-" + le + " C-"+ (a_root.size()-3) + " X-"+a_root.size() + " " + a_root);
			System.out.println("s_path " + s_path);
			
			//System.out.println("dosql ");	
			
			String c_path = "";
			
			if (le == 0)
			{
				dosqltable(Integer.valueOf(le), "i2b2", "i2b2", "\\" + n_label + "\\", n_label, "CA", n_id, n_label);
			}
			
			
			//for(String obj : s_label)
			//for(String obj : a_label)
			//{
			//	c_path = c_path + "\\" + obj;
			//    
			//}
			
			//System.out.println("c_path" + c_path + " size " + a_label.size());
			
			System.out.println("a_label " + a_label + " size " + a_label.size());
			
			for (int idx = a_label.size() - 1; idx >= le + 1; idx--)
			{
				a_label.remove(idx);
			}
			
			
			for(String obj : a_label)
			{
				c_path = c_path + "\\" + obj;
			    
			}
			
			System.out.println("c_path " + c_path + " size " + a_label.size());
			
			
			
			if ((a_root.size()-3) == 0) 
			{
				//System.out.println("This is a node ");
				
				p = p + n_label + " | ";
				
				System.out.println("p "  + p);
				
				dosql(Integer.valueOf(le), c_path + "\\", n_label, "LA", n_id, c_path);
				
				
				c = c -1;
				
				//System.out.println("c " + c);
				
				if (!lifo.empty()) 
				{
				le = lifo.pop();
				System.out.println("popped " + le + " lifo " + lifo.toString());
		    	
				}
				
				if (!s_path.empty()) 
				{
				   s_path.pop();
				   System.out.println("popped " + le + " s_path " + s_path.toString());
			    	
				}
				
				//if (!s_label.empty()) 
				//{
				//   s_label.pop();
				//}
				
				//if (a_label.size() != 0) 
				//{
				//   a_label.remove(n_label.length());
				//}
				
				System.out.println("c " + c);
				//System.out.println("pop " + le + " lifo " + lifo.toString());
				
				System.out.println("");
				
				//if (c==0 && !s_label.empty()) 					
				//{
				//	s_label.pop();
				//    System.out.println("pop " + le + " s_label " + s_label.toString());					
				//}
					
				//if (c==0 && !lifo.empty()) 
				//{
				//	le = lifo.pop();
				//	System.out.println("pop " + le + " lifo " + lifo.toString());					
				//}
			}
			else
			{
			
			try {
				
				dosql(Integer.valueOf(le), c_path + "\\", n_label, "FA", n_id, c_path);
				
				le = le + 1;	
				p = p + n_label + " | ";
				
				System.out.println("p "  + p);
				
				
				//c++;
				List<JSONObject> childl1 = JsonPath.read(a_root, "$.children[*]");
			    //System.out.println("This has " + childl1.size() + " children " + " le " + le);
			    			   			    
			    c=c+childl1.size();
			    
			    if (childl1.size() >= 2)
			    {
			    	for(int i=1; i < childl1.size(); i++ )
			    	{
			    	   lifo.push(le);
			    	   System.out.println(c + "push " + le + " lifo " + lifo.toString());
			    	}
			    	
			    	//System.out.println("push " + le + " lifo " + lifo.toString());
			    }
			    
			
			    System.out.println("");
				
			    nodestack(childl1);
			
			}
			catch (InvalidPathException e) 
			{
		        System.out.println("Exception le "+le + " c " + c);						
			}
			
			}
			
		}
		
	}

	public void nodeq(List<JSONObject> nodes) {
		
		
		for (JSONObject a_root : nodes)
		{
			//List<String> dataS = JsonPath.read(track, "$.?[data="+a_root+"]");
			//System.out.println("X"+dataS);
			System.out.println("L-" + le + " C-"+ (a_root.size()-3) + " X-"+a_root.size() + " " + a_root);
			
			Set<String> keys = a_root.keySet();

			for(String k : keys)
			{
				//System.out.println("k "+k);
				
			}
		
			if ((a_root.size()-3) == 0) 
			{
				System.out.println("This is a node ");
				
				c = c -1;
				
				System.out.println("c " + c);
				
				if (c==0) 
				{
					le = queue.remove();
					System.out.println("remove " + le + " queue " + queue.toString());
					//System.out.println("le " + le);
				}
			}
			else
			{
			
			try {
				
				le = le + 1;	
				//c++;
				List<JSONObject> childl1 = JsonPath.read(a_root, "$.children[*]");
			    System.out.println("This has " + childl1.size() + " children " + " le " + le);
			    
			    
			    
			    c=childl1.size();
			    
			    if (childl1.size() >= 2)
			    {
			    	queue.add(le);	
			    	System.out.println("add " + le + " queue " + queue.toString());
			    }
			    
				//level = level + 1;
				
			    nodeq(childl1);
			
			}
			catch (InvalidPathException e) 
			{
		        System.out.println("Exception le "+le + " c " + c);
				le = le - 1; 
				
			}
			
			}
			
			//for (JSONObject a_childs : childl1)
			//{
			//	node(childl1);
			//}
			
			

		}
		
	}

	public void node1(List<JSONObject> nodes, int level) {
		
		
		for (JSONObject a_root : nodes)
		{
			//List<String> dataS = JsonPath.read(track, "$.?[data="+a_root+"]");
			//System.out.println("X"+dataS);
			System.out.println("L-" + level + " C-"+ (a_root.size()-3) + " X-"+a_root.size() + " " + a_root);
			
			Set<String> keys = a_root.keySet();

			for(String k : keys)
			{
				//System.out.println("k "+k);
				
			}
		
			
			
			try {
				
				level = level + 1;	
				//c++;
				List<JSONObject> childl1 = JsonPath.read(a_root, "$.children[*]");
			    System.out.println("This has " + childl1.size() + " children ");
			    
			    c=childl1.size();
			    
			    //if (childl1.size() >= 0)
			    //{
			    //	level = level + 1;		    
			    //}
			    
				//level = level + 1;
				
			    node1(childl1, level);
			
			}
			catch (InvalidPathException e) 
			{
		        System.out.println("Exception level "+level + " c " + c);
				level = level - 1; 
				
			}
			
			
			//for (JSONObject a_childs : childl1)
			//{
			//	node(childl1);
			//}
			
			

		}
		
	}
	
	@POST
	@Path("settree")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	//public Response createTrackInJSON(String track) {
	public String createTrackInJSON(String track) {
			 
		String result = "Track saved : " + track;
		System.out.println("X1 " + track);
		
		
		
		
		
		
		
		
		
		
		
		
		getproject();
		
		createdb();
		
		resetDatabasems();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	    //[{"data":"Certain infectious and parasitic diseases(A00-B99p9) p(undefined)","attr":{"id":"A00-B99p9"},"state":"open","metadata":{},
		//"children":[{"data":"Helminthiases(B65-B83p9) p(A00-B99p9)","attr":{"id":"B65-B83p9","class":""},"state":"open","metadata":{},
		//"children":[{"data":"Other intestinal helminthiases, not elsewhere classified(B81) p(B65-B83p9)","attr":{"id":"B81","class":""},"state":"open","metadata":{},"children":[{"data":"Other specified intestinal helminthiases(B81p8)","attr":{"id":"B81p8"},"metadata":{}},{"data":"Intestinal angiostrongyliasis(B81p3)","attr":{"id":"B81p3"},"metadata":{}},{"data":"Helminthiases(B65-B83p9)","attr":{"id":"B65-B83p9"},"metadata":{}},{"data":"Trichostrongyliasis(B81p2) p(B81)","attr":{"id":"B81p2","class":""},"metadata":{}}]}]}]},{"data":"SNOMED International(C1140118) p(undefined)","attr":{"id":"C1140118"},"state":"closed","metadata":{},"children":[{"data":"GENERAL LINKAGE/MODIFIERS(C0338068) p(C1140118)","attr":{"id":"C0338068","class":""},"state":"closed","metadata":{},"children":[{"data":"GENERAL INFORMATION QUALIFIERS(C0332118) p(C0338068)","attr":{"id":"C0332118","class":""},"state":"closed","metadata":{},"children":[{"data":"Availability of(C0470187)","attr":{"id":"C0470187"},"metadata":{}},{"data":"Treatment required for(C0332121) p(C0332118)","attr":{"id":"C0332121","class":""},"metadata":{}}]}]}]},{"data":"Diseases of the musculoskeletal system and connective tissue(M00-M99p9) p(undefined)","attr":{"id":"M00-M99p9"},"state":"closed","metadata":{},"children":[{"data":"Arthropathies(M00-M25p9) p(M00-M99p9)","attr":{"id":"M00-M25p9","class":""},"state":"closed","metadata":{},"children":[{"data":"Arthrosis(M15-M19p9) p(M00-M25p9)","attr":{"id":"M15-M19p9","class":""},"state":"closed","metadata":{},"children":[{"data":"Coxarthrosis [arthrosis of hip](M16)","attr":{"id":"M16"},"metadata":{}},{"data":"Polyarthrosis(M15) p(M15-M19p9)","attr":{"id":"M15","class":""},"metadata":{}}]}]}]},{"data":"parent","attr":{"id":"root.id","class":""},"state":"closed","metadata":{},"children":[{"data":"child1","attr":{"id":"child1.id"},"metadata":{}},{"data":"child2","attr":{"id":"child2.id","class":""},"metadata":{}}]}]

		List<Node> nodes = new ArrayList<Node>();
		List<String> nodesS = new ArrayList<String>();
		
				
		List<String> data = JsonPath.read(track, "$..data[*]");
		
		System.out.println("X2 " + data.size());
		
        List<String> children = JsonPath.read(track, "$..children[*]");
		
		System.out.println("X3 " + children.size());
		
		String data1 = JsonPath.read(track, "$.[0].data[*]");
		System.out.println("X4 " + data1);
		
		List<String> data2 = JsonPath.read(track, "$.[*].data[*]");
		System.out.println("X5 " + data2);
		
		List<JSONObject> all_roots = JsonPath.read(track, "$.[*].[*]");
		System.out.println("X7 " + all_roots.size() + " " + all_roots);
		// top level
		
		for (JSONObject a_root : all_roots)
		{
			System.out.println("X8 " + a_root);	
			List<JSONObject> a = new ArrayList<JSONObject>();
			le=0;
			queue = new PriorityQueue<Integer>(10);
			lifo = new Stack<Integer>();
			s_path = new Stack<String>();
			//s_label = new Stack<String>();
			a_label = new ArrayList<String>();
			
			a.add(a_root);
			//c = 0;
			//node1(a, 0);
			//nodeq(a);
			nodestack(a);	
		}
		
		return "{ \"d\" : \"" + currentproject + "\"}";
		
		//List<String> dataS = JsonPath.read(track, "$.?[data="+a_root+"]");
		//System.out.println("X"+dataS);
	
		//node(all_roots, 0);
		
/*		
	*/	
		/*
		for (JSONObject a_root : all_roots)
		{
			System.out.println("X "+a_root.size() + " " + a_root);
			
			Set<String> keys = a_root.keySet();

			for(String k : keys)
			{
				System.out.println("k "+k);
				
			}
			
			List<JSONObject> childl1 = JsonPath.read(a_root, "$.children[*]");
			System.out.println(childl1.size() + " " + childl1);
			
		}*/
		
			// for each child iterate
			
				
		/*		
	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            if( jObject.get(key) instanceof JSONObject ){

	            }
*/
		/*	for (int i = 0; i < roots.length(); ++i) {
			    JSONObject rec = recs.getJSONObject(i);
			    int id = rec.getInt("id");
			    String loc = rec.getString("loc");
			    
			}*/
		
		
	/*
		for(int i =0; i<data.size()-children.size(); i++)
		{
			List<String> dataS = JsonPath.read(track, "$.data["+i+"]");
			System.out.println("X"+dataS);
			
		}
		*/
		//path2 = JsonPath.read(track, "$.success.data[0].list[0].classBean.relations[0].entry.string[1]");
        
		//return Response.status(201).entity(result).build();
 
	}
	
	@GET
	@Path("parents/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String parents(@PathParam("query") String query, @Context UriInfo ui) {
		
		String q = "";
		String ontologyid = "";
		String ontologyversionid = "";
		String conceptid = "";
		
		List<ClassBean> classBean = new ArrayList<ClassBean>();
    	
		String uri = ui.getRequestUri().toString();
		int i = uri.indexOf('?'); 
        
		
		Map<String, String> map = getQueryMap(uri.substring(i+1));  
		Set<String> keys = map.keySet();  
		for (String key : keys)  
		{  
		   System.out.println("Name=" + key);  
		   System.out.println("Value=" + map.get(key));  
		   
		   if (key.equals("ontologyid")) { ontologyid = map.get(key); }
		   if (key.equals("conceptid")) { conceptid = map.get(key); }
		   if (key.equals("ontologyVersionId")) { ontologyversionid = map.get(key); }
		   
		}  
		
		System.out.println("http://rest.bioontology.org/bioportal/virtual/rootpath/" + ontologyid + "/" + conceptid + "?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");
		// http://rest.bioontology.org/bioportal/virtual/rootpath/3232/C0189917?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339
		
		try {
		URL objectGet = new URL("http://rest.bioontology.org/bioportal/virtual/rootpath/" + ontologyid + "/" + conceptid + "?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");
		// http://rest.bioontology.org/bioportal/search/?query=heart&ontologyids=1526&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339

        URLConnection yc = objectGet.openConnection();
        yc.setRequestProperty("Accept", "application/json");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                yc.getInputStream()));
        
                
        StringBuilder sb = new StringBuilder();
        String line;
        String json = "";
        while ((line = in.readLine()) != null) {
        	System.out.println(line);
        	json = line;
        	logger.info(line);
			sb.append(line);
		}
        
        System.out.println("p1");
        
        String[] parts = null;
        String path2 = "";
        		
        try {
        path2 = JsonPath.read(json, "$.success.data[0].list[0].classBean.relations[0].entry.string[1]");
        //ontologyversionid = JsonPath.read(json, "$.success.data[0].list[0].classBean.ontologyVersionId");
        }
        catch (ClassCastException cce)
        {
            System.out.println("p2");
        }
        
        System.out.println("p2");
        
        path2 = path2 + "." + conceptid;
        System.out.println("Parents " + path2);
        System.out.println("ontologyversionid " + ontologyversionid);  
        
        
        // if ontologyversionid = 44103
        
        if (ontologyversionid.equals("44103"))
        {
           Pattern pattern = Pattern.compile("\\.[A-Z]");
           Matcher matcher = pattern.matcher(path2);
           
           List<Integer> split = new ArrayList<Integer>();
           
           while (matcher.find()) {
        	      System.out.print("Start index: " + matcher.start());
        	      System.out.print(" End index: " + matcher.end() + " ");
        	      System.out.println(matcher.group());
        	      split.add(matcher.start());
           }
           
           split.add(path2.length());
           
           parts = new String[split.size()];
           
           
           int y = 0;
           int z = 0;
           for (int x : split)
           {      	   
        	   parts[z] = path2.substring(y, x);
        	   //parts[z] = path2.substring(y, x);
        	   
        	   System.out.println("XXXX " + path2.substring(y, x));    
        	   y = x+1;
        	   z++;
           }
           
        }
        else
        {
            parts = path2.split("\\.");	
        }
        
        
        		
        System.out.println("parts " + parts);
        System.out.println("parts length " + parts.length);
        
        if (parts != null)
        {
        	String parent = null;
        	
        	for (String p : parts)
        	{
        		if (!p.equals(""))
        		{
        		System.out.println("p=" + p + "*");
        		// http://rest.bioontology.org/bioportal/concepts/50291/C0018821?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339
        	        		
        		URL objectGet1 = new URL("http://rest.bioontology.org/bioportal/concepts/"+ ontologyversionid +"/"+ p +"?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");
        		// http://rest.bioontology.org/bioportal/search/?query=heart&ontologyids=1526&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339

                URLConnection yc1 = objectGet1.openConnection();
                yc1.setRequestProperty("Accept", "application/json");
                BufferedReader in1 = new BufferedReader(
                        new InputStreamReader(
                        yc1.getInputStream()));
                
                        
                StringBuilder sb1 = new StringBuilder();
                String line1;
                String json1 = "";
                while ((line1 = in1.readLine()) != null) {
                	//System.out.println(line1);
                	json1 = line1;
                	//logger.info(line1);
        			sb1.append(line1);
        		}
        		
                String id = JsonPath.read(json1, "$.success.data[0].classBean.id");
                String fullid = JsonPath.read(json1, "$.success.data[0].classBean.fullId");
                String label = JsonPath.read(json1, "$.success.data[0].classBean.label");
                
                ClassBean o = new ClassBean();
                
                if (parent != null) { o.setParentid(parent); }
                o.setId(id);
                o.setFullId(fullid);
                o.setLabel(label);
                
                classBean.add(o);
                
                parent = id;
        		}
                        		
        	}            
        	
        }
                              
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		String o_json = "";
		
        Gson gson = new Gson();
        
        Type listType = new TypeToken<List<ClassBean>>(){}.getType();
        
        o_json = gson.toJson(classBean,listType);
        System.out.println(o_json);
                
		return o_json;
	}
	
	
	@GET
	@Path("siblings/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String siblings(@PathParam("query") String query, @Context UriInfo ui) {
		
		String q = "";
		String ontologyid = "";
		String ontologyversionid = "";
		String conceptid = "";
		
		List<ClassBean> classBean = new ArrayList<ClassBean>();
		Set<ClassBean> classBeanSet = new HashSet<ClassBean>();
		List<ClassBean> list = new ArrayList<ClassBean>();
		
		String uri = ui.getRequestUri().toString();
		int i = uri.indexOf('?'); 
        
		
		Map<String, String> map = getQueryMap(uri.substring(i+1));  
		Set<String> keys = map.keySet();  
		for (String key : keys)  
		{  
		   System.out.println("Name=" + key);  
		   System.out.println("Value=" + map.get(key));  
		   
		   if (key.equals("ontologyid")) { ontologyid = map.get(key); }
		   if (key.equals("conceptid")) { conceptid = map.get(key); }
		   if (key.equals("ontologyversionid")) { ontologyversionid = map.get(key); }
		}  
		
		
		// http://rest.bioontology.org/bioportal/virtual/rootpath/3232/C0189917?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339
		
		try {
		        		// http://rest.bioontology.org/bioportal/concepts/50291/C0018821?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339
        	        
				System.out.println("xxxxxxxxx http://rest.bioontology.org/bioportal/concepts/"+ ontologyversionid +"/"+ conceptid +"?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339"); 
        		URL objectGet1 = new URL("http://rest.bioontology.org/bioportal/concepts/"+ ontologyversionid +"/"+ conceptid +"?apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339");
        		// http://rest.bioontology.org/bioportal/search/?query=heart&ontologyids=1526&apikey=473e4e23-6ca6-4d54-a7aa-bbd9a24bf339

                URLConnection yc1 = objectGet1.openConnection();
                yc1.setRequestProperty("Accept", "application/json");
                BufferedReader in1 = new BufferedReader(
                        new InputStreamReader(
                        yc1.getInputStream()));
                
                        
                StringBuilder sb1 = new StringBuilder();
                String line1;
                String json1 = "";
                while ((line1 = in1.readLine()) != null) {
                	//System.out.println(line1);
                	json1 = line1;
                	//logger.info(line1);
        			sb1.append(line1);
        		}
        		
                List<String> id = JsonPath.read(json1, "$.success.data[*].classBean.relations[0].entry.list.classBean.id");
                List<String> fullid = JsonPath.read(json1, "$.success.data[*].classBean.relations[0].entry.list.classBean.fullId");
                List<String> label = JsonPath.read(json1, "$.success.data[*].classBean.relations[0].entry.list.classBean.label");
                
                
                int count = 0;
                for(String s: id)
                {
                	ClassBean o = new ClassBean();
                    
                    //o.setParentid(parent);
                    o.setId(s);
                    o.setFullId(fullid.get(count));
                    o.setLabel(label.get(count));
                    
                    System.out.println(label.get(count) + " " + label.get(count));
                	
                    count++;
                    classBean.add(o);
                    classBeanSet.add(o);
                            	
                                  	        	
                }
                
                Collections.sort(classBean, ClassBean.ClassBeanNameComparator);
                //Collections.sort(classBeanSet, ClassBean.ClassBeanNameComparator);
                
                list = new ArrayList<ClassBean>(classBeanSet);

                Collections.sort(list, ClassBean.ClassBeanNameComparator);
                
                
                                              
		}catch (Exception e) {
            e.printStackTrace();
        }
		
		String o_json = "";
		
        Gson gson = new Gson();
        
        Type listType = new TypeToken<List<ClassBean>>(){}.getType();
        
        //o_json = gson.toJson(classBean,listType);
        o_json = gson.toJson(list,listType);
        System.out.println(o_json);
                
		return o_json;
	}
	
	
	@GET
	@Path("authenticate/{username}/{password}/{apikey}/{time}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String authenticate(@PathParam("username") String username, @PathParam("password") String password, @PathParam("apikey") String apikey, @PathParam("time") String time) {
		return "{sessionid}";
	}
	
	@GET
	@Path("project/create/{name}/{sessionid}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String createproject(@PathParam("name") String name, @PathParam("sessionid") String sessionid) {
		return "{tttt,gggg}";
	}
	
	
	@GET
	@Path("i2b2callback2/{incomingXML}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXMLi2b2(@PathParam("incomingXML") String incomingXML) {
		return "{tttt,gggg}";
	}
	
	@GET
	@Path("i2b2callback3/{incomingXML}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXMLi2b3(@PathParam("incomingXML") String incomingXML) {
		return "callbacki2b2({\"status\" : \"OK\"})";
	}
		
	@GET
	@Path("i2b2callback1/{incomingXML}")
	@Produces("text/html")
	public String postOnlyXMLi2b1(@PathParam("incomingXML") String incomingXML) {
		logger.info("postOnlyXMLi2b1 incomingXML :" + incomingXML);
		
		String ids = "";
		String project = "";
		List<String> civi_contact_id = new ArrayList<String>();
		
		
		
		if (incomingXML.contains("*")) {
			String[] parts = incomingXML.split("\\*");
			ids = parts[0]; 
			project = parts[1]; 
			logger.info("if ids :" + ids);
			logger.info("if project :" + project);
			
		} 
		else 
		{
			ids = incomingXML;
			logger.info("else ids :" + ids);
		}
		
	//ist<String> brisskitids = sql(ids);

		/*
		String date = project + "-i2b2-cohort-"
				+ new java.text.SimpleDateFormat("yyyy-MM-dd--HH-mm-ss")
						.format(new java.util.Date());
		System.out.println(date);*/

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);

		/*WebResource createGroupService = client.resource(createGroup(date));
		String createGroupValueService = createGroupService.get(String.class);

		logger.info(" ");
		logger.info("responseoptionValueService............"
				+ createGroupValueService);

		int group_id = JsonPath.read(createGroupValueService, "$.values[0].id");

		logger.info("group_id............" + group_id);*/

		System.out.println("2");
		
		String error = "";
		int error_count = 0;
		int patient_count = 0;
				
		//Authenticate
		
		WebResource getAuthenticatedService = client.resource(getAuthenticated());
		
		logger.info(" * "); 
		
        String getAuthenticatedValueService = getAuthenticatedService.get(String.class);
        
        logger.info(" ** ");
       
        String api_key = JsonPath.read(getAuthenticatedValueService,"$.api_key");       
        logger.info(" api_key " + api_key);        
        String PHPSESSID = JsonPath.read(getAuthenticatedValueService,"$.PHPSESSID");        
        logger.info(" PHPSESSID " + PHPSESSID);        
        String key = JsonPath.read(getAuthenticatedValueService,"$.key");        
        logger.info(" key " + key);
        
        //Authenticate
        
		
		
		
		
		
		
		String groupname = "i2b2-" + project + "-p-" +patient_count + " "
				+ new java.text.SimpleDateFormat("yyyy-MM-dd--HH-mm-ss")
						.format(new java.util.Date());
		
		if (error_count > 0)
		{
			groupname = "i2b2-" + project + "-p-" + patient_count + "-m-" + error_count + " "
					+ new java.text.SimpleDateFormat("yyyy-MM-dd--HH-mm-ss")
							.format(new java.util.Date());
		}
		
		
		
		System.out.println(groupname);
		
		WebResource createGroupService = client.resource(createGroup(groupname,api_key,PHPSESSID,key));
		String createGroupValueService = createGroupService.get(String.class);

		logger.info(" ");
		logger.info("responseoptionValueService............"
				+ createGroupValueService);

		int group_id = JsonPath.read(createGroupValueService, "$.values[0].id");

		logger.info("group_id............" + group_id);
		
		
		
		
		for (String civi_contact_ids : civi_contact_id) {
			logger.info("civi_contact_ids :" + civi_contact_ids);
			
			WebResource addContactToGroupService = client
					.resource(addContactToGroup(group_id, civi_contact_ids,api_key,PHPSESSID,key));
			String addContactToGroupValueService = addContactToGroupService
					.get(String.class);

			logger.info(" ");
			logger.info("addContactToGroupValueService............"
					+ addContactToGroupValueService);
			
		}
		
		
		

		if (error != "")
		{
			logger.info("Do not exist in civi " + error.substring(0, error.length()-1));
			//return "callbacki2b2({\"status\" : \" "+ error.substring(0, error.length()-1) + "\"})";
			return "callbacki2b2({\"status\" : \"success\", \"log\" : \"some patients do not exist in civicrm\", \"patients\" : \""+ patient_count + "\", \"missing\" : \""+ error_count + "\"})";
		}
		else
		{
		    return "callbacki2b2({\"status\" : \"success\", \"log\" : \"all patients exist in civicrm\", \"patients\" : \""+ patient_count + "\", \"missing\" : \""+ error_count + "\"})";
		}
		
		//return "success";
	}


	@GET
	@Path("disp/{val}")
	@Produces("text/plain")
	public String getParameterToAdd(@PathParam("val") String name) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("Dislay Message : ").append(name);

		return buffer.toString();
	}

	


	


	
	
	/************************************/
	/* CIVI CALLS                       */
	/************************************/
		
	private static URI getOptionGroupBaseURI(String api_key, String PHPSESSID, String key) {
		logger.info("getOptionGroupBaseURI ");
		logger.info("http://"+ civiurl + rest_location + "?json=1&debug=1&version=3&entity=OptionGroup&action=get&name=activity_status" + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);
			
		return UriBuilder
				.fromUri(
						"http://" + civiurl +  rest_location + "?json=1&debug=1&version=3&entity=OptionGroup&action=get&name=activity_status" + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID)
				.build();
	}

	private static URI getOptionValueBaseURI(String option_group_id,String status,String api_key, String PHPSESSID, String key) {
		logger.info("getOptionValueBaseURI ");
		logger.info("option_group_id = " + option_group_id);
		logger.info("status = " + status);
		
		logger.info("http://" + civiurl +  rest_location + "?json=1&debug=1&version=3&entity=OptionValue&action=get&option_group_id="
				+ option_group_id + "&name=" + status + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  rest_location + "?json=1&debug=1&version=3&entity=OptionValue&action=get&option_group_id="
								+ option_group_id + "&name=" + status + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();

	}

	private static URI getBaseURI(String activity_id, String status_id, String api_key, String PHPSESSID, String key) {
		logger.info("getBaseURI ");
		logger.info("activity_id = " + activity_id);
		logger.info("status_id = " + status_id);
				
		logger.info("http://" + civiurl +  rest_location + "?json=1&debug=1&entity=Activity&action=update&status_id="
				+ status_id + "&id=" + activity_id + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  rest_location + "?json=1&debug=1&entity=Activity&action=update&status_id="
								+ status_id
								+ "&id="
								+ activity_id
								+ "&details=" + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();

	}

	
	private static URI getAuthenticated() {

		logger.info(" *************** getAuthenticated");
		
		http://hack5.brisskit.le.ac.uk/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name=saj&pass=saj&key=c3d22d956e7c6531e750bdbe2ee3c115&json=1
			
		logger.info("http://" + civiurl + "/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name="+ name + "&pass="+ pass + "&key="+ key + "&json=1");

		return UriBuilder
				.fromUri("http://" + civiurl + "/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name="+ name + "&pass="+ pass + "&key="+ key + "&json=1").build();
	}
	
	private static URI createGroup(String groupname, String api_key, String PHPSESSID, String key) {

		logger.info(" ***************" + groupname);
		logger.info("http://" + civiurl +  rest_location + "?json=1&sequential=1&debug=1&entity=Group&action=create&title="
				+ groupname + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  rest_location + "?json=1&sequential=1&debug=1&entity=Group&action=create&title="
								+ groupname + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();
	}

	
	private static URI getContactId(String brisskitid, String api_key, String PHPSESSID, String key) {

		logger.info(" ***************" + brisskitid);
		//logger.info("http://" + civiurl +  rest_location_orig + "?json=1&sequential=1&debug=1&entity=Brisskit&action=get&id=" + brisskitid);
		logger.info("http://" + civiurl +  rest_location + "?json=1&sequential=1&debug=1&entity=Contact&action=get&custom_2=" + brisskitid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		// Ensure that custom_2 is correct, could be custom_3 4 5 6, we dont know
		///civicrm/civicrm/ajax/rest?json=1&sequential=1&debug=1&&entity=CustomField&action=get&name=BRISSkit_ID
				
		
		return UriBuilder
				.fromUri("http://" + civiurl +  rest_location + "?json=1&sequential=1&debug=1&entity=Contact&action=get&custom_2=" + brisskitid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();
	}

	private static URI addContactToGroup(int groupid, String contactid, String api_key, String PHPSESSID, String key) {

		logger.info(groupid + " ***************" + contactid);
		logger.info("http://" + civiurl +  rest_location + "?json=1&sequential=1&debug=1&entity=GroupContact&action=create&group_id="
				+ groupid + "&contact_id=" + contactid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  rest_location + "?json=1&sequential=1&debug=1&entity=GroupContact&action=create&group_id="
								+ groupid + "&contact_id=" + contactid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();
	}

	//createdb
	
	public static synchronized void createdb() {
		String DB_CONN_STRING = "jdbc:jtds:sqlserver://" + url + ":1433/i2b2management;ssl=off;useCursors=true";	    
	    String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";
	    	    
	    String USER_NAME = "sa";
	    String PASSWORD = "root";
	    
	    // jdbc:jtds:sqlserver://winxp1;appName=RazorSQL;ssl=request;useCursors=true
	    
	    Connection result = null;
	    try {
	       Class.forName(DRIVER_CLASS_NAME).newInstance();
	    }
	    catch (Exception ex){
	       System.out.println("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
	    }

	    try {
	      result = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
	    }
	    catch (SQLException e){
	    	System.out.println( "Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
	    }
	    
	    
	    try {
			//Statement st = result.createStatement();
			
			String selectSQL = "create database "+currentproject+";";
			//PreparedStatement preparedStatement = result.prepareStatement(selectSQL);
			Statement st = result.createStatement(); 
			st.execute(selectSQL );
			 System.out.println("************** " + selectSQL);
			    
									
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
	    
	    
    }


	
	public static synchronized void getproject() {
		String DB_CONN_STRING = "jdbc:jtds:sqlserver://" + url + ":1433/i2b2management;ssl=off;useCursors=true";	    
	    String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";
	    	    
	    String USER_NAME = "sa";
	    String PASSWORD = "root";
	    
	    // jdbc:jtds:sqlserver://winxp1;appName=RazorSQL;ssl=request;useCursors=true
	    
	    Connection result = null;
	    try {
	       Class.forName(DRIVER_CLASS_NAME).newInstance();
	    }
	    catch (Exception ex){
	       System.out.println("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
	    }

	    try {
	      result = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
	    }
	    catch (SQLException e){
	    	System.out.println( "Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
	    }
	    
	    
	    try {
			//Statement st = result.createStatement();
			
			String selectSQL = "SELECT [projectcount] FROM [i2b2management].[dbo].[projects]";
			//PreparedStatement preparedStatement = result.prepareStatement(selectSQL);
			Statement st = result.createStatement(); 
			
			ResultSet rs = st.executeQuery(selectSQL );
			int projectcount;
			while (rs.next()) {
				projectcount = rs.getInt("projectcount");
				currentprojectcount = projectcount;
				currentproject = "i16project" + projectcount;
				
				System.out.println("****** currentprojectcount " + currentprojectcount);
				System.out.println("****** projectcount " + projectcount);
				
				Statement st1 = result.createStatement();
				st1.executeUpdate("UPDATE [i2b2management].[dbo].[projects]SET [projectcount] = " + (projectcount+1));
				
			}
			
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
	    
	    
    }

	
	  static public Connection getSimpleConnectionMSSQL() {
			
		    String DB_CONN_STRING = "jdbc:jtds:sqlserver://" + url + ":1433/i16project"+ currentprojectcount +";ssl=off;useCursors=true";	    
		    String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";
		    	    
		    String USER_NAME = "sa";
		    String PASSWORD = "root";
		    
		    // jdbc:jtds:sqlserver://winxp1;appName=RazorSQL;ssl=request;useCursors=true
		    
		    Connection result = null;
		    try {
		       Class.forName(DRIVER_CLASS_NAME).newInstance();
		    }
		    catch (Exception ex){
		       System.out.println("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
		    }

		    try {
		      result = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
		    }
		    catch (SQLException e){
		    	System.out.println( "Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
		    }
		    return result;
		  }  
	  
	  
	  
      public  void resetDatabasems()  
      {  
          String s            = new String();  
          StringBuffer sb = new StringBuffer();  
    
          try  
          {  
              FileReader fr = new FileReader(new File("/var/local/brisskit/i2b2/1ms.sql"));  
              
              BufferedReader br = new BufferedReader(fr);  
    
              while((s = br.readLine()) != null)  
              {  
            	  s = s.replaceAll("i16project2", "i16project" + currentprojectcount);
            	  s = s.replaceAll("SajProject1", "i16project" + currentprojectcount);
            	  s = s.replaceAll("QTProject1DS", "QTProject" + currentprojectcount + "DS");
            	  s = s.replaceAll("WProject1DS", "WProject" + currentprojectcount + "DS");
            	  s = s.replaceAll("OProject1DS", "OProject" + currentprojectcount + "DS");
            	  
            	 /* s = s.replaceAll("i16project2", "i16project3");
            	  s = s.replaceAll("SajProject1", "i16project3");
            	  s = s.replaceAll("QTProject1DS", "QTProject3DS");
            	  s = s.replaceAll("WProject1DS", "WProject3DS");
            	  s = s.replaceAll("OProject1DS", "OProject3DS"); */
            	  
                  sb.append(s);  
                  System.out.println(s);
                  
              }  
              br.close();  
    
              String[] inst = sb.toString().split(";");  
              
              //Replace i16project2, SajProject1, 
              //Replace QTProject1DS , WProject1DS , OProject1DS,
    
              Connection c = getSimpleConnectionMSSQL();  
              Statement st = c.createStatement();  
    
              for(int i = 0; i<inst.length; i++)  
              {  
                  if(!inst[i].trim().equals(""))  
                  {  
                     
                  	try
                  	{                   		
                  	st.executeUpdate(inst[i]);
                  	}
                  	catch(Exception e)  
                      {  
                  		System.out.println("*** Error : "+e.toString());  
                          System.out.println("*** " + inst[i]);  
                          System.out.println("*** Error : ");  
                          e.printStackTrace();  
                          System.out.println("################################################");  
                          System.out.println(sb.toString());
                      }
                      
                  	//System.out.println(">>"+inst[i]);  
                  }  
              }   
              
              st.setEscapeProcessing(false);
              st.execute ("create " +
  	                "PROCEDURE CREATE_TEMP_CONCEPT_TABLE (@tempConceptTableName VARCHAR(500), @errorMsg varchar(max) = NULL OUTPUT) " +
  	                "as " +
  	                "BEGIN " +
  	                "declare @exec_str nvarchar(MAX); " +
  	                "BEGIN TRY " +
  	                "BEGIN TRANSACTION " +
  	                "print @tempConceptTableName " +
  	                "set @exec_str = ' create table '  + @tempConceptTableName  +' ( " +
  	                "	                    CONCEPT_CD VARCHAR(50) NOT NULL , " +
  	                "	                	CONCEPT_PATH VARCHAR(700) NOT NULL , " +
  	                "	                	NAME_CHAR VARCHAR(2000), " +
  	                "	                	CONCEPT_BLOB text, " +
  	                "	                	UPDATE_DATE datetime, " +
  	                "	                	DOWNLOAD_DATE DATEtime, " +
  	                "	                	IMPORT_DATE DATEtime, " +
  	                "	                	SOURCESYSTEM_CD VARCHAR(50) " +
  	                "	                	 )'; " +
  	                "	                exec sp_executesql  @exec_str; " +
  	                "set  @exec_str = 'CREATE INDEX idx_' + @tempConceptTableName + '_pat_id ON ' +  @tempConceptTableName + '  (CONCEPT_PATH)'; " +
  	                "exec sp_executesql  @exec_str; " +
  	                "print @tempConceptTableName " +
  	                "COMMIT " +
  	                "  END TRY " +
  	                "  BEGIN CATCH " +
  	                "if @@TRANCOUNT > 0 " +
  	                "ROLLBACK " +
  	                "declare @errMsg nvarchar(4000), @errSeverity int " +
  	                "select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY(); " +
  	                "	                   set @errorMsg = @errMsg; " +
  	                "	                   RAISERROR(@errMsg,@errSeverity,1); " +
  	                "	                 END CATCH " +
  	                "END");  
              
              st.execute ("create  PROCEDURE CREATE_TEMP_EID_TABLE(@tempEnconterMappingTableName  VARCHAR(500),    @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN  declare @createSql nvarchar(MAX), @createIndexSql nvarchar(MAX); BEGIN TRY BEGIN TRANSACTION set @createSql = 'create table ' +  @tempEnconterMappingTableName + ' ( 	ENCOUNTER_MAP_ID       	VARCHAR(200) NOT NULL,     ENCOUNTER_MAP_ID_SOURCE	VARCHAR(50) NOT NULL,     PATIENT_MAP_ID          VARCHAR(200),  	PATIENT_MAP_ID_SOURCE   VARCHAR(50),      ENCOUNTER_ID       	    VARCHAR(200) NOT NULL,     ENCOUNTER_ID_SOURCE     VARCHAR(50) ,     ENCOUNTER_NUM           INT,      ENCOUNTER_MAP_ID_STATUS    VARCHAR(50),     PROCESS_STATUS_FLAG     CHAR(1), 	UPDATE_DATE DATETIME,  	DOWNLOAD_DATE DATETIME,  	IMPORT_DATE DATETIME,  	SOURCESYSTEM_CD VARCHAR(50) )';  exec sp_executesql @createSql;   set @createIndexSql =  'CREATE INDEX idx_' + @tempEnconterMappingTableName + '_eid_id ON ' + @tempEnconterMappingTableName + '  (  ENCOUNTER_ID, ENCOUNTER_ID_SOURCE,ENCOUNTER_MAP_ID, ENCOUNTER_MAP_ID_SOURCE,ENCOUNTER_NUM   )'; exec sp_executesql @createIndexSql;  set @createIndexSql =  'CREATE CLUSTERED INDEX idx_' + @tempEnconterMappingTableName + '_stateid_id ON ' + @tempEnconterMappingTableName + '  ( PROCESS_STATUS_FLAG)'; exec sp_executesql @createIndexSql;   COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              st.execute ("create  PROCEDURE CREATE_TEMP_MODIFIER_TABLE (@tempModifierTableName VARCHAR(500), @errorMsg varchar(max) = NULL OUTPUT)  as  BEGIN  declare @exec_str nvarchar(MAX);  BEGIN TRY BEGIN TRANSACTION print @tempModifierTableName  set @exec_str = ' create table '  + @tempModifierTableName  +' (         MODIFIER_CD VARCHAR(50) NOT NULL ,  	MODIFIER_PATH VARCHAR(700) NOT NULL ,  	NAME_CHAR VARCHAR(2000),  	MODIFIER_BLOB text,  	UPDATE_DATE datetime,  	DOWNLOAD_DATE DATEtime,  	IMPORT_DATE DATEtime,  	SOURCESYSTEM_CD VARCHAR(50) 	 )';   exec sp_executesql  @exec_str;   set  @exec_str = 'CREATE INDEX idx_' + @tempModifierTableName + '_pat_id ON '        +  @tempModifierTableName + '  (MODIFIER_PATH)';  exec sp_executesql  @exec_str;  print @tempModifierTableName    COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);  END CATCH END ");
              
              st.execute ("create  PROCEDURE CREATE_TEMP_PATIENT_TABLE(@tempPatientDimensionTableName  VARCHAR(500),    @errorMsg varchar(max) = NULL OUTPUT)  AS   BEGIN      declare @createSql  nvarchar(MAX);  BEGIN TRY BEGIN TRANSACTION set @createSql =  'create table ' +  @tempPatientDimensionTableName + ' ( 		PATIENT_ID VARCHAR(200),  		PATIENT_ID_SOURCE VARCHAR(50), 		PATIENT_NUM INT, 	    VITAL_STATUS_CD VARCHAR(50),  	    BIRTH_DATE DATETIME,  	    DEATH_DATE DATETIME,  	    SEX_CD VARCHAR(50),  	    AGE_IN_YEARS_NUM INT,  	    LANGUAGE_CD VARCHAR(50),  		RACE_CD VARCHAR(50),  		MARITAL_STATUS_CD VARCHAR(50),  		RELIGION_CD VARCHAR(50),  		ZIP_CD VARCHAR(10),  		STATECITYZIP_PATH VARCHAR(700),  		PATIENT_BLOB TEXT,  		UPDATE_DATE DATETIME,  		DOWNLOAD_DATE DATETIME,  		IMPORT_DATE DATETIME,  		SOURCESYSTEM_CD VARCHAR(50) 	)';    exec sp_executesql  @createSql;   set  @createSql  = 'CREATE INDEX idx_' + @tempPatientDimensionTableName + '_pat_id ON '        +  @tempPatientDimensionTableName + '  (PATIENT_ID,PATIENT_ID_SOURCE,PATIENT_NUM)';  exec sp_executesql  @createSql;  COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              st.execute ("create  PROCEDURE CREATE_TEMP_PID_TABLE(@tempPatientMappingTableName  VARCHAR(500), @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN  declare @createSql nvarchar(MAX), @createIndexSql nvarchar(MAX); BEGIN TRY BEGIN TRANSACTION set @createSql =  'create table ' +  @tempPatientMappingTableName + ' ( 	    PATIENT_MAP_ID VARCHAR(200),  		PATIENT_MAP_ID_SOURCE VARCHAR(50),  		PATIENT_ID_STATUS VARCHAR(50),  		PATIENT_ID  VARCHAR(200), 	    PATIENT_ID_SOURCE varchar(50), 		PATIENT_NUM INT,          PATIENT_MAP_ID_STATUS VARCHAR(50), 		PROCESS_STATUS_FLAG CHAR(1),  		UPDATE_DATE DATETIME,  		DOWNLOAD_DATE DATETIME,  		IMPORT_DATE DATETIME,  		SOURCESYSTEM_CD VARCHAR(50) 	 )'; exec sp_executesql @createSql;   set @createIndexSql =  'CREATE INDEX idx_' + @tempPatientMappingTableName + '_pid_id ON ' + @tempPatientMappingTableName + '  ( PATIENT_ID, PATIENT_ID_SOURCE )'; exec sp_executesql @createIndexSql;  set @createIndexSql =  'CREATE INDEX idx_' + @tempPatientMappingTableName + 'map_pid_id ON ' + @tempPatientMappingTableName + '  ( PATIENT_ID,PATIENT_ID_SOURCE,PATIENT_MAP_ID, PATIENT_MAP_ID_SOURCE,PATIENT_NUM )'; exec sp_executesql @createIndexSql;   set @createIndexSql =  'CREATE CLUSTERED INDEX idx_' + @tempPatientMappingTableName + 'stat_pid_id ON ' + @tempPatientMappingTableName + '  ( process_status_flag )'; exec sp_executesql @createIndexSql; COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              st.execute ("create PROCEDURE CREATE_TEMP_PROVIDER_TABLE(@tempProviderTableName  VARCHAR(500),     @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN   declare @createSql nvarchar(MAX); BEGIN TRY BEGIN TRANSACTION set @createSql =  'create table ' +  @tempProviderTableName + ' (   PROVIDER_ID VARCHAR(50) NOT NULL,  	PROVIDER_PATH VARCHAR(700) NOT NULL,  	NAME_CHAR VARCHAR(2000),  	PROVIDER_BLOB TEXT,  	UPDATE_DATE DATETIME,  	DOWNLOAD_DATE DATETIME,  	IMPORT_DATE DATETIME,  	SOURCESYSTEM_CD VARCHAR(50),  	UPLOAD_ID INT )'; exec  sp_executesql @createSql;  set @createSql =  'CREATE INDEX idx_' + @tempProviderTableName + '_ppath_id ON ' + @tempProviderTableName + '  (PROVIDER_PATH)';  exec  sp_executesql @createSql;  COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              st.execute ("create  PROCEDURE CREATE_TEMP_TABLE(@tempTableName  VARCHAR(500), @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN    declare @createSql nvarchar(MAX),@createIndexSql nvarchar(MAX);  BEGIN TRY BEGIN TRANSACTION 	set @createSql = 'create table '+ @tempTableName +'  ( 		encounter_num  INT, 		encounter_id varchar(200) not null,          encounter_id_source varchar(50) not null, 		concept_cd 	 VARCHAR(50) not null,          patient_num INT,  		patient_id  varchar(200) not null,         patient_id_source  varchar(50) not null, 	    Provider_Id    	varchar(50) NOT NULL, 	    Start_Date     	datetime NOT NULL, 	    Modifier_Cd    	varchar(100) NOT NULL, 		instance_num    int, 	    ValType_Cd     	varchar(50) NULL, 	   TVal_Char      	varchar(255) NULL, 	   NVal_Num       	decimal(18,5) NULL, 	   ValueFlag_Cd   	varchar(50) NULL, 	   Quantity_Num   	decimal(18,5) NULL, 	   Units_Cd       	varchar(50) NULL, 	   End_Date       	datetime NULL, 	   Location_Cd    	varchar(50) NULL, 	   Observation_Blob text NULL, 	   Confidence_Num 	decimal(18,5) NULL,  	   update_date  DATETIME, 	   download_date DATETIME,  	   import_date DATETIME, 	   sourcesystem_cd VARCHAR(50),  	   upload_id INT 	)';    exec  sp_executesql @createSql;          set @createIndexSql =  'CREATE INDEX idx_' + @tempTableName + '_pk ON ' + @tempTableName + '  ( encounter_num,patient_num,concept_cd,provider_id,start_date,modifier_cd,instance_num )';    exec  sp_executesql @createIndexSql;         set @createIndexSql =  'CREATE INDEX idx_' + @tempTableName + '_enc_pat_id ON ' + @tempTableName + '  (encounter_id,encounter_id_source, patient_id,patient_id_source )';        exec  sp_executesql @createIndexSql; COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH     END; ");
              
              st.execute ("create  PROCEDURE CREATE_TEMP_VISIT_TABLE(@tempTableName  VARCHAR(500), @errorMsg varchar(max) = NULL OUTPUT)  AS   BEGIN    declare @createSql nvarchar(MAX),@createIndexSql nvarchar(MAX);     BEGIN TRY BEGIN TRANSACTION 	set @createSql =  'create table ' +  @tempTableName + ' ( 		encounter_id 			VARCHAR(200) not null, 		encounter_id_source 	VARCHAR(50) not null,  		patient_id  			VARCHAR(200) not null, 		patient_id_source 		VARCHAR(50) not null, 		encounter_num	 		    INT,  		inout_cd   			VARCHAR(50), 		location_cd 			VARCHAR(50), 		location_path 			VARCHAR(900),  		start_date   			DATETIME,   		end_date    			DATETIME,  		visit_blob 				TEXT,  		update_date  			DATETIME, 		download_date 			DATETIME,  		import_date 			DATETIME, 		sourcesystem_cd 		VARCHAR(50) 	)';  	exec sp_executesql @createSql; 	     set @createIndexSql = 'CREATE INDEX idx_' + @tempTableName + '_enc_id ON ' + @tempTableName + '  (  encounter_id,encounter_id_source,patient_id,patient_id_source )';          exec sp_executesql @createIndexSql;          set @createIndexSql =  'CREATE INDEX idx_' + @tempTableName + '_patient_id ON ' + @tempTableName + '  ( patient_id,patient_id_source )';     exec sp_executesql @createIndexSql;      COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              st.execute ("create   PROCEDURE  INSERT_CONCEPT_FROMTEMP (@tempConceptTableName VARCHAR(500), @upload_id int, @errorMsg VARCHAR(MAX) = NULL OUTPUT)  AS   BEGIN   declare @deleteDuplicateSql nvarchar(max);  declare @insertSql nvarchar(max);  declare @updateSql nvarchar(max); BEGIN TRY   BEGIN TRANSACTION 	 	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY concept_path,concept_cd ORDER BY concept_path,concept_cd ) AS RNUM FROM ' + @tempConceptTableName +')  delete  from deleteTempDup where rnum>1'; 	exec sp_executesql @deleteDuplicateSql;     set @updateSql = ' UPDATE concept_dimension  set  			 		name_char= temp.name_char,                     concept_blob= temp.concept_blob,                     update_date= temp.update_date,                     import_date = getdate(),                     DOWNLOAD_DATE=temp.DOWNLOAD_DATE, 					SOURCESYSTEM_CD=temp.SOURCESYSTEM_CD, 					UPLOAD_ID = '+ convert(nvarchar,@upload_id)+ ' 					from concept_dimension cd                      inner join ' + @tempConceptTableName + ' temp                     on  cd.concept_path = temp.concept_path                     where temp.update_date >= cd.update_date';        exec sp_executesql @updateSql;        	set @insertSql = 'insert into concept_dimension(concept_cd,concept_path,name_char,concept_blob, ' +                       ' update_date,download_date,import_date,sourcesystem_cd,upload_id)  ' +  			         ' select  concept_cd, concept_path,name_char,concept_blob, update_date,download_date, ' +                          ' getdate(),sourcesystem_cd,'+ convert(nvarchar,@upload_id) + ' from  ' + @tempConceptTableName +  ' temp ' + 					' where not exists (select concept_cd from concept_dimension cd where ' +  				    ' cd.concept_path = temp.concept_path) ';      exec sp_executesql @insertSql;    COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;     RAISERROR(@errMsg,@errSeverity,1);   END CATCH  END;        ");
              
              st.execute ("CREATE PROCEDURE INSERT_EID_MAP_FROMTEMP (@tempEidTableName VARCHAR(500),  @upload_id INT, @errorMsg VARCHAR(MAX) = NULL OUTPUT)  AS BEGIN  declare @existingEncounterNum varchar(32);  declare  @maxEncounterNum int;  declare @deleteDuplicateSql nvarchar(MAX);  declare  @sql_stmt  nvarchar(MAX);   declare  @disEncounterId varchar(200);  declare  @disEncounterIdSource varchar(50);    BEGIN TRY  set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY encounter_map_id,encounter_map_id_source,encounter_id,encounter_id_source    ORDER BY encounter_map_id,encounter_map_id_source,encounter_id,encounter_id_source ) AS RNUM FROM ' + @tempEidTableName +')  delete  from deleteTempDup where rnum>1'; exec sp_executesql @deleteDuplicateSql;     select @maxEncounterNum = isnull(max(encounter_num),0) from encounter_mapping with (UPDLOCK);   SELECT @sql_stmt = 'DECLARE my_cur INSENSITIVE CURSOR FOR ' +               ' SELECT distinct encounter_id,encounter_id_source from ' +  @tempEidTableName  ; EXEC sp_executesql @sql_stmt;  OPEN my_cur;  FETCH NEXT FROM my_cur into @disEncounterId, @disEncounterIdSource ;  WHILE @@FETCH_STATUS = 0    BEGIN   BEGIN TRANSACTION   if  @disEncounterIdSource = 'HIVE'       begin     select @existingEncounterNum = encounter_num from encounter_mapping where encounter_num = @disEncounterId and encounter_ide_source = 'HIVE';         if @existingEncounterNum is not NULL     begin         set @sql_stmt =  ' update ' + @tempEidTableName  + ' set encounter_num = encounter_id, process_status_flag = ''P'' ' +          ' where encounter_id = @pdisEncounterId and not exists (select 1 from encounter_mapping em where em.encounter_ide = encounter_map_id ' +          ' and em.encounter_ide_source = encounter_map_id_source)';          EXEC sp_executesql @sql_stmt,N'@pdisEncounterId nvarchar(200)', @pdisEncounterId = @disEncounterId;     end     else      begin                  if @maxEncounterNum < @disEncounterId          begin             set @maxEncounterNum = @disEncounterId;         end;         set @sql_stmt = ' update ' + @tempEidTableName + ' set encounter_num = encounter_id, process_status_flag = ''P'' where ' +         ' encounter_id =  @pdisEncounterId and encounter_id_source = ''HIVE'' and not exists (select 1 from encounter_mapping em where em.encounter_ide = encounter_map_id ' +         ' and em.encounter_ide_source = encounter_map_id_source)';          EXEC sp_executesql @sql_stmt, N'@pdisEncounterId nvarchar(200)',@pdisEncounterId=@disEncounterId ;      end;          end  else   begin        select @existingEncounterNum = encounter_num  from encounter_mapping where encounter_ide = @disEncounterId and          encounter_ide_source = @disEncounterIdSource ;                  if @existingEncounterNum is not  NULL        begin             set @sql_stmt =  ' update ' + @tempEidTableName + ' set encounter_num = @pexistingEncounterNum, process_status_flag = ''P'' ' +              ' where encounter_id = @pdisEncounterId and not exists (select 1 from encounter_mapping em where em.encounter_ide = encounter_map_id ' +             ' and em.encounter_ide_source = encounter_map_id_source)' ;         EXEC sp_executesql @sql_stmt,N'@pexistingEncounterNum int, @pdisEncounterId nvarchar(200)',@pexistingEncounterNum=@existingEncounterNum ,           @pdisEncounterId=@disEncounterId;        end        else        begin              set @maxEncounterNum = @maxEncounterNum + 1 ;              set @sql_stmt =   ' insert into ' + @tempEidTableName + ' (encounter_map_id,encounter_map_id_source,encounter_id,encounter_id_source,encounter_num,process_status_flag              ,encounter_map_id_status,update_date,download_date,import_date,sourcesystem_cd)               values(@pmaxEncounterNum1,''HIVE'',@pmaxEncounterNum2,''HIVE'',@pmaxEncounterNum3,''P'',''A'',getdate(),getdate(),getdate(),''edu.harvard.i2b2.crc'')' ;             EXEC sp_executesql @sql_stmt, N'@pmaxEncounterNum1 int,@pmaxEncounterNum2 int, @pmaxEncounterNum3 int',              @pmaxEncounterNum1=@maxEncounterNum,@pmaxEncounterNum2=@maxEncounterNum,@pmaxEncounterNum3=@maxEncounterNum;               			 set @sql_stmt =   ' update ' + @tempEidTableName +' set encounter_num = @pmaxEncounterNum , process_status_flag = ''P'' ' +                ' where encounter_id = @pdisEncounterId and  not exists (select 1 from ' +               ' encounter_mapping em where em.encounter_ide = encounter_map_id ' +               ' and em.encounter_ide_source = encounter_map_id_source)' ;              EXEC sp_executesql @sql_stmt,N'@pmaxEncounterNum int,@pdisEncounterId nvarchar(200)',@pmaxEncounterNum=@maxEncounterNum , @pdisEncounterId=@disEncounterId;                     end  ;             end ;  commit; FETCH NEXT FROM my_cur into @disEncounterId, @disEncounterIdSource ; END ; CLOSE my_cur DEALLOCATE my_cur  BEGIN TRANSACTION  set @sql_stmt = ' update encounter_mapping set encounter_num = temp.encounter_id,     	patient_ide   =   temp.patient_map_id ,     	patient_ide_source  =	temp.patient_map_id_source ,     	encounter_ide_status	= temp.encounter_map_id_status  ,     	update_date = temp.update_date,     	download_date  = temp.download_date , 		import_date = getdate() ,     	sourcesystem_cd  = temp.sourcesystem_cd , 		upload_id = ' + convert(nvarchar,@upload_id) + '  		from encounter_mapping em            inner join ' + @tempEidTableName + ' temp                     on em.encounter_ide = temp.encounter_map_id and 			em.encounter_ide_source = temp.encounter_map_id_source      	where  temp.encounter_id_source = ''HIVE'' and temp.process_status_flag is null  and         isnull(em.update_date,0)<= isnull(temp.update_date,0) ' ; EXEC sp_executesql @sql_stmt;   set @sql_stmt =  ' insert into encounter_mapping (encounter_ide,encounter_ide_source,encounter_ide_status,encounter_num,update_date,download_date,import_date,sourcesystem_cd,upload_id)      select encounter_map_id,encounter_map_id_source,encounter_map_id_status,encounter_num,update_date,download_date,getdate(),sourcesystem_cd,' + convert(nvarchar,@upload_id) + ' from ' + @tempEidTableName + '       where process_status_flag = ''P'' ' ;  EXEC sp_executesql @sql_stmt; commit; END TRY  BEGIN CATCH    if @@TRANCOUNT > 0    begin       ROLLBACK    end    begin try    DEALLOCATE my_cur    end try    begin catch    end catch      declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH  end;");
              
              st.execute ("create  PROCEDURE   \"INSERT_ENCOUNTERVISIT_FROMTEMP\" (@tempTableName  VARCHAR(500), @upload_id int ,  @errorMsg varchar(max) = NULL OUTPUT)  AS   BEGIN  declare @deleteDuplicateSql nvarchar(MAX),   @insertSql nvarchar(MAX) ,  @updateSql nvarchar(MAX);  BEGIN TRY   BEGIN TRANSACTION    select max(encounter_num) from encounter_mapping with (UPDLOCK);   set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY encounter_id,encounter_id_source,patient_id,patient_id_source    ORDER BY encounter_id,encounter_id_source ) AS RNUM FROM ' + @tempTableName +')  delete  from deleteTempDup where rnum>1';  exec sp_executesql @deleteDuplicateSql; 	 set @insertSql = ' insert into encounter_mapping (encounter_ide,encounter_ide_source,encounter_num,patient_ide,patient_ide_source,encounter_ide_status, upload_id)      	(select distinctTemp.encounter_id, distinctTemp.encounter_id_source, distinctTemp.encounter_id,  distinctTemp.patient_id,distinctTemp.patient_id_source,''A'',  ' + convert(nvarchar,@upload_id) + ' 				from  					(select distinct encounter_id, encounter_id_source,patient_id,patient_id_source from ' + @tempTableName + '  temp 					where  				     not exists (select encounter_ide from encounter_mapping em where em.encounter_ide = temp.encounter_id and em.encounter_ide_source = temp.encounter_id_source) 					 and encounter_id_source = ''HIVE'' )   distinctTemp) ' ;      exec sp_executesql @insertSql;     	      	 	 set @updateSql =  ' UPDATE ' +  @tempTableName + ' SET encounter_num = (SELECT em.encounter_num 		     FROM encounter_mapping em 		     WHERE em.encounter_ide = '+  @tempTableName + '.encounter_id                      and em.encounter_ide_source = '+ @tempTableName +'.encounter_id_source  					 and isnull(em.patient_ide_source,'''') = isnull('+ @tempTableName +'.patient_id_source,'''') 				     and isnull(em.patient_ide,'''')= isnull('+ @tempTableName +'.patient_id,'''') 	 	    ) WHERE EXISTS (SELECT em.encounter_num  		     FROM encounter_mapping em 		     WHERE em.encounter_ide = '+ @tempTableName +'.encounter_id                      and em.encounter_ide_source = '+ @tempTableName +'.encounter_id_source 					 and isnull(em.patient_ide_source,'''') = isnull('+ @tempTableName +'.patient_id_source,'''') 				     and isnull(em.patient_ide,'''')= isnull('+ @tempTableName +'.patient_id,''''))';	       exec sp_executesql @updateSql;    exec sp_executesql @updateSql; set @updateSql = ' UPDATE visit_dimension  set  			 		inout_cd = temp.inout_cd, 			 		location_cd = temp.location_cd, 			 		location_path = temp.location_path, 			 		start_date = temp.start_date, 			 		end_date = temp.end_date, 			 		visit_blob = temp.visit_blob, 			 		update_date = temp.update_date, 			 		download_date = temp.download_date, 			 		import_date = getdate(), 			 		sourcesystem_cd = temp.sourcesystem_cd                     from visit_dimension vd                      inner join ' + @tempTableName + ' temp                     on  vd.encounter_num = temp.encounter_num 				    where temp.update_date >= vd.update_date '; exec sp_executesql @updateSql;    set @insertSql =  ' insert into visit_dimension  (encounter_num,patient_num,START_DATE,END_DATE,INOUT_CD,LOCATION_CD,VISIT_BLOB,UPDATE_DATE,DOWNLOAD_DATE,IMPORT_DATE,SOURCESYSTEM_CD, UPLOAD_ID) 	               select temp.encounter_num, pm.patient_num, 					temp.START_DATE,temp.END_DATE,temp.INOUT_CD,temp.LOCATION_CD,temp.VISIT_BLOB, 					temp.update_date, 					temp.download_date, 					getdate(),  					temp.sourcesystem_cd, 		            '+ convert(nvarchar,@upload_id) +' 			from  				' + @tempTableName + '  temp , patient_mapping pm  			where                   temp.encounter_num is not null and  		      	 not exists (select encounter_num from visit_dimension vd where vd.encounter_num = temp.encounter_num) and  				 pm.patient_ide = temp.patient_id and pm.patient_ide_source = temp.patient_id_source 	 ';  exec sp_executesql @insertSql;  COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH     END;     ");
              
              st.execute ("create   PROCEDURE  INSERT_MODIFIER_FROMTEMP (@tempModifierTableName VARCHAR(500), @upload_id int, @errorMsg VARCHAR(MAX) = NULL OUTPUT)  AS   BEGIN   declare @deleteDuplicateSql nvarchar(max);  declare @insertSql nvarchar(max);  declare @updateSql nvarchar(max); BEGIN TRY   BEGIN TRANSACTION 	  	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY modifier_path,modifier_cd ORDER BY modifier_path,modifier_cd ) AS RNUM FROM ' + @tempModifierTableName +')  delete  from deleteTempDup where rnum>1'; 	exec sp_executesql @deleteDuplicateSql;     set @updateSql = ' UPDATE modifier_dimension  set  			 		name_char= temp.name_char,                     modifier_blob= temp.modifier_blob,                     update_date= temp.update_date,                     import_date = getdate(),                     DOWNLOAD_DATE=temp.DOWNLOAD_DATE, 					SOURCESYSTEM_CD=temp.SOURCESYSTEM_CD, 					UPLOAD_ID = '+ convert(nvarchar,@upload_id)+ ' 					from modifier_dimension cd                      inner join ' + @tempModifierTableName + ' temp                     on  cd.modifier_path = temp.modifier_path                     where temp.update_date >= cd.update_date';        exec sp_executesql @updateSql;        	set @insertSql = 'insert into modifier_dimension(modifier_cd,modifier_path,name_char,modifier_blob, ' +                       ' update_date,download_date,import_date,sourcesystem_cd,upload_id)  ' +  			         ' select  modifier_cd, modifier_path,name_char,modifier_blob, update_date,download_date, ' +                          ' getdate(),sourcesystem_cd,'+ convert(nvarchar,@upload_id) + ' from  ' + @tempModifierTableName +  ' temp ' + 					' where not exists (select modifier_cd from modifier_dimension cd where ' +  				    ' cd.modifier_path = temp.modifier_path) ';      exec sp_executesql @insertSql;    COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;     RAISERROR(@errMsg,@errSeverity,1);   END CATCH  END;        ");
              
              st.execute ("create  PROCEDURE   INSERT_PATIENT_FROMTEMP (@tempPatientTableName  VARCHAR(500),  @upload_id  INT,  @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN  	declare @insertPmSql nvarchar(MAX), @insertSql nvarchar(MAX);  	declare @updateSql nvarchar(MAX); BEGIN TRY   BEGIN TRANSACTION   select max(patient_num) from patient_mapping with (UPDLOCK);    	set @insertPmSql =  'insert into patient_mapping (patient_ide,patient_ide_source,patient_ide_status, patient_num,upload_id)  		select distinct temp.patient_id, temp.patient_id_source,''A'',temp.patient_id , '+ convert(nvarchar,@upload_id)+ '  		from ' + @tempPatientTableName +'  temp  		where temp.patient_id_source = ''HIVE'' and     		not exists (select patient_ide from patient_mapping pm where pm.patient_num = temp.patient_id and pm.patient_ide_source = temp.patient_id_source)  		';      print @insertPmSql;     exec sp_executesql  @insertPmSql;    set @updateSql =  'UPDATE ' + @tempPatientTableName +    ' SET patient_num = (SELECT pm.patient_num 		     FROM patient_mapping pm 		     WHERE pm.patient_ide = ' +  @tempPatientTableName +'.patient_id                      and pm.patient_ide_source = '+ @tempPatientTableName+'.patient_id_source 	 	    ) WHERE EXISTS (SELECT pm.patient_num  		     FROM patient_mapping pm 		     WHERE pm.patient_ide = '+ @tempPatientTableName+'.patient_id                      and pm.patient_ide_source = '+ @tempPatientTableName+'.patient_id_source)';		       exec sp_executesql @updateSql;  set @updateSql =  'UPDATE ' +  @tempPatientTableName +     ' SET patient_num = convert(numeric,patient_id)        WHERE patient_id_source = ''HIVE''';  exec sp_executesql @updateSql;  set @updateSql = ' UPDATE patient_dimension  set  			 		VITAL_STATUS_CD= temp.VITAL_STATUS_CD,                     BIRTH_DATE= temp.BIRTH_DATE,                     DEATH_DATE= temp.DEATH_DATE,                     SEX_CD= temp.SEX_CD,                     AGE_IN_YEARS_NUM=temp.AGE_IN_YEARS_NUM,                     LANGUAGE_CD=temp.LANGUAGE_CD,                     RACE_CD=temp.RACE_CD,                     MARITAL_STATUS_CD=temp.MARITAL_STATUS_CD,                     RELIGION_CD=temp.RELIGION_CD,                     ZIP_CD=temp.ZIP_CD, 					STATECITYZIP_PATH =temp.STATECITYZIP_PATH, 					PATIENT_BLOB=temp.PATIENT_BLOB, 					UPDATE_DATE=temp.UPDATE_DATE, 					DOWNLOAD_DATE=temp.DOWNLOAD_DATE, 					SOURCESYSTEM_CD=temp.SOURCESYSTEM_CD, 					UPLOAD_ID = '+ convert(nvarchar,@upload_id)+ ' 					from patient_dimension pd                      inner join ' + @tempPatientTableName + ' temp                     on  pd.patient_num = temp.patient_num                     where temp.update_date >= pd.update_date ';  print @updateSql;  exec sp_executesql @updateSql;   	set @insertSql = ' insert into patient_dimension( 					PATIENT_NUM, 					VITAL_STATUS_CD,                     BIRTH_DATE,                     DEATH_DATE,                     SEX_CD,                     AGE_IN_YEARS_NUM,                     LANGUAGE_CD,                     RACE_CD,                     MARITAL_STATUS_CD,                     RELIGION_CD,                     ZIP_CD, 					STATECITYZIP_PATH, 					PATIENT_BLOB, 					UPDATE_DATE, 					DOWNLOAD_DATE, 					SOURCESYSTEM_CD, 					import_date, 	                upload_id  					)  			 	 select   					temp.PATIENT_NUM, 					temp.VITAL_STATUS_CD,                     temp.BIRTH_DATE,                     temp.DEATH_DATE,                     temp.SEX_CD,                     temp.AGE_IN_YEARS_NUM,                     temp.LANGUAGE_CD,                     temp.RACE_CD,                     temp.MARITAL_STATUS_CD,                     temp.RELIGION_CD,                     temp.ZIP_CD, 					temp.STATECITYZIP_PATH, 					temp.PATIENT_BLOB, 					temp.UPDATE_DATE, 					temp.DOWNLOAD_DATE, 					temp.SOURCESYSTEM_CD, 					getdate(), 	     			' + convert(nvarchar,@upload_id)+'  	     			 from ' + @tempPatientTableName + ' temp 				 where not exists (select pd.patient_num from patient_dimension pd                    where temp.patient_num  = pd.patient_num  )                   and temp.patient_num is not null';                    print @insertSql;  exec sp_executesql @insertSql;   COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;      ");
              
              st.execute ("create  PROCEDURE          [dbo].[INSERT_PATIENT_MAP_FROMTEMP] (@tempPatientTableName  VARCHAR(500),  @upload_id  INT,   @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN  	declare @insertPmSql nvarchar(MAX), @insertSql nvarchar(MAX);  	declare @updateSql nvarchar(MAX); BEGIN TRY   BEGIN TRANSACTION   select max(patient_num) from patient_mapping with (UPDLOCK);   	 	set @insertPmSql =  'insert into patient_mapping (patient_ide,patient_ide_source,patient_ide_status, patient_num,upload_id)  		select distinct temp.patient_id, temp.patient_id_source,''A'',temp.patient_id , '+ convert(nvarchar,@upload_id)+ '  		from ' + @tempPatientTableName +'  temp  		where temp.patient_id_source = ''HIVE'' and     		not exists (select patient_ide from patient_mapping pm where pm.patient_num = temp.patient_id and pm.patient_ide_source = temp.patient_id_source)  		';      print @insertPmSql;        exec sp_executesql  @insertPmSql;    set @updateSql =  'UPDATE ' + @tempPatientTableName +    ' SET patient_num = (SELECT pm.patient_num 		     FROM patient_mapping pm 		     WHERE pm.patient_ide = ' +  @tempPatientTableName +'.patient_id                      and pm.patient_ide_source = '+ @tempPatientTableName+'.patient_id_source 	 	    ) WHERE EXISTS (SELECT pm.patient_num  		     FROM patient_mapping pm 		     WHERE pm.patient_ide = '+ @tempPatientTableName+'.patient_id                      and pm.patient_ide_source = '+ @tempPatientTableName+'.patient_id_source)';		       exec sp_executesql @updateSql;  set @updateSql =  'UPDATE ' +  @tempPatientTableName +     ' SET patient_num = convert(numeric,patient_id)        WHERE patient_id_source = ''HIVE''';  exec sp_executesql @updateSql;  set @updateSql = ' UPDATE patient_dimension  set  			 		VITAL_STATUS_CD= temp.VITAL_STATUS_CD,                     BIRTH_DATE= temp.BIRTH_DATE,                     DEATH_DATE= temp.DEATH_DATE,                     SEX_CD= temp.SEX_CD,                     AGE_IN_YEARS_NUM=temp.AGE_IN_YEARS_NUM,                     LANGUAGE_CD=temp.LANGUAGE_CD,                     RACE_CD=temp.RACE_CD,                     MARITAL_STATUS_CD=temp.MARITAL_STATUS_CD,                     RELIGION_CD=temp.RELIGION_CD,                     ZIP_CD=temp.ZIP_CD, 					STATECITYZIP_PATH =temp.STATECITYZIP_PATH, 					PATIENT_BLOB=temp.PATIENT_BLOB, 					UPDATE_DATE=temp.UPDATE_DATE, 					DOWNLOAD_DATE=temp.DOWNLOAD_DATE, 					SOURCESYSTEM_CD=temp.SOURCESYSTEM_CD, 					UPLOAD_ID = '+ convert(nvarchar,@upload_id)+ ' 					from patient_dimension pd                      inner join ' + @tempPatientTableName + ' temp                     on  pd.patient_num = temp.patient_num                     where temp.update_date > pd.update_date ';  print @updateSql;  exec sp_executesql @updateSql;   	set @insertSql = ' insert into patient_dimension( 					PATIENT_NUM, 					VITAL_STATUS_CD,                     BIRTH_DATE,                     DEATH_DATE,                     SEX_CD,                     AGE_IN_YEARS_NUM,                     LANGUAGE_CD,                     RACE_CD,                     MARITAL_STATUS_CD,                     RELIGION_CD,                     ZIP_CD, 					STATECITYZIP_PATH, 					PATIENT_BLOB, 					UPDATE_DATE, 					DOWNLOAD_DATE, 					SOURCESYSTEM_CD, 					import_date, 	                upload_id  					)  			 	 select   					temp.PATIENT_NUM, 					temp.VITAL_STATUS_CD,                     temp.BIRTH_DATE,                     temp.DEATH_DATE,                     temp.SEX_CD,                     temp.AGE_IN_YEARS_NUM,                     temp.LANGUAGE_CD,                     temp.RACE_CD,                     temp.MARITAL_STATUS_CD,                     temp.RELIGION_CD,                     temp.ZIP_CD, 					temp.STATECITYZIP_PATH, 					temp.PATIENT_BLOB, 					temp.UPDATE_DATE, 					temp.DOWNLOAD_DATE, 					temp.SOURCESYSTEM_CD, 					getdate(), 	     			' + convert(nvarchar,@upload_id)+'  	     			 from ' + @tempPatientTableName + ' temp 				 where not exists (select pd.patient_num from patient_dimension pd                    where temp.patient_num  = pd.patient_num                    and temp.patient_num is not null)';                    print @insertSql;  exec sp_executesql @insertSql;   COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;      ");
              
              st.execute ("create PROCEDURE  INSERT_PID_MAP_FROMTEMP (@tempPatientMapTableName VARCHAR(500), @upload_id INT,     @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN    declare @deleteDuplicateSql nvarchar(MAX),     @insertSql nvarchar(MAX);   declare  @existingPatientNum nvarchar(32); declare @maxPatientNum int; declare @disPatientId nvarchar(200);  declare @disPatientIdSource nvarchar(50); declare @sql nvarchar(MAX); BEGIN TRY      	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY patient_map_id,patient_map_id_source,patient_id,patient_id_source    ORDER BY patient_map_id,patient_map_id_source,patient_id,patient_id_source ) AS RNUM FROM ' + @tempPatientMapTableName +')  delete  from deleteTempDup where rnum>1';  exec sp_executesql @deleteDuplicateSql; 	  select @maxPatientNum = isnull(max(patient_num),0) from patient_mapping with (UPDLOCK);  SELECT @sql = 'DECLARE my_cur INSENSITIVE CURSOR FOR ' +               ' SELECT distinct patient_id,patient_id_source from ' +  @tempPatientMapTableName  ; EXEC sp_executesql @sql  OPEN my_cur  FETCH NEXT FROM my_cur into @disPatientId, @disPatientIdSource ;  WHILE @@FETCH_STATUS = 0   BEGIN  BEGIN TRANSACTION   if  @disPatientIdSource = 'HIVE'     begin      select @existingPatientNum  = patient_num  from patient_mapping where patient_num = @disPatientId and patient_ide_source = 'HIVE';          if @existingPatientNum IS NOT NULL      begin          set @sql = ' update ' + @tempPatientMapTableName + ' set patient_num = patient_id, process_status_flag = ''P'' ' +          ' where patient_id =  @pdisPatientId   and not exists (select 1 from patient_mapping pm where pm.patient_ide = patient_map_id ' +          ' and pm.patient_ide_source = patient_map_id_source)' ;           EXEC sp_executesql @sql, N'@pdisPatientId nvarchar(200)',  @pdisPatientId = @disPatientId;       end      else       begin         if @maxPatientNum < @disPatientId          begin            set @maxPatientNum = @disPatientId;         end;         set @sql = ' update ' + @tempPatientMapTableName +' set patient_num = patient_id, process_status_flag = ''P'' where ' +         ' patient_id = @pdisPatientId and patient_id_source = ''HIVE'' and not exists (select 1 from patient_mapping pm where pm.patient_ide = patient_map_id ' +         ' and pm.patient_ide_source = patient_map_id_source)';         EXEC sp_executesql @sql, N'@pdisPatientId nvarchar(200)', @pdisPatientId=@disPatientId;       end;     print ' HIVE ';   end;  else   begin        select @existingPatientNum = patient_num   from patient_mapping where patient_ide = @disPatientId and          patient_ide_source = @disPatientIdSource ;                 if @existingPatientNum is not NULL        begin           set @sql = ' update ' + @tempPatientMapTableName +' set patient_num = @pexistingPatientNum , process_status_flag = ''P'' ' +              ' where patient_id = @pdisPatientId and not exists (select 1 from patient_mapping pm where pm.patient_ide = patient_map_id ' +              ' and pm.patient_ide_source = patient_map_id_source)' ;              EXEC sp_executesql @sql,N'@pexistingPatientNum int, @pdisPatientId nvarchar(200)',@pexistingPatientNum=@existingPatientNum,@pdisPatientId=disPatientId;        end        else         begin              set @maxPatientNum = @maxPatientNum + 1 ;              set @sql = 'insert into ' + @tempPatientMapTableName + ' (patient_map_id,patient_map_id_source,patient_id,patient_id_source,patient_num,process_status_flag ' +               ',patient_map_id_status,update_date,download_date,import_date,sourcesystem_cd ) ' +                ' values(@pmaxPatientNum1,''HIVE'',@pmaxPatientNum2,''HIVE'',@pmaxPatientNum3,''P'',''A'',getdate(),getdate(),getdate(),''edu.harvard.i2b2.crc'')' ;                EXEC sp_executesql  @sql ,N'@pmaxPatientNum1 int,@pmaxPatientNum2 int, 			@pmaxPatientNum3 int',@pmaxPatientNum1 = @maxPatientNum ,@pmaxPatientNum2 = @maxPatientNum,@pmaxPatientNum3 = @maxPatientNum;              set @sql =  'update ' + @tempPatientMapTableName + ' set patient_num =  @pmaxPatientNum , process_status_flag = ''P'' ' +                ' where patient_id = @pdisPatientId and  not exists (select 1 from ' +              ' patient_mapping pm where pm.patient_ide = patient_map_id ' +              ' and pm.patient_ide_source = patient_map_id_source)' ;               EXEC sp_executesql @sql,N'@pmaxPatientNum int,@pdisPatientId nvarchar(200)',@pmaxPatientNum = @maxPatientNum, @pdisPatientId=@disPatientId  ;           end;           end ; commit;  FETCH NEXT FROM my_cur into @disPatientId, @disPatientIdSource ; END  CLOSE my_cur DEALLOCATE my_cur BEGIN TRANSACTION     set @sql = ' update patient_mapping set patient_num = temp.patient_id,     	patient_ide_status	= temp.patient_map_id_status  ,     	update_date = temp.update_date,     	download_date  = temp.download_date , 		import_date = getdate() ,     	sourcesystem_cd  = temp.sourcesystem_cd , 		upload_id = ' + convert(nvarchar,@upload_id) + '  		from patient_mapping pm          inner join ' + @tempPatientMapTableName + ' temp         on  pm.patient_ide = temp.patient_map_id and pm.patient_ide_source = temp.patient_map_id_source         where temp.patient_id_source = ''HIVE'' and temp.process_status_flag is null  and isnull(temp.update_date,0) >= isnull(pm.update_date,0)';  EXEC sp_executesql @sql;  set @sql = ' insert into patient_mapping (patient_ide,patient_ide_source,patient_ide_status,patient_num,update_date,download_date,import_date,sourcesystem_cd,upload_id) ' +      ' select patient_map_id,patient_map_id_source,patient_map_id_status,patient_num,update_date,download_date,getdate(),sourcesystem_cd,' + convert(nvarchar,@upload_id) + ' from '+@tempPatientMapTableName+       ' where process_status_flag = ''P'' ' ; EXEC sp_executesql @sql;  commit;    END TRY BEGIN CATCH    if @@TRANCOUNT > 0    begin      ROLLBACK    end        begin try    DEALLOCATE my_cur    end try    begin catch    end catch     declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              //st.execute ("create PROCEDURE  INSERT_PID_MAP_FROMTEMP (@tempPatientMapTableName VARCHAR(500), @upload_id INT)  AS   BEGIN    declare @deleteDuplicateSql nvarchar(MAX),     @insertSql nvarchar(MAX);   declare  @existingPatientNum nvarchar(32); declare @maxPatientNum int; declare @disPatientId nvarchar(200);  declare @disPatientIdSource nvarchar(50); declare @sql nvarchar(MAX); BEGIN TRY      	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY patient_id,patient_id_source,patient_num    ORDER BY patient_id,patient_id_source,patient_num ) AS RNUM FROM ' + @tempPatientMapTableName +')  delete  from deleteTempDup where rnum>1';  exec sp_executesql @deleteDuplicateSql; 	     select @maxPatientNum = max(patient_num) from patient_mapping with (UPDLOCK);     SELECT @sql = 'DECLARE my_cur INSENSITIVE CURSOR FOR ' +               ' SELECT distinct patient_id,patient_id_source from ' +  @tempPatientMapTableName  ; EXEC sp_executesql @sql  OPEN my_cur  FETCH NEXT FROM my_cur into @disPatientId, @disPatientIdSource ;  WHILE @@FETCH_STATUS = 0   BEGIN  BEGIN TRANSACTION   print @disPatientId + ' ' + @disPatientIdSource         if  @disPatientIdSource = 'HIVE'     begin      select @existingPatientNum  = patient_num  from patient_mapping where patient_num = @disPatientId and patient_ide_source = 'HIVE';          if @existingPatientNum IS NOT NULL      begin          print 'not null'         set @sql = ' update ' + @tempPatientMapTableName + ' set patient_num = patient_id, process_status_flag = ''P'' ' +          ' where patient_id =  @pdisPatientId   and not exists (select 1 from patient_mapping pm where pm.patient_ide = patient_map_id ' +          ' and pm.patient_ide_source = patient_map_id_source)' ;           EXEC sp_executesql @sql, N'@pdisPatientId nvarchar(200)',  @pdisPatientId = @disPatientId;          select @disPatientId;      end      else       begin         print 'null not exist HIVE' + @disPatientId         if @maxPatientNum < @disPatientId          begin            set @maxPatientNum = @disPatientId;         end;         set @sql = ' update ' + @tempPatientMapTableName +' set patient_num = patient_id, process_status_flag = ''P'' where ' +         ' patient_id = @pdisPatientId and patient_id_source = ''HIVE'' and not exists (select 1 from patient_mapping pm where pm.patient_ide = patient_map_id ' +         ' and pm.patient_ide_source = patient_map_id_source)';         EXEC sp_executesql @sql, N'@pdisPatientId nvarchar(200)', @pdisPatientId=@disPatientId;       end;     print ' HIVE ';   end;  else   begin        select @existingPatientNum = patient_num   from patient_mapping where patient_ide = @disPatientId and          patient_ide_source = @disPatientIdSource and patient_ide_status = 'A';                 if @existingPatientNum is not null         begin           set @sql = ' update ' + @tempPatientMapTableName +' set patient_num = @pexistingPatientNum , process_status_flag = ''P'' ' +              ' where patient_id = @pdisPatientId and not exists (select 1 from patient_mapping pm where pm.patient_ide = patient_map_id ' +              ' and pm.patient_ide_source = patient_map_id_source)' ;              EXEC sp_executesql @sql,N'@pexistingPatientNum int, @pdisPatientId nvarchar(200)',@pexistingPatientNum=@existingPatientNum,@pdisPatientId=disPatientId;        end        else         begin               print ' NOT HIVE and not present ' + @disPatientId;              set @maxPatientNum = @maxPatientNum + 1 ;              set @sql = 'insert into ' + @tempPatientMapTableName + ' (patient_map_id,patient_map_id_source,patient_id,patient_id_source,patient_num,process_status_flag ' +               ' ) ' +                ' values(@pmaxPatientNum1,''HIVE'',@pmaxPatientNum2,''HIVE'',@pmaxPatientNum3,''P'')' ;                EXEC sp_executesql  @sql ,N'@pmaxPatientNum1 int,@pmaxPatientNum2 int, 			@pmaxPatientNum3 int',@pmaxPatientNum1 = @maxPatientNum ,@pmaxPatientNum2 = @maxPatientNum,@pmaxPatientNum3 = @maxPatientNum;              set @sql =  'update ' + @tempPatientMapTableName + ' set patient_num =  @pmaxPatientNum , process_status_flag = ''P'' ' +                ' where patient_id = @pdisPatientId and  not exists (select 1 from ' +              ' patient_mapping pm where pm.patient_ide = patient_map_id ' +              ' and pm.patient_ide_source = patient_map_id_source)' ;               EXEC sp_executesql @sql,N'@pmaxPatientNum int,@pdisPatientId nvarchar(200)',@pmaxPatientNum = @maxPatientNum, @pdisPatientId=@disPatientId  ;           end;           end ; commit;  FETCH NEXT FROM my_cur into @disPatientId, @disPatientIdSource ; END  CLOSE my_cur DEALLOCATE my_cur BEGIN TRANSACTION set @sql = ' insert into patient_mapping (patient_ide,patient_ide_source,patient_ide_status,patient_num,update_date,download_date,import_date,sourcesystem_cd,upload_id) ' +      ' select patient_map_id,patient_map_id_source,''A'',patient_num,update_date,download_date,import_date,sourcesystem_cd,' + convert(nvarchar,@upload_id) + ' from '+@tempPatientMapTableName+       ' where process_status_flag = ''P'' ' ; EXEC sp_executesql @sql; commit;    END TRY BEGIN CATCH    if @@TRANCOUNT > 0    begin      ROLLBACK    end    deallocate my_cur;    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    RAISERROR(@errMsg,@errSeverity,1);   END CATCH END;");
              
              st.execute ("create  PROCEDURE  \"INSERT_PROVIDER_FROMTEMP\" (@tempProviderTableName VARCHAR(500), @upload_id INT,   @errorMsg varchar(max)  = NULL OUTPUT)  AS   BEGIN    declare @deleteDuplicateSql nvarchar(MAX), @insertSql nvarchar(MAX),@updateSql nvarchar(MAX);  BEGIN TRY BEGIN TRANSACTION 	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY provider_path    ORDER BY provider_path ) AS RNUM FROM ' + @tempProviderTableName +')  delete  from deleteTempDup where rnum>1';  exec sp_executesql @deleteDuplicateSql; 	 set @updateSql = ' UPDATE patient_dimension  set  			 	    provider_id = temp.provider_id,                     name_char = temp.name_char, 				    provider_blob = provider_blob,                     IMPORT_DATE=getdate(),                  	UPDATE_DATE=temp.UPDATE_DATE, 					DOWNLOAD_DATE=temp.DOWNLOAD_DATE, 					SOURCESYSTEM_CD=temp.SOURCESYSTEM_CD, 					UPLOAD_ID = '+ convert(nvarchar,@upload_id)+ ' 					from provider_dimension pd                      inner join ' + @tempProviderTableName + ' temp                     on  pd.provider_path = temp.provider_path                     where temp.update_date >= pd.update_date ';  print @updateSql;      		set @insertSql =  'insert into provider_dimension  (provider_id,provider_path,name_char,provider_blob,update_date,download_date,import_date,sourcesystem_cd,upload_id) 			    select  provider_id,provider_path,                          name_char,provider_blob,                         update_date,download_date,                         getdate(),sourcesystem_cd, ' + convert(nvarchar,@upload_id) +  ' 	                    from ' + @tempProviderTableName + '  temp 					where not exists (select provider_id from provider_dimension pd where pd.provider_path = temp.provider_path) 				';  exec sp_executesql @insertSql;  COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);  END CATCH END;     ");
              
              st.execute ("CREATE PROCEDURE REMOVE_TEMP_TABLE(@tempTableName VARCHAR(500))  AS BEGIN      declare @dropSql nvarchar(MAX); 	set @dropSql = 'drop table ' + @tempTableName + ' '; 	exec sp_executesql @dropSql; END; ");

              st.execute ("create  PROCEDURE SYNC_CLEAR_CONCEPT_TABLE (@tempConceptTableName varchar(500), @backupConceptTableName VARCHAR(500), @upload_id INT, @errorMsg varchar(max) = NULL OUTPUT)  as  BEGIN  declare @exec_str nvarchar(MAX); declare @interConceptDimensionTableName nvarchar(MAX); declare  @deleteDuplicateSql nvarchar(MAX); BEGIN TRY BEGIN TRANSACTION  	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY concept_path,concept_cd ORDER BY concept_path,concept_cd ) AS RNUM FROM ' + @tempConceptTableName +')  delete  from deleteTempDup where rnum>1'; 	exec sp_executesql @deleteDuplicateSql; 	 set @interConceptDimensionTableName = @backupConceptTableName + '_inter';   set @exec_str = ' create table '  + @interConceptDimensionTableName  +' (     concept_path   	varchar(700) NOT NULL, 	concept_cd     	varchar(50) NULL, 	name_char      	varchar(2000) NULL, 	concept_blob   	text NULL, 	update_date    	datetime NULL, 	download_date  	datetime NULL, 	import_date    	datetime NULL, 	sourcesystem_cd	varchar(50) NULL,       UPLOAD_ID       INT NULL,     CONSTRAINT '+ @interConceptDimensionTableName +'_PK PRIMARY KEY(concept_path) 	 )'; exec sp_executesql  @exec_str;   set @exec_str = ' insert into ' +  @interConceptDimensionTableName + ' (concept_cd, concept_path,name_char,concept_blob,update_date, download_date, import_date, sourcesystem_cd,upload_id)   select  ' +  ' concept_cd, concept_path,name_char,concept_blob,update_date, download_date, import_date, sourcesystem_cd,' + convert(nvarchar,@upload_id) + ' from ' + @tempConceptTableName ; exec  sp_executesql  @exec_str;  exec sp_rename 'concept_dimension', @backupConceptTableName;  set  @exec_str = 'CREATE INDEX idx_' + @interConceptDimensionTableName + '_uid ON '        +  @interConceptDimensionTableName + '  (upload_id)'; exec  sp_executesql  @exec_str;    set  @exec_str = 'CREATE INDEX idx_' + @interConceptDimensionTableName + '_cpcd ON '        +  @interConceptDimensionTableName + '  (concept_path,concept_cd)'; exec  sp_executesql  @exec_str;          exec sp_rename @interConceptDimensionTableName, 'concept_dimension';    COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);  END CATCH END ");
              
              st.execute ("create  PROCEDURE SYNC_CLEAR_MODIFIER_TABLE (@tempModifierTableName varchar(500), @backupModifierTableName VARCHAR(500), @upload_id INT, @errorMsg varchar(max) = NULL OUTPUT)  as  BEGIN  declare @exec_str nvarchar(MAX); declare @interModifierDimensionTableName nvarchar(MAX); declare  @deleteDuplicateSql nvarchar(MAX); BEGIN TRY BEGIN TRANSACTION  	 	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY modifier_path,modifier_cd ORDER BY modifier_path,modifier_cd ) AS RNUM FROM ' + @tempModifierTableName +')  delete  from deleteTempDup where rnum>1'; 	exec sp_executesql @deleteDuplicateSql; 	 set @interModifierDimensionTableName = @backupModifierTableName + '_inter';   set @exec_str = ' create table '  + @interModifierDimensionTableName  +' (     modifier_path   	varchar(700) NOT NULL,     modifier_cd     	varchar(50) NULL, 	name_char      	varchar(2000) NULL, 	modifier_blob   	text NULL, 	update_date    	datetime NULL, 	download_date  	datetime NULL, 	import_date    	datetime NULL, 	sourcesystem_cd	varchar(50) NULL,       UPLOAD_ID       INT NULL,     CONSTRAINT '+ @interModifierDimensionTableName +'_PK PRIMARY KEY(modifier_path) 	 )'; exec sp_executesql  @exec_str;   set @exec_str = ' insert into ' +  @interModifierDimensionTableName + ' (modifier_cd, modifier_path,name_char,modifier_blob,update_date, download_date, import_date, sourcesystem_cd,upload_id)   select  ' +  ' modifier_cd, modifier_path,name_char,modifier_blob,update_date, download_date, import_date, sourcesystem_cd,' + convert(nvarchar,@upload_id) + ' from ' + @tempModifierTableName ; exec  sp_executesql  @exec_str;  exec sp_rename 'modifier_dimension', @backupModifierTableName;  set  @exec_str = 'CREATE INDEX idx_' + @interModifierDimensionTableName + '_uid ON '        +  @interModifierDimensionTableName + '  (upload_id)'; exec  sp_executesql  @exec_str;    set  @exec_str = 'CREATE INDEX idx_' + @interModifierDimensionTableName + '_mpmd ON '        +  @interModifierDimensionTableName + '  (modifier_path,modifier_cd)'; exec  sp_executesql  @exec_str;          exec sp_rename @interModifierDimensionTableName, 'modifier_dimension';    COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);  END CATCH END ");
              
              st.execute ("create  PROCEDURE SYNC_CLEAR_PROVIDER_TABLE (@tempProviderTableName varchar(500), @backupProviderTableName VARCHAR(500), @upload_id INT, @errorMsg varchar(max) = NULL OUTPUT)  as  BEGIN  declare @exec_str nvarchar(MAX); declare @interProviderDimensionTableName nvarchar(MAX); declare  @deleteDuplicateSql nvarchar(MAX); BEGIN TRY BEGIN TRANSACTION  	set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY provider_path,provider_id ORDER BY provider_path,provider_id ) AS RNUM FROM ' + @tempProviderTableName +')  delete  from deleteTempDup where rnum>1'; 	exec sp_executesql @deleteDuplicateSql; 	 set @interProviderDimensionTableName = @backupProviderTableName + '_inter';   set @exec_str = ' create table '  + @interProviderDimensionTableName  +' (    Provider_Id    	varchar(50) NOT NULL, 	Provider_Path  	varchar(700) NOT NULL, 	Name_Char      	varchar(850) NULL, 	Provider_Blob  	text NULL, 	Update_Date    	datetime NULL, 	Download_Date  	datetime NULL, 	Import_Date    	datetime NULL, 	Sourcesystem_Cd	varchar(50) NULL ,     UPLOAD_ID         	INT NULL,     CONSTRAINT '+ @interProviderDimensionTableName +'_PK PRIMARY KEY(provider_path,provider_id) 	 )'; exec sp_executesql  @exec_str;   set @exec_str = ' insert into ' +  @interProviderDimensionTableName + ' (provider_id, provider_path,name_char,provider_blob,update_date, download_date, import_date, sourcesystem_cd,upload_id)   select  ' +  ' provider_id, provider_path,name_char,provider_blob,update_date, download_date, import_date, sourcesystem_cd,' + convert(nvarchar,@upload_id) + ' from ' + @tempProviderTableName ; exec  sp_executesql  @exec_str;  exec sp_rename 'provider_dimension', @backupProviderTableName;  set  @exec_str = 'CREATE INDEX idx_' + @interProviderDimensionTableName + '_pid ON '        +  @interProviderDimensionTableName + '  (provider_id,name_char)'; exec  sp_executesql  @exec_str;    set  @exec_str = 'CREATE INDEX idx_' + @interProviderDimensionTableName + '_uid ON '        +  @interProviderDimensionTableName + '  (upload_id)'; exec  sp_executesql  @exec_str;     exec sp_rename @interProviderDimensionTableName, 'provider_dimension';    COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);  END CATCH END ");
              
              st.execute ("create  PROCEDURE  UPDATE_OBSERVATION_FACT (@upload_temptable_name VARCHAR(500), @upload_id int, @appendFlag int,     @errorMsg varchar(max)  = NULL OUTPUT) AS BEGIN  declare @deleteDuplicateSql nvarchar(MAX), @deleteNullStartDateSql nvarchar(MAX);  declare @insertSql nvarchar(MAX), @updateSql nvarchar(MAX),@deleteSql nvarchar(MAX); BEGIN TRY   BEGIN TRANSACTION set @deleteDuplicateSql = 'with deleteTempDup as (SELECT *,ROW_NUMBER() OVER  ( PARTITION BY encounter_id,encounter_id_source,patient_id,patient_id_source,   concept_cd,start_date,modifier_cd,provider_id,instance_num ORDER BY encounter_id,encounter_id_source ) AS RNUM FROM ' + @upload_temptable_name +')  delete  from deleteTempDup where rnum>1';  exec sp_executesql @deleteDuplicateSql;  set @deleteNullStartDateSql =  'DELETE FROM ' + @upload_temptable_name + '               where start_date is null'; exec sp_executesql @deleteNullStartDateSql;                          set @updateSql =  'UPDATE ' +  @upload_temptable_name  + ' SET encounter_num = (SELECT em.encounter_num 		     FROM encounter_mapping em 		     WHERE em.encounter_ide = ' + @upload_temptable_name + '.encounter_id                      and em.encounter_ide_source = '+ @upload_temptable_name + '.encounter_id_source 	 	    ) WHERE EXISTS (SELECT em.encounter_num 		     FROM encounter_mapping em 		     WHERE em.encounter_ide = ' + @upload_temptable_name +'.encounter_id                      and em.encounter_ide_source = ' + @upload_temptable_name +'.encounter_id_source)';		       exec sp_executesql @updateSql;                    set @updateSql = 'UPDATE ' + @upload_temptable_name +    ' SET patient_num = (SELECT pm.patient_num 		     FROM patient_mapping pm 		     WHERE pm.patient_ide = ' +  @upload_temptable_name +'.patient_id                      and pm.patient_ide_source = '+ @upload_temptable_name+'.patient_id_source 	 	    ) WHERE EXISTS (SELECT pm.patient_num  		     FROM patient_mapping pm 		     WHERE pm.patient_ide = '+ @upload_temptable_name+'.patient_id                      and pm.patient_ide_source = '+ @upload_temptable_name+'.patient_id_source)';		      exec sp_executesql @updateSql;    IF @appendFlag = 0 BEGIN set @insertSql =  'INSERT  INTO  archive_observation_fact (encounter_num,patient_num,concept_Cd,provider_id,start_date,  modifier_cd,valtype_cd,tval_char,nval_num,valueflag_cd,quantity_num,units_cd,end_date,location_cd,confidence_num, observation_blob,update_date,download_date,import_date,sourcesystem_cd,archive_upload_id) 		SELECT obsfact.encounter_num,obsfact.patient_num,obsfact.concept_Cd,obsfact.provider_id,obsfact.start_date,  obsfact.modifier_cd,obsfact.valtype_cd,obsfact.tval_char,obsfact.nval_num,obsfact.valueflag_cd,obsfact.quantity_num, obsfact.units_cd,obsfact.end_date,obsfact.location_cd,obsfact.confidence_num, obsfact.observation_blob,obsfact.update_date,obsfact.download_date,obsfact.import_date,obsfact.sourcesystem_cd, ' + convert(nvarchar,@upload_id) +' archive_upload_id  		FROM observation_fact obsfact 		WHERE obsfact.encounter_num IN  			(SELECT temp_obsfact.encounter_num 			FROM  ' + @upload_temptable_name +' temp_obsfact                         group by  temp_obsfact.encounter_num               )';  exec sp_executesql @insertSql;   set @deleteSql  = 'DELETE  observation_fact  					WHERE EXISTS ( 					SELECT archive.encounter_num 					FROM archive_observation_fact  archive 					where archive.archive_upload_id = '+ convert(nvarchar,@upload_id) +'                                          AND archive.encounter_num=observation_fact.encounter_num 										 AND archive.concept_cd = observation_fact.concept_cd 										 AND archive.start_date = observation_fact.start_date                     )';  exec sp_executesql @deleteSql; END;    IF @appendFlag = 0 begin set @insertSql =  'INSERT  INTO observation_fact(encounter_num,concept_cd, patient_num,provider_id, start_date,modifier_cd,instance_num,valtype_cd,tval_char,nval_num,valueflag_cd, quantity_num,confidence_num,observation_blob,units_cd,end_date,location_cd, update_date,download_date,import_date,sourcesystem_cd, upload_id)  SELECT encounter_num,concept_cd, patient_num,provider_id, start_date,modifier_cd,instance_num,valtype_cd,tval_char,nval_num,valueflag_cd, quantity_num,confidence_num,observation_blob,units_cd,end_date,location_cd, update_date,download_date,getdate() import_date,sourcesystem_cd, temp.upload_id  FROM ' + @upload_temptable_name +' temp where temp.patient_num is not null and  temp.encounter_num is not null';  exec sp_executesql @insertSql; end else  begin set @updateSql = ' UPDATE observation_fact  set  			 		valtype_cd = temp.valtype_cd,                     tval_char = temp.tval_char,                     nval_num = temp.nval_num ,                     valueflag_cd = temp.valueflag_cd,                     quantity_num = temp.quantity_num,                     confidence_num = temp.confidence_num ,                     observation_blob = temp.observation_blob,                     units_cd = temp.units_cd,                     end_date = temp.end_date,                     location_cd = temp.location_cd,                     update_date= temp.update_date,                     download_date = temp.download_date,                     import_date = getdate(),                     sourcesystem_cd = temp.sourcesystem_cd, 					UPLOAD_ID = '+ convert(nvarchar,@upload_id)+ ' 					from observation_fact obsfact                      inner join ' + @upload_temptable_name + ' temp                     on  obsfact.encounter_num = temp.encounter_num  				    and obsfact.patient_num = temp.patient_num                     and obsfact.concept_cd = temp.concept_cd 					and obsfact.start_date = temp.start_date 		            and obsfact.provider_id = temp.provider_id 			 		and obsfact.modifier_cd = temp.modifier_cd 					and obsfact.instance_num = temp.instance_num                     where isnull(obsfact.update_date,0) <= isnull(temp.update_date,0)';  exec sp_executesql @updateSql;  set @insertSql = 'insert into observation_fact(encounter_num, 	patient_num,concept_cd,provider_id,start_date,modifier_cd,instance_num,valtype_cd,tval_char, 	nval_num,valueflag_cd,quantity_num,units_cd,end_date,location_cd ,confidence_num,observation_blob, 	update_date,download_date,import_date,sourcesystem_cd,upload_id)' +      ' select  temp.encounter_num, temp.patient_num,temp.concept_cd,temp.provider_id,temp.start_date,temp.modifier_cd,temp.instance_num,temp.valtype_cd,temp.tval_char, 	temp.nval_num,temp.valueflag_cd,temp.quantity_num,temp.units_cd,temp.end_date,temp.location_cd,temp.confidence_num,temp.observation_blob, 	temp.update_date,temp.download_date,getdate(),temp.sourcesystem_cd,'+ convert(nvarchar,@upload_id) + ' from  ' + @upload_temptable_name +  ' temp ' + 					' where temp.patient_num is not null and  temp.encounter_num is not null and not exists (select obsfact.concept_cd from observation_fact obsfact where ' +  				    ' obsfact.encounter_num = temp.encounter_num  				      and obsfact.patient_num = temp.patient_num                       and obsfact.concept_cd = temp.concept_cd 					  and obsfact.start_date = temp.start_date 		              and obsfact.provider_id = temp.provider_id 			 		  and obsfact.modifier_cd = temp.modifier_cd 					  and obsfact.instance_num = temp.instance_num 					) ';  exec sp_executesql @insertSql; end;   COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);   END CATCH  END;   ");
              
              st.execute ("create procedure UPDATE_QUERYINSTANCE_MESSAGE (@message text, @instanceId int , @errorMsg varchar(max) = NULL OUTPUT) as  begin  declare @ptrval binary(16), @i int  BEGIN TRY BEGIN TRANSACTION  select @ptrval = TEXTPTR(message),   @i=  datalength(message) from qt_query_instance where query_instance_id = @instanceId   updatetext qt_query_instance.message @ptrval @i 0 @message   COMMIT  END TRY   BEGIN CATCH    if @@TRANCOUNT > 0        ROLLBACK    declare @errMsg nvarchar(4000), @errSeverity int    select @errMsg = ERROR_MESSAGE(), @errSeverity = ERROR_SEVERITY();    set @errorMsg = @errMsg;    RAISERROR(@errMsg,@errSeverity,1);  END CATCH end ");
              

          }  
          catch(Exception e)  
          {  
              System.out.println("*** Outer Error : "+e.toString());  
              System.out.println("*** Outer ");  
              System.out.println("*** Outer Error : ");  
              e.printStackTrace();  
              System.out.println(" Outer ################################################");  
              //System.out.println(sb.toString());  
          }  
    
      }  
	
      public  void dosql(int c_hlevel, String c_fullname, String c_name, String c_visualattributes,
    		            String c_basecode, String c_tooltip)  
      {  
    	 /* System.out.println("dosql method");
    	  System.out.println("c_hlevel-" + c_hlevel);
    	  System.out.println("c_fullname-" + c_fullname);
    	  System.out.println("c_name-" + c_name);
    	  System.out.println("c_visualattributes-" + c_visualattributes);
    	  System.out.println("c_basecode-" + c_basecode);
    	  System.out.println("c_tooltip-" + c_tooltip);
    	 */ 
          try  
          {  
    
              Connection c = getSimpleConnectionMSSQL();  
              Statement st = c.createStatement();  
                          
              String ins = "INSERT INTO I2B2(C_HLEVEL, " +
              		"C_FULLNAME, " +
              		"C_NAME, " +
              		"C_SYNONYM_CD, " +
              		"C_VISUALATTRIBUTES, " +
              		"C_TOTALNUM, " +
              		"C_BASECODE, " +
              		"C_METADATAXML, " +
              		"C_FACTTABLECOLUMN, " +
              		"C_TABLENAME, " +
              		"C_COLUMNNAME, " +
              		"C_COLUMNDATATYPE, " +
              		"C_OPERATOR, " +
              		"C_DIMCODE, " +
              		"C_COMMENT, " +
              		"C_TOOLTIP, " +
              		"M_APPLIED_PATH, " +
              		"UPDATE_DATE, " +
              		"DOWNLOAD_DATE, " +
              		"IMPORT_DATE, " +
              		"SOURCESYSTEM_CD, " +
              		"VALUETYPE_CD, " +
              		"M_EXCLUSION_CD, " +
              		"C_PATH, " +
              		"C_SYMBOL) " +
              		"VALUES " +          		
              		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
              	//"(1, '\\i2b2\\Demographics\\', 'Demographics', 'N', 'CA ', null, null, null, 'concept_cd', 'concept_dimension', 'concept_path', 'T', 'LIKE', '\\i2b2\\Demographics\\', null, 'Demographic', '@', '2007-04-10 00:00:00.0', '2007-04-10 00:00:00.0', '2007-04-10 00:00:00.0', 'DEM2FACT CONVERT', null, null, null, null);";
             
              PreparedStatement p = c.prepareStatement(ins);
              
              //p.setInt(1, 4); //C_HLEVEL
              p.setInt(1, c_hlevel); //C_HLEVEL
              
              //p.setString(2, "\\i2b2\\Diagnoses\\Circulatory system (390-459)\\(448) Disease of capillaries\\(448-0) Dereditary hemorrhagic te~\\"); //C_FULLNAME
              p.setString(2, c_fullname); //C_FULLNAME
              
              //p.setString(3, "Dereditary hemorrhagic telangiectasia");
              p.setString(3, c_name);
              
              p.setString(4, String.valueOf('N')); // SHOULD BE N
              
              //p.setString(5, "LA"); // C_VISUALATTRIBUTES LA is leaf
              p.setString(5, c_visualattributes); // C_VISUALATTRIBUTES LA is leaf
              
              
              //p.setInt(6, null);
              p.setNull(6, Types.NULL);
              
              //p.setString(7, "ICD9:449.0"); // C_BASECODE
              p.setString(7, c_basecode); // C_BASECODE
              
              
              p.setString(8, null);
              p.setString(9, "concept_cd");
              p.setString(10, "concept_dimension");
              p.setString(11, "concept_path");
              p.setString(12, "T");
              p.setString(13, "LIKE");
              
              //p.setString(14, "\\i2b2\\Diagnoses\\Circulatory system (390-459)\\(448) Disease of capillaries\\(448-0) Dereditary hemorrhagic te~\\");
              p.setString(14, c_basecode);
              
              p.setString(15, null);
              
              //p.setString(16, "Diagnoses \\ Circulatory system \\ Disease of capillaries \\ Dereditary hemorrhagic telangiectasia"); // C_TOOLTIP
              p.setString(16, c_tooltip); // C_TOOLTIP
              
              p.setString(17, "@");
              p.setTimestamp(18,getCurrentTimeStamp());
              p.setTimestamp(19,getCurrentTimeStamp());
              p.setTimestamp(20,getCurrentTimeStamp());
              p.setString(21, "RPDR");
              p.setString(22, null);
              p.setString(23, null);
              p.setString(24, null);
              p.setString(25, null);
              
              p.executeUpdate();
              
              
              
          }  
          catch(Exception e)  
          {  
              System.out.println("*** Outer Error : "+e.toString());  
              System.out.println("*** Outer ");  
              System.out.println("*** Outer Error : ");  
              e.printStackTrace();  
              System.out.println(" Outer ################################################");  
              //System.out.println(sb.toString());  
          }  
    
      }  

      private static java.sql.Timestamp getCurrentTimeStamp() {
    	    java.util.Date today = new java.util.Date();
    	    return new java.sql.Timestamp(today.getTime());
    	}

      public  void dosqltable(int c_hlevel, String c_table_cd, String c_table_name, String c_fullname, String c_name, String c_visualattributes,
	            String c_dimcode, String c_tooltip)  
		{  
		/*System.out.println("dosql method");
		System.out.println("c_hlevel-" + c_hlevel);
		System.out.println("c_table_cd-" + c_table_cd);
		System.out.println("c_name-" + c_name);
		System.out.println("c_fullname-" + c_fullname);
		System.out.println("c_visualattributes-" + c_visualattributes);
		System.out.println("c_dimcode-" + c_dimcode);
		System.out.println("c_tooltip-" + c_tooltip);*/
		
		try  
		{  
		
		    Connection c = getSimpleConnectionMSSQL();  
		    Statement st = c.createStatement();  
		                
		    String ins = "INSERT INTO TABLE_ACCESS(" +
		    		"C_TABLE_CD, " +
		    		"C_TABLE_NAME, " +
		    		"C_PROTECTED_ACCESS, " +
		    		"C_HLEVEL, " +
		    		"C_FULLNAME, " +
		    		"C_NAME, " +
		    		"C_SYNONYM_CD, " +
		    		"C_VISUALATTRIBUTES, " +
		    		"C_TOTALNUM, " +
		    		"C_BASECODE, " +
		    		"C_METADATAXML, " +
		    		"C_FACTTABLECOLUMN, " +
		    		"C_DIMTABLENAME, " +
		    		"C_COLUMNNAME, " +
		    		"C_COLUMNDATATYPE, " +
		    		"C_OPERATOR, " +
		    		"C_DIMCODE, " +
		    		"C_COMMENT, " +
		    		"C_TOOLTIP, " +
		    		"C_ENTRY_DATE, " +
		    		"C_CHANGE_DATE, " +
		    		"C_STATUS_CD, " +
		    		"VALUETYPE_CD) " +
		    		//"VALUES ('i2b2_VISIT', 'I2B2', 'N', 1, '\\i2b2\\Visit Details\\', 'Visit Details', 'N', 'CA ', null, null, null, 'concept_cd', 'concept_dimension', 'concept_path', 'T', 'LIKE', '\\i2b2\\Visit Details\\', null, 'Visit Details', null, null, null, null);" +          		
		    		"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    	//"(1, '\\i2b2\\Demographics\\', 'Demographics', 'N', 'CA ', null, null, null, 'concept_cd', 'concept_dimension', 'concept_path', 'T', 'LIKE', '\\i2b2\\Demographics\\', null, 'Demographic', '@', '2007-04-10 00:00:00.0', '2007-04-10 00:00:00.0', '2007-04-10 00:00:00.0', 'DEM2FACT CONVERT', null, null, null, null);";
		   
		    PreparedStatement p = c.prepareStatement(ins);
		    
		    //p.setInt(1, "i2b2"); //C_TABLE_CD
		    p.setString(1, c_table_cd); //C_TABLE_CD
		    
		    //p.setString(2, "i2b2"); //C_TABLE_NAME
		    p.setString(2, c_table_name); //C_TABLE_NAME
		    
		    p.setString(3, "N");
		    
		    p.setInt(4, c_hlevel); // C_HLEVEL
		    
		    //p.setString(5, "\i2b2\"); // C_FULLNAME
		    p.setString(5, c_fullname); // C_FULLNAME
		    
		  //p.setString(6, "Ontology"); // C_NAME
		    p.setString(6, c_name); // C_NAME
		  
		  
		    p.setString(7, "N"); // should be N
		    
		    p.setString(8, "CA");
		   
		    p.setNull(9, Types.NULL); // C_TOTALNUM
	            
		    
		    p.setString(10, "");
		    p.setString(11, "");
		    p.setString(12, "concept_cd");
		    p.setString(13, "concept_dimension");
		    p.setString(14, "concept_path");
		    p.setString(15, "T");
		    
		    p.setString(16, "LIKE");
		    
		  //p.setString(15, "\i2b2\");		    
		    p.setString(17, c_dimcode);
		    
		    p.setString(18, ""); // C_COMMENT
		    
		    //p.setString(19, "Ontology"); // C_TOOLTIP	    
		    p.setString(19, c_tooltip); // C_TOOLTIP	
		    
		    p.setTimestamp(20, getCurrentTimeStamp());
		    p.setTimestamp(21,getCurrentTimeStamp());
		    p.setString(22, "");
		    p.setString(23, "");
		    
		    p.executeUpdate();
		    
		    
		    
		}  
		catch(Exception e)  
		{  
		    System.out.println("*** Outer Error : "+e.toString());  
		    System.out.println("*** Outer ");  
		    System.out.println("*** Outer Error : ");  
		    e.printStackTrace();  
		    System.out.println(" Outer ################################################");  
		    //System.out.println(sb.toString());  
		}  
		
		}  
      
	/********** TESTS **********/

	
	
	
	
	
	
	
}