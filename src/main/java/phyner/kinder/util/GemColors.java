package phyner.kinder.util;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.IntFunction;

public enum GemColors implements StringIdentifiable {
    WHITE (0, "white", 16777215),
    ORANGE (1, "orange", 16750848),
    MAGENTA (2, "magenta", 16730610),
    LIGHT_BLUE (3, "light_blue", 9889535),
    YELLOW (4, "yellow", 16764199),
    LIME (5, "lime", 11140914),
    PINK (6, "pink", 16754888),
    GRAY (7, "gray", 5329233),
    LIGHT_GRAY (8, "light_gray", 10197915),
    CYAN (9, "cyan", 65535),
    PURPLE (10, "purple", 12273663),
    BLUE (11, "blue", 4346623),
    BROWN (12, "brown", 6304293),
    GREEN (13, "green", 52224),
    RED (14, "red", 16711760),
    BLACK (15, "black", 1973790);

    private static final IntFunction<GemColors> BY_ID = ValueLists.createIdToValueFunction (GemColors::getId, values (), ValueLists.OutOfBoundsHandling.ZERO);
    private final int id;
    private final String name;
    private final float[] colorComponents;

    GemColors (int id, String name, int color){
        this.id = id;
        this.name = name;
        int j = (color & 16711680) >> 16;
        int k = (color & '\uff00') >> 8;
        int l = (color & 255);
        this.colorComponents = new float[]{(float) j / 255.0F, (float) k / 255.0F, (float) l / 255.0F};
    }

    /**
     * {@return the dye color whose ID is {@code id}}
     *
     * @apiNote If out-of-range IDs are passed, this returns {@link #WHITE}.
     */
    public static GemColors byId (int id){
        return BY_ID.apply (id);
    }

    public static float[] getDyedColor (GemColors color){
        if (color == GemColors.WHITE) {
            return new float[]{1, 1, 1};
        } else {
            float[] fs = color.getColorComponents ();
            float f = 0.75F;
            return new float[]{fs[0] * f, fs[1] * f, fs[2] * f};
        }
    }

    public static int lerpHex (ArrayList<Integer> colors){
        Random random = new Random ();
        if (colors.size () == 0) {
            return 0;
        }
        if (colors.size () == 1) {
            return colors.get (0);
        }
        int r;
        int g;
        int b;
        float u = random.nextFloat ();

        int bound = random.nextInt (colors.size () - 1);

        int b_r = (colors.get (bound) & 16711680) >> 16;
        int b_g = (colors.get (bound) & 65280) >> 8;
        int b_b = (colors.get (bound) & 255);
        int e_r = (colors.get (bound + 1) & 16711680) >> 16;
        int e_g = (colors.get (bound + 1) & 65280) >> 8;
        int e_b = (colors.get (bound + 1) & 255);

        r = (int) (u * b_r + (1f - u) * e_r);
        g = (int) (u * b_g + (1f - u) * e_g);
        b = (int) (u * b_b + (1f - u) * e_b);

        return (r << 16) + (g << 8) + b;
    }

    /**
     * {@return the integer ID of the dye color}
     */
    public int getId (){
        return this.id;
    }

    /**
     * {@return the name of the dye color}
     */
    public String getName (){
        return this.name;
    }

    /**
     * {@return the float array containing the red, green and blue components of this dye color}
     *
     * <p>Each value of the array is between {@code 0.0} and {@code 255.0} (both inclusive).
     */
    public float[] getColorComponents (){
        return this.colorComponents;
    }

    public String toString (){
        return this.name;
    }

    public String asString (){
        return this.name;
    }
}
