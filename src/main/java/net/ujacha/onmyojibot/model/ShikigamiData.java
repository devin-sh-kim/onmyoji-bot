package net.ujacha.onmyojibot.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class ShikigamiData {
    private long shikigamiId;
    private ShikigamiRarity rarity;
    private String title;
    private List<String> imageUrls;
    private List<String> messages;
    private String infoUrl;
    private List<String> hints;
    private String initial;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShikigamiData that = (ShikigamiData) o;
        return rarity == that.rarity &&
                title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rarity, title);
    }
}
