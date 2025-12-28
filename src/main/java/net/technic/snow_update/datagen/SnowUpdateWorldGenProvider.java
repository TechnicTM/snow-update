package net.technic.snow_update.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.technic.snow_update.SnowUpdate;
import net.technic.snow_update.worldgen.SnowUpdateBiomeModifiers;
import net.technic.snow_update.worldgen.SnowUpdateConfigFeatures;
import net.technic.snow_update.worldgen.SnowUpdatePlacedFeatures;
import net.technic.snow_update.worldgen.biome.SnowUpdateBiomes;

public class SnowUpdateWorldGenProvider extends DatapackBuiltinEntriesProvider{

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.PLACED_FEATURE, SnowUpdatePlacedFeatures::boostrap)
        .add(Registries.CONFIGURED_FEATURE, boostrap -> {
            SnowUpdateConfigFeatures.boostrap(boostrap);
        })
        .add(Registries.BIOME, SnowUpdateBiomes::bosstrap)
        .add(ForgeRegistries.Keys.BIOME_MODIFIERS, SnowUpdateBiomeModifiers::bootstrap);

    public SnowUpdateWorldGenProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, BUILDER, Set.of(SnowUpdate.MOD_ID));
    }
    
}
