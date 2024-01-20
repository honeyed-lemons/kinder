package phyner.kinder.util;

public enum PaletteType {
    GEM("gem"),
    SKIN("skin"),
    HAIR("hair");

    public final String type;
    private static final PaletteType[] values = PaletteType.values();
    PaletteType(String id) {
        this.type = id;
    }
    public String getType(){
        return this.type;
    }
    public static PaletteType getPaletteType(int i) {
        return PaletteType.values[i];
    }
}
