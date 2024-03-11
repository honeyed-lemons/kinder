package phyner.kinder.util;

public enum GemPlacements {
    FOREHEAD(0),
    LEFT_EYE(1),
    RIGHT_EYE(2),
    LEFT_EAR(3),
    RIGHT_EAR(4),
    NOSE(5),
    LEFT_CHEEK(6),
    RIGHT_CHEEK(7),
    MOUTH(8),
    LEFT_SHOULDER(9),
    RIGHT_SHOULDER(10),
    CHEST(11),
    BACK(12),
    LEFT_HAND(13),
    RIGHT_HAND(14),
    BELLY(15),
    LEFT_KNEE(16),
    RIGHT_KNEE(17),
    BACK_OF_HEAD(18),
    TOP_OF_HEAD(19),
    LEFT_PALM(20),
    RIGHT_PALM(21),
    LEFT_THIGH(22),
    RIGHT_THIGH(23),
    LEFT_ARM(24),
    RIGHT_ARM(25);
    public final int id;
    private static final GemPlacements[] vals = GemPlacements.values();

    GemPlacements(int id){
        this.id = id;
    }

    public static GemPlacements getPlacement(int i){
        return GemPlacements.vals[i];
    }
}
