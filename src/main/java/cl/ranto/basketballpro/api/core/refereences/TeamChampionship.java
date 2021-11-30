package cl.ranto.basketballpro.api.core.refereences;

import com.google.cloud.firestore.annotation.DocumentId;

public class TeamChampionship {

    @DocumentId
    private String oid;
    private String name;
    private String nameURL;
}
