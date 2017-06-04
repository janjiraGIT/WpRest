package com.wizepass.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.wizepass.util.Constants;


public class EsResponse {

	public static IndexResponse postNewDocument( String index, String type , XContentBuilder Xbuilder) throws IOException{	
		final Client client = EsTransportClient.getTransportClient(Constants.ES_HOST,Constants.PORT);	
		IndexResponse indexresponse = client.prepareIndex(index, type)
                .setSource(Xbuilder)
                .execute()
                .actionGet();	
		return indexresponse;			
	}
	public static IndexResponse putNewDocument( String index, String type , String id, XContentBuilder Xbuilder) throws IOException{	
		final Client client = EsTransportClient.getTransportClient(Constants.ES_HOST,Constants.PORT);	
		IndexResponse indexresponse = client.prepareIndex(index, type,id)
                .setSource(Xbuilder)
                .execute()
                .actionGet();	
		return indexresponse;			
	}

}
