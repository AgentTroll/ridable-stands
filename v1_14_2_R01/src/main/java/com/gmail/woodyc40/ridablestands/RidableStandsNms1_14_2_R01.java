package com.gmail.woodyc40.ridablestands;

import com.gmail.woodyc40.ridablestands.entity.RidableArmorStand;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.Objects;

public class RidableStandsNms1_14_2_R01 implements RidableStandsNms {
    private EntityTypes<EntityArmorStand> armorStandType = EntityTypes.ARMOR_STAND;

    @Override
    public void registerEntity() {
        armorStandType = register(1, "ridable_armor_stand", "armor_stand", RidableArmorStand::new, EnumCreatureType.MISC);
    }

    // Lifted from: https://www.spigotmc.org/threads/1-14-nms-registering-custom-entity.371482/
    private static <T extends Entity> EntityTypes<T> register(int id, String name, String superTypeName, EntityTypes.b producer, EnumCreatureType type) {
        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a()
                        .getSchema(DataFixUtils.makeKey(SharedConstants.a().getWorldVersion()))
                        .findChoiceType(DataConverterTypes.ENTITY)
                        .types();
        String keyName = "minecraft:" + name;
        dataTypes.put(keyName, dataTypes.get("minecraft:" + superTypeName));

        EntityTypes.a<T> a = EntityTypes.a.a(producer, type);
        return IRegistry.a(IRegistry.ENTITY_TYPE, id, keyName, a.a(name));
    }

    @Override
    public boolean canMount(@NonNull ArmorStand as) {
        EntityArmorStand handle = ((CraftArmorStand) as).getHandle();
        return handle instanceof RidableArmorStand;
    }

    @Override
    public @NonNull ArmorStand spawnNewEntity(@NonNull Location spawnLocation) {
        World world = Objects.requireNonNull(spawnLocation.getWorld());
        WorldServer worldServer = ((CraftWorld) world).getHandle();

        RidableArmorStand ras = new RidableArmorStand(this.armorStandType, worldServer);
        ras.getWorld().addEntity(ras);

        ArmorStand as = (ArmorStand) ras.getBukkitEntity();
        as.teleport(spawnLocation);

        return as;
    }
}
