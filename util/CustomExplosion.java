package com.xristopher.practicemod.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomExplosion
{
    /** whether or not the explosion sets fire to blocks around it */
    private final boolean causesFire;
    /** whether or not this explosion spawns smoke particles */
    private final boolean damagesTerrain;
    private final Random random;
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    private final Entity exploder;
    private final float size;
    /** A list of ChunkPositions of blocks affected by this explosion */
    private final List<BlockPos> affectedBlockPositions;
    /** Maps players to the knockback vector applied by the explosion, to send to the client */
    private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
    private final Vec3d position;

    @SideOnly(Side.CLIENT)
    public CustomExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions)
    {
        this(worldIn, entityIn, x, y, z, size, false, true, affectedPositions);
    }

    @SideOnly(Side.CLIENT)
    public CustomExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean causesFire, boolean damagesTerrain, List<BlockPos> affectedPositions)
    {
        this(worldIn, entityIn, x, y, z, size, causesFire, damagesTerrain);
        this.affectedBlockPositions.addAll(affectedPositions);
    }

    public CustomExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain)
    {
        this.random = new Random();
        this.affectedBlockPositions = Lists.<BlockPos>newArrayList();
        this.playerKnockbackMap = Maps.<EntityPlayer, Vec3d>newHashMap();
        this.world = worldIn;
        this.exploder = entityIn;
        this.size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        this.causesFire = flaming;
        this.damagesTerrain = damagesTerrain;
        this.position = new Vec3d(this.x, this.y, this.z);
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void explosionEffect(boolean spawnParticles)
    {
        this.world.playSound((EntityPlayer)null, this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.size >= 2.0F && this.damagesTerrain)
        {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
        }

        
    }

    public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
    {
        return this.playerKnockbackMap;
    }

    /**
     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
     */
    @Nullable
    public EntityLivingBase getExplosivePlacedBy()
    {
        if (this.exploder == null)
        {
            return null;
        }
        else if (this.exploder instanceof EntityTNTPrimed)
        {
            return ((EntityTNTPrimed)this.exploder).getTntPlacedBy();
        }
        else
        {
            return this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null;
        }
    }

    public void clearAffectedBlockPositions()
    {
        this.affectedBlockPositions.clear();
    }

    public List<BlockPos> getAffectedBlockPositions()
    {
        return this.affectedBlockPositions;
    }

    public Vec3d getPosition(){ return this.position; }
}