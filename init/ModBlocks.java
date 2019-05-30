package com.xristopher.practicemod.init;

import java.util.ArrayList;
import java.util.List;

import com.xristopher.practicemod.blocks.BlockBase;
import com.xristopher.practicemod.blocks.PortableHouseBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block PORTABLE_HOUSE_BLOCK = new PortableHouseBlock("portable_house_block", Material.TNT);
}
