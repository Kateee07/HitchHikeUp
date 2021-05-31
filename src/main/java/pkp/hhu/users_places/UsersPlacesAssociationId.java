package pkp.hhu.users_places;

import java.io.Serializable;
import java.util.Objects;

public class UsersPlacesAssociationId implements Serializable {

    private int userId;
    private int placeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersPlacesAssociationId that = (UsersPlacesAssociationId) o;
        return userId == that.userId && placeId == that.placeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, placeId);
    }
}
