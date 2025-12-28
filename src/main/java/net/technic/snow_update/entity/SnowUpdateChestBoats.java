package net.technic.snow_update.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.technic.snow_update.init.SnowEntityRegistry;
import net.technic.snow_update.init.SnowItemsRegistry;

public class SnowUpdateChestBoats extends ChestBoat{
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

    public SnowUpdateChestBoats(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SnowUpdateChestBoats(Level pLevel, double pX, double pY, double pZ) {
        this(SnowEntityRegistry.FROSTWOOD_CHESTBOAT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }
    
    public void setVariant(SnowUpdateBoats.Type pVariant) {
        this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
    }

    public SnowUpdateBoats.Type getModVariant() {
        return SnowUpdateBoats.Type.byID(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, SnowUpdateBoats.Type.FROSTWOOD.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putString("Type", this.getModVariant().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Type", 0)) {
            this.setVariant(SnowUpdateBoats.Type.byName(pCompound.getString("Type")));
        }
    }

    @Override
    public Item getDropItem() {
        switch (getModVariant()) {
            case FROSTWOOD -> {
                return SnowItemsRegistry.FROSTWOOD_CHESTBOAT.get();
            }
        }

        return super.getDropItem();
    }

    

}
