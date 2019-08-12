package api.admin.filterApi;

import com.google.appengine.api.search.*;
import util.StringUtil;

public enum SearchIndexManager {
    INSTANCE;

    public void indexDocument(String indexName, Document document) {
        //Setup the Index
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        try {
            //Put the Document in the Index. If the document is already existing, it will be overwritten
            index.put(document);
        } catch (PutException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
                // retry putting the document
            }
        }
    }

    public Document retrieveDocument(String documentId) {
        //Setup the Index
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(StringUtil.ARTICLE_INDEXS_NAME).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        //Retrieve the Record from the Index
        return index.get(documentId);
    }

    public Results<ScoredDocument> retrieveDocuments(String searchText) {
        //Setup the Index
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(StringUtil.ARTICLE_INDEXS_NAME).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        //Retrieve the Records from the Index
        return index.search(searchText);
    }


    public void deleteDocumentFromIndex(String documentId) {
        //Setup the Index
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(StringUtil.ARTICLE_INDEXS_NAME).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

        //Delete the Records from the Index
        index.delete(documentId);
    }
}
