package modals.requests;

import java.util.List;

public class AddBookRequest {
    private String userId;
    private List<CollectionOfIsbn> collectionOfIsbns;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CollectionOfIsbn> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<CollectionOfIsbn> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }


}
