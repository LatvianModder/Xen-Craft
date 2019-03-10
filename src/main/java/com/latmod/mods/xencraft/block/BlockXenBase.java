package com.latmod.mods.xencraft.block;

import com.latmod.mods.xencraft.XenCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author LatvianModder
 */
public class BlockXenBase extends Block implements IXenBlock
{
	public static final PropertyEnum<EnumXenColor> COLOR = PropertyEnum.create("color", EnumXenColor.class);

	public BlockXenBase()
	{
		super(Material.ROCK, MapColor.GRAY);
		setHardness(1F);
		setDefaultState(blockState.getBaseState().withProperty(COLOR, EnumXenColor.WHITE));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, COLOR);
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(COLOR, EnumXenColor.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(COLOR).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(COLOR).getMetadata();
	}

	@Override
	@Deprecated
	public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.getValue(COLOR).getMapColor();
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		for (EnumXenColor color : EnumXenColor.VALUES)
		{
			items.add(new ItemStack(this, 1, color.getMetadata()));
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return true;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return XenCraft.PROXY.getXenLightValue(world, getXenLayer());
	}

	@Override
	public BlockRenderLayer getXenLayer()
	{
		return BlockRenderLayer.SOLID;
	}

	@Override
	@Deprecated
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 0F;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(I18n.format(EnumXenColor.byMeta(stack.getMetadata()).getTranslationKey()));
	}
}