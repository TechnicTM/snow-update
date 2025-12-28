package net.technic.snow_update.entity;

import java.util.function.IntFunction;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.technic.snow_update.init.SnowBlockRegistry;
import net.technic.snow_update.init.SnowEntityRegistry;
import net.technic.snow_update.init.SnowItemsRegistry;

public class SnowUpdateBoats extends Boat{
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

    public SnowUpdateBoats(EntityType<? extends Boat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SnowUpdateBoats(Level pLevel, double pX, double pY, double pZ) {
        this(SnowEntityRegistry.FROSTWOOD_BOAT.get(), pLevel);
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
        this.entityData.define(DATA_ID_TYPE, Type.FROSTWOOD.ordinal());
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
                return SnowItemsRegistry.FROSTWOOD_BOAT.get();
            }
        }

        return super.getDropItem();
    }

    @SuppressWarnings("deprecation")
    public static enum Type implements StringRepresentable {
        FROSTWOOD(SnowBlockRegistry.FROSTED_PLANKS.get(), "frostwood");

        private final String name;
        private final Block material;
        public static final StringRepresentable.EnumCodec<SnowUpdateBoats.Type> CODEC = StringRepresentable.fromEnum(SnowUpdateBoats.Type::values);
        private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

        private Type(Block pMaterial, String pName) {
            this.material = pMaterial;
            this.name = pName;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }

        public Block getMaterial() {
            return this.material;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static SnowUpdateBoats.Type byID(int pId) {
            return BY_ID.apply(pId);
        }

        public static SnowUpdateBoats.Type byName(String pName) {
            return CODEC.byName(pName, FROSTWOOD);
        }
        
    }

}
