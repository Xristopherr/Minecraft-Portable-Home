package com.xristopher.practicemod.util;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class Smoke {
	
	 /**
     * Creates an explosion in the world.
     */
    public Explosion createExplosion(World world, @Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking)
    {
        return world.newExplosion(entityIn, x, y, z, strength, false, isSmoking);
    }

    /**
     * returns a new explosion. Does initiation (at time of writing Explosion is not finished)
     */
    public CustomExplosion newExplosion(World world, @Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking)
    {
        CustomExplosion explosion = new CustomExplosion(world, entityIn, x, y, z, strength, isFlaming, isSmoking);
        explosion.explosionEffect(true);
        return explosion;
    }
    
    


}
