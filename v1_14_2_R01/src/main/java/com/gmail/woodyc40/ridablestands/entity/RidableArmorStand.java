package com.gmail.woodyc40.ridablestands.entity;

import net.minecraft.server.v1_14_R1.*;

import java.lang.reflect.Field;
import java.util.List;

public class RidableArmorStand extends EntityArmorStand {
    private static final double JUMP_CONSTANT = 0.42;
    private static final Field IS_JUMPING;

    static {
        try {
            IS_JUMPING = EntityLiving.class.getDeclaredField("jumping");
            IS_JUMPING.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }

    public RidableArmorStand(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
        super(entitytypes, world);
    }

    @Override
    public void e(Vec3D vec3d) {
        List<Entity> passengers = this.getPassengers();

        // No rider, let the stock method handle
        if (passengers.isEmpty()) {
            super.e(vec3d);
            return;
        }

        EntityLiving passenger = (EntityLiving) passengers.get(0);

        // Not a player controlling, let the stock method handle
        if (!(passenger instanceof EntityPlayer)) {
            super.e(vec3d);
            return;
        }

        // Modify head angles
        // Code remains the same as EntityHorseAbstract
        this.yaw = passenger.yaw;
        this.lastYaw = this.yaw;
        this.pitch = passenger.pitch * 0.5F;
        this.setYawPitch(this.yaw, this.pitch);
        this.aK = this.yaw;
        this.aM = this.aK;

        // Not sure why this is needed, but I've kept
        // it from EntityHorseAbstract nonetheless
        double speed = passenger.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
        this.o((float) speed);

        // Actual movement
        double f = passenger.bb;
        double f1 = passenger.bd;
        super.e(new Vec3D(f, vec3d.y, f1));

        // Jumping logic
        Vec3D mot = this.getMot();
        double y;
        try {
            boolean isStartingToJump = (boolean) IS_JUMPING.get(passenger) && this.onGround;
            y = isStartingToJump ? JUMP_CONSTANT : mot.y;
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        this.setMot(mot.x, y, mot.z);
    }
}
