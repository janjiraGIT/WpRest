package com.wizepass.elasticsearch;

import java.net.InetSocketAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.ConnectTransportException;

public class EsTransportClient {
	private static TransportClient client;
	public static Client getTransportClient(String host, int port){
		try{	
			   client =    TransportClient.builder().build()
					   .addTransportAddress(new InetSocketTransportAddress
					   (new InetSocketAddress(host, port)));  
						 
		}catch(ConnectTransportException e){
			        	  System.out.println("TransportClient error " + e.getStackTrace());		
		}
		return (Client) client;	
	}
}
