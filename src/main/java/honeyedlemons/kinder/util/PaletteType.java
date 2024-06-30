package honeyedlemons.kinder.util;

public enum PaletteType {
    GEM("gem"), SKIN("skin"), HAIR("hair");

    public final String type;

    PaletteType(String id) {
        this.type = id;
    }

    public String getType() {
        return this.type;
    }
}
