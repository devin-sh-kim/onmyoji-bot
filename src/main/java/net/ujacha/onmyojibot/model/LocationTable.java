package net.ujacha.onmyojibot.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class LocationTable {
    private long locationId;
    private LocationType type;
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationTable that = (LocationTable) o;
        return  type == that.type &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title);
    }
}
