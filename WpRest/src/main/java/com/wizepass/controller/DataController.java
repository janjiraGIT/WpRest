package com.wizepass.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;



import com.wizepass.elasticsearch.EsTransportClient;
import com.wizepass.util.Constants;

public class DataController {
	private static Client client = EsTransportClient.getTransportClient(Constants.ES_HOST, Constants.PORT);
	
	public List<Map<String,Object>> getUsers(){
		List<Map<String, Object>> listOfUsers = new ArrayList<Map<String,Object>>();
		SearchResponse searchResponse = null;
		int scrollsize = 1000;
		int i = 0;
		while ( searchResponse == null || searchResponse.getHits().hits().length !=0){
					searchResponse = client.prepareSearch(Constants.INDEX)
												.setTypes(Constants.TYPE_USERS)
												.setQuery(QueryBuilders.matchAllQuery())
												.setSize(scrollsize)
												.setFrom(i * scrollsize)
												.execute()
												.actionGet();
					for(SearchHit hit : searchResponse.getHits()){
						listOfUsers.add(hit.getSource());
		            	}
		            i++;
		        }
					return listOfUsers;		
		}
	
	public List<Map<String,Object>> getMg(){
		List<Map<String, Object>> listOfUsers = new ArrayList<Map<String,Object>>();
		SearchResponse searchResponse = null;
		int scrollsize = 1000;
		int i = 0;
		while ( searchResponse == null || searchResponse.getHits().hits().length !=0){
					searchResponse = client.prepareSearch(Constants.INDEX)
												.setTypes(Constants.TYPE_MG)
												.setQuery(QueryBuilders.matchAllQuery())
												.setSize(scrollsize)
												.setFrom(i * scrollsize)
												.execute()
												.actionGet();
					for(SearchHit hit : searchResponse.getHits()){
						listOfUsers.add(hit.getSource());
		            	}
		            i++;
		        }
					return listOfUsers;		
		}
	
	public List<Map<String,Object>> getRegistrationToken(){
		List<Map<String, Object>> listOfReg = new ArrayList<Map<String,Object>>();
		SearchResponse searchResponse = null;
		int scrollsize = 1000;
		int i = 0;
		while ( searchResponse == null || searchResponse.getHits().hits().length !=0){
					searchResponse = client.prepareSearch(Constants.INDEX)
												.setTypes(Constants.TYPE_REG)
												.setQuery(QueryBuilders.matchAllQuery())
												.setSize(scrollsize)
												.setFrom(i * scrollsize)
												.execute()
												.actionGet();
					for(SearchHit hit : searchResponse.getHits()){
						listOfReg.add(hit.getSource());
		            	}
		            i++;
		        }
					return listOfReg;		
		}
	
	public List<Map<String, Object>> getByName(String name){
		List<Map<String, Object>> listOfSearchName = new ArrayList<Map<String,Object>>();
		QueryBuilder qname = 	QueryBuilders.nestedQuery("attributes", 
								QueryBuilders.boolQuery()
								.must(QueryBuilders.matchQuery("attributes.account_name", name)));
		try {
			SearchResponse sr = client.prepareSearch(Constants.INDEX)
						.setTypes(Constants.TYPE_USERS)
						.setQuery(qname)
						.execute()
						.actionGet();
			for(SearchHit hit : sr.getHits()){
				listOfSearchName.add(hit.getSource());			
			}
			}catch(Exception e){
				System.out.println("Could not find data"+ e);	
			}
		
		return listOfSearchName;
		
	}
}

