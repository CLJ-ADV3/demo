package com.example.demo.client;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;

public class DocumentClientFactory {

    private static final String HOST = "https://cdcosmosdb2.documents.azure.com:443/";
    private static final String Master_KEY = "nik9pOCf01O3o3W5V7CfXiZ913RN12VT88bthxVyNErvlWetNUFHZZMgUSM9VFOvFhSFDtPw7e2YUHigmOdadQ==";

    private static DocumentClient documentClient = new DocumentClient(HOST, Master_KEY,
            ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);

    public static DocumentClient getDocumentClient(){
        return documentClient;
    }
}
