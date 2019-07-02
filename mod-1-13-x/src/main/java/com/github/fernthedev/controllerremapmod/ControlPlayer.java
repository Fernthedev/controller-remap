package com.github.fernthedev.controllerremapmod;

import com.github.fernthedev.controllerremapmod.core.IControlPlayer;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

@AllArgsConstructor
public class ControlPlayer implements IControlPlayer {
    private EntityPlayer player;

    @Override
    public void moveForward(float value) {
        player.moveForward = value;
    }

    @Override
    public void moveStrafing(float value) {
        player.moveStrafing = value;
    }

    @Override
    public void addRotationYaw(float value) {
        player.rotationYaw += value;
    }

    @Override
    public void addRotationPitch(float value) {
        player.rotationPitch += value;
    }

    @Override
    public void openInventory() {
        Minecraft.getInstance().displayGuiScreen(new GuiInventory(player));
    }

    @Override
    public void toggleSneak() {
        player.setSneaking(!player.isSneaking());
    }

    @Override
    public void scrollSlot(int amount) {
        player.inventory.changeCurrentItem(amount);
    }

    @Override
    public void dropItem() {
        player.dropItem(false);
        //player.inventory.getCurrentItem().setCount(player.inventory.getCurrentItem().getCount() - 1);
    }

    @Override
    public void dropStack() {
        player.dropItem(true);
    }

    @Override
    public boolean isSneak() {
        return player.isSneaking();
    }

    @Override
    public boolean isHandActive() {
        return player.isHandActive();
    }

    @Override
    public boolean staringAtMob() {
        return Minecraft.getInstance().objectMouseOver.entity != null;
    }

    @Override
    public boolean isCreative() {
        return player.isCreative();
    }

    @Override
    public boolean isFlying() {
        return player.abilities.isFlying;
    }

    @Override
    public void setSprinting(boolean sprint) {
        player.setSprinting(sprint);
    }
}
