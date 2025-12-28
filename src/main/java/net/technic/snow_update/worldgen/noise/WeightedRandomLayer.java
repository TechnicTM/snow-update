package net.technic.snow_update.worldgen.noise;

import java.util.List;

import net.minecraft.util.random.WeightedEntry;
import net.technic.snow_update.worldgen.area.AreaContext;
import net.technic.snow_update.worldgen.area.AreaTransformer0;
import net.technic.snow_update.worldgen.util.WeightedRandomList;

public abstract class WeightedRandomLayer<T extends WeightedEntry> implements AreaTransformer0 {
    private final WeightedRandomList<T> weightedEntries;

    public WeightedRandomLayer(List<T> entries)
    {
        this.weightedEntries = WeightedRandomList.create(entries);
    }

    @Override
    public int apply(AreaContext context, int x, int y)
    {
        return this.weightedEntries.getRandom(context).map(this::getEntryIndex).orElse(getDefaultIndex());
    }

    protected abstract int getEntryIndex(T entry);

    protected int getDefaultIndex()
    {
        return 0;
    }
}
