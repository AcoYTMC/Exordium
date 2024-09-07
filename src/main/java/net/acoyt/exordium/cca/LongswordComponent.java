package net.acoyt.exordium.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.acoyt.exordium.Exordium;
import net.acoyt.exordium.registry.ExordiumItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class LongswordComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;
    private boolean blocking = false;

    public LongswordComponent(PlayerEntity player) {
        this.player = player;
    }

    public static LongswordComponent get(@NotNull PlayerEntity player) {
        return (LongswordComponent) Exordium.LONGSWORD.get(player);
    }

    private void sync() {
        Exordium.LONGSWORD.sync(this.player);
    }

    public void tick() {
    }

    public int getChargeTint(ItemStack stack) {
        return 0;
    }

    public boolean isBlocking() {
        if (!this.player.world.isClient() && (!this.player.getMainHandStack().isOf(ExordiumItems.EXORDIOR) || !this.player.isUsingItem())) {
            this.setBlocking(false);
        }

        return this.blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
        this.sync();
    }

    public void readFromNbt(NbtCompound tag) {
        this.blocking = tag.getBoolean("isBlocking");
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("isBlocking", this.blocking);
    }
}
