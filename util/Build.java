package com.xristopher.practicemod.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public class Build {
	
	double bottomLeftX;
	double bottomLeftY;
	double bottomLeftZ;

	double topLeftX;
	double topLeftY;
	double topLeftZ;
	
	double bottomRightX;
	double bottomRightY;
	double bottomRightZ;
	
	double topRightX;
	double topRightY;
	double topRightZ;
	
	double x;
	double y;
	double z;
	
	int dimension;
	World world;
	IBlockState state;

	public Build(World world, IBlockState state, double x, double y, double z) {
		this.world = world; 
		this.state = state;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void buildHouse(int length) {
		buildFloor(8);
		dimension = length;
		buildLeftSide(8);
		buildRightSide(8);
		buildFrontSide(8);
		buildBackSide(8);
		clearInside();

	}
	
	public void clearInside() {
		
		BlockPos startClear = new BlockPos(x+1, y, z+1);
		BlockPos endClear = new BlockPos(x+6,y+5,z+6);
		
		Iterable<MutableBlockPos> blocks = BlockPos.getAllInBoxMutable(startClear, endClear);
		
		for(MutableBlockPos block : blocks) {
			world.setBlockState(block, Blocks.AIR.getDefaultState(), 2);	
		}
		
		
	}
	
	public void buildLeftSide(int length) {
		
		for(int i = 0; i < length; i++) {
			buildLineY(7, bottomLeftX+i, bottomLeftY, bottomLeftZ);
		}
	}
	
	public void buildRightSide(int length) {
		
		for(int i = 0; i < length; i++) {
			buildLineY(7, bottomRightX, bottomRightY, bottomRightZ-i);
		}
	}

	public void buildFrontSide(int length) {
	
		for(int i = 0; i < length; i++) {
			if(i == 3 || i == 4) {
				buildLineY(4, topLeftX, topLeftY+3, topLeftZ+i);
				BlockPos pos = new BlockPos(topLeftX, topLeftY+1, topLeftZ+i); 
				ItemDoor.placeDoor(world, pos, EnumFacing.WEST, Blocks.DARK_OAK_DOOR, false);				
			}
			else {
				buildLineY(7, topLeftX, topLeftY, topLeftZ+i);
			}
		}
	}
	
	public void buildBackSide(int length) {
		
		for(int i = 0; i < length; i++) {
			buildLineY(7, topRightX-i, topRightY, topRightZ);
		}
		
	}
	
	public void buildFloor(int length) {
		for(int i = 0; i < length; i++) {
			buildLineX(length, x, y-1, z+i);
			if(i == 0) {
				bottomLeftX = x;
				bottomLeftY = y-1;
				bottomLeftZ = z+i;
				
				topLeftX = x+length-1;
				topLeftY = y-1;
				topLeftZ = z+i;
			}
			
			if(i == length - 1) {
				bottomRightX = x;
				bottomRightY = y-1;
				bottomRightZ = z+i;
				
				topRightX = x+length-1;
				topRightY = y-1;
				topRightZ = z+i;
			}
		}
		
		//buildLineY(length, bottomLeftX, bottomLeftY, bottomLeftZ);
		//buildLineY(length, topLeftX, topLeftY, topLeftZ);
		//buildLineY(length, bottomRightX, bottomRightY, bottomRightZ);
		//buildLineY(length, topRightX, topRightY, topRightZ);
	}
	
	public void buildLineX(int length, double x, double y, double z) {
		for(int i = 0; i < length; i++) {
			buildOne(x+i, y, z);
		}
	}
	
	public void buildLineY(int length, double x, double y, double z) {
		for(int i = 0; i < length; i++) {
			buildOne(x, y+i, z);
		}
	}
	
	public void buildLineZ(int length, double x, double y, double z) {
		for(int i = 0; i < length; i++) {
			buildOne(x, y, z+i);
		}
	}
	
	public void buildOne(double x, double y, double z) {
		BlockPos pos = new BlockPos(x, y, z);
		world.setBlockState(pos, state, 2);	
	}
	
	public void buildOne() {
		BlockPos pos = new BlockPos(x, y, z);
		world.setBlockState(pos, state, 2);	
	}
	

}
