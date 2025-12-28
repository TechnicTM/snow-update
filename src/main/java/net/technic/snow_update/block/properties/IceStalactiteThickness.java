package net.technic.snow_update.block.properties;

import net.minecraft.util.StringRepresentable;

public enum IceStalactiteThickness implements StringRepresentable{
    TIP_MERGE("tip_merge"),
    TIP("tip"),
    FRUSTUM("frustum"),
    MIDDLE("middle"),
    BASE("base");

    private final String name;

    private IceStalactiteThickness(String pName){
        this.name = pName;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    
}
