package com.example.demo.data;

import com.example.demo.client.DocumentClientFactory;
import com.example.demo.models.UserDetails;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.documentdb.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DemoData implements IDemoData {

    private static final String DATABASE_ID = "UserData";
    private static final String COLLECTION_ID = "CorpOu";

    private static DocumentClient documentClient = DocumentClientFactory.getDocumentClient();
    private static DocumentCollection collectionCache;
    private static Database databaseCache;

    // We'll use Gson for POJO <=> JSON serialization for this example.
    // private static Gson gson = new Gson();
    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    private Database getTodoDatabase() {
        if (databaseCache == null) {
            // Get the database if it exists
            List<Database> databaseList = documentClient
                    .queryDatabases(
                            "SELECT * FROM root r WHERE r.id='" + DATABASE_ID
                                    + "'", null).getQueryIterable().toList();

            if (databaseList.size() > 0) {
                // Cache the database object so we won't have to query for it
                // later to retrieve the selfLink.
                databaseCache = databaseList.get(0);
            } else {
                // Create the database if it doesn't exist.
                try {
                    Database databaseDefinition = new Database();
                    databaseDefinition.setId(DATABASE_ID);

                    databaseCache = documentClient.createDatabase(
                            databaseDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return databaseCache;
    }

    private DocumentCollection getTodoCollection() {
        if (collectionCache == null) {
            // Get the collection if it exists.
            List<DocumentCollection> collectionList = documentClient
                    .queryCollections(
                            getTodoDatabase().getSelfLink(),
                            "SELECT * FROM root r WHERE r.id='" + COLLECTION_ID
                                    + "'", null).getQueryIterable().toList();

            if (collectionList.size() > 0) {
                // Cache the collection object so we won't have to query for it
                // later to retrieve the selfLink.
                collectionCache = collectionList.get(0);
            } else {
                // Create the collection if it doesn't exist.
                try {
                    DocumentCollection collectionDefinition = new DocumentCollection();
                    collectionDefinition.setId(COLLECTION_ID);

                    collectionCache = documentClient.createCollection(
                            getTodoDatabase().getSelfLink(),
                            collectionDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return collectionCache;
    }


    private Document getDocumentById(String id) {
        // Retrieve the document using the DocumentClient.
        List<Document> documentList = documentClient
                .queryDocuments(getTodoCollection().getSelfLink(),
                        "SELECT * FROM root r WHERE r.id='" + id + "'", null)
                .getQueryIterable().toList();

        if (documentList.size() > 0) {
            return documentList.get(0);
        } else {
            return null;
        }
    }


    @Override
    public String getUserDetails(String id) {

        Document todoItemDocument = getDocumentById(id);
        String result = null;

        try {
            if (todoItemDocument != null) {

                result = todoItemDocument.toJson();

               // System.out.println("Output"+todoItemDocument.toJson());
                // De-serialize the document in to a TodoItem.
                // userDetails = mapper.readValue(todoItemDocument.toString(), UserDetails.class);

                //readValue(todoItemDocument.toString(), UserDetails.class);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

}
