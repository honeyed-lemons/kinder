package phyner.kinder.util;

public enum PaletteType {
    GEM("gem"),SKIN("skin"),HAIR("hair");

    private static final PaletteType[] values = PaletteType.values();
    public final String type;

    PaletteType(String id){
        this.type = id;
    }

    public static PaletteType getPaletteType(int i){
        return PaletteType.values[i];
    }

    public String getType(){
        return this.type;
    }
}
