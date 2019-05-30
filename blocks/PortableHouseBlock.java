package com.xristopher.practicemod.blocks;

import com.xristopher.practicemod.util.Build;
import com.xristopher.practicemod.util.Smoke;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;


public class PortableHouseBlock extends BlockBase {

    public static final PropertyBool EXPLODE = PropertyBool.create("explode");

	public PortableHouseBlock(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.METAL);
		setHardness(5.0f);
		setResistance(15.0f);
	}
	
	
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE))
        {
            this.explode(worldIn, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)), playerIn);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

            if (itemstack.getItem() == Items.FLINT_AND_STEEL)
            {
                itemstack.damageItem(1, playerIn);
            }
            else if (!playerIn.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }

            return true;
        }
        else
        {
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        }
    }
	
	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter)
    {
        if (!worldIn.isRemote)
        {
            if (((Boolean)state.getValue(EXPLODE)).booleanValue())
            {
                //EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), igniter);
                //worldIn.spawnEntity(entitytntprimed);
                
                Smoke smoke = new Smoke(); 
                smoke.createExplosion(worldIn, (EntityPlayer)null,(double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), 4.0f, false);
                Build build = new Build(worldIn, Blocks.BRICK_BLOCK.getDefaultState(), (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F));
                build.buildHouse(8);
        		build.buildOne(); //fills in initial hole

                //worldIn.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }
	
	/**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(EXPLODE, Boolean.valueOf((meta & 1) > 0));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(EXPLODE)).booleanValue() ? 1 : 0;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {EXPLODE});
    }

}
