package com.github.fernthedev.controllerremapmod;

import com.github.fernthedev.controllerremapmod.core.IControlPlayer;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

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
//        player.setPositionAndRotationDirect(player.posX,player.posY,player.posZ,player.rotationYaw + value,player.rotationPitch,1,false);
        player.rotateTowards(value, 0);
    }

    @Override
    public void addRotationPitch(float value) {
        player.rotateTowards(0, value);
//        player.setPositionAndRotationDirect(player.posX,player.posY,player.posZ,player.rotationYaw,player.rotationPitch + value,1,false);
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

    @Override
    public boolean getIsHittingBlock() {
        return Minecraft.getInstance().playerController.getIsHittingBlock();
    }

    @Override
    public void rotateTowards(double yaw, double pitch) {
        player.rotateTowards(yaw, pitch);
    }

    @Override
    public void setRotation(double yaw, double pitch) {
        player.rotationPitch = (float) pitch;
        player.rotationYaw = (float) yaw;
    }

    @Override
    public double getRotationPitch() {
        return player.rotationPitch;
    }

    @Override
    public double getRotationYaw() {
        return player.rotationYaw;
    }

    @Override
    public void resetBlockRemoving() {
        Minecraft.getInstance().playerController.resetBlockRemoving();
    }

    @Override
    public boolean staringAtAir() {
        return Minecraft.getInstance().objectMouseOver.hitInfo == RayTraceResult.Type.MISS;
    }

    @Override
    public void onStoppedUsingItem() {
        Minecraft.getInstance().playerController.onStoppedUsingItem(player);
    }

    @Override
    public boolean staringAtBlock() {
        return Minecraft.getInstance().objectMouseOver.type == RayTraceResult.Type.BLOCK;
    }

    @Override
    public boolean isObjectMouseOverNull() {
        return Minecraft.getInstance().objectMouseOver == null;
    }
}
