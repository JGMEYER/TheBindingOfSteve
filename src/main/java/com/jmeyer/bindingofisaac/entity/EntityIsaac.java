package com.jmeyer.bindingofisaac.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityIsaac extends EntityLiving {

    public EntityIsaac(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.99F); // EntitySkeleton proportions
    }

}
