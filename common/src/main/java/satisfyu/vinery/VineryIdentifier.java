package satisfyu.vinery;

import net.minecraft.resources.ResourceLocation;

public class VineryIdentifier extends ResourceLocation {

    public VineryIdentifier(String path) {
        super(Vinery.MODID, path);
    }

    public static String asString(String path) {
        return (Vinery.MODID + ":" + path);
    }
}
