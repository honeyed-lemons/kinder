package honeyedlemons.kinder.modcompat;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "kinder")
public class KinderConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip()
    public boolean rejuvothergems = true;
    @ConfigEntry.Gui.Tooltip()
    public int incubationtime = 16000;

    @ConfigEntry.Gui.Tooltip()
    public int oystertime = 24000;
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.RequiresRestart
    public DriedGemSeedConfig driedGemSeedConfig = new DriedGemSeedConfig();
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.CollapsibleObject
    public RubyConfig rubyConfig = new RubyConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public QuartzConfig quartzConfig = new QuartzConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public PearlConfig pearlConfig = new PearlConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public SapphireConfig sapphireConfig = new SapphireConfig();
    public static class DriedGemSeedConfig{
        @ConfigEntry.Gui.RequiresRestart
        public boolean addtostructure = true;
        @ConfigEntry.Gui.RequiresRestart
        public float chance = 0.333f;
        @ConfigEntry.Gui.RequiresRestart
        public boolean addtovillager = true;
        @ConfigEntry.Gui.RequiresRestart
        public int price = 3;
    }
    public static class RubyConfig
    {
        public boolean incubatable = true;
        public int attack_damage = 3;
        public int max_health = 30;
    }
    public static class QuartzConfig
    {
        public boolean incubatable = true;
        public int attack_damage = 6;
        public int max_health = 50;
    }
    public static class PearlConfig
    {
        public int attack_damage = 1;
        public int max_health = 20;
    }
    public static class SapphireConfig
    {
        public boolean incubatable = true;
        public int attack_damage = 1;
        public int max_health = 50;
    }
}
