package com.cout970.magneticraft.block

import com.cout970.magneticraft.block.core.BlockBase
import com.cout970.magneticraft.block.core.BlockBuilder
import com.cout970.magneticraft.block.core.IBlockMaker
import com.cout970.magneticraft.block.core.IStatesEnum
import com.cout970.magneticraft.item.itemblock.itemBlockListOf
import com.cout970.magneticraft.misc.CreativeTabMg
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemBlock
import net.minecraft.util.IStringSerializable

/**
 * Created by cout970 on 2017/06/11.
 */
object Ores : IBlockMaker{

    val PROPERTY_ORE_TYPE = PropertyEnum.create("ore_type", OreType::class.java)!!

    lateinit var ores: BlockBase private set

    override fun initBlocks(): List<Pair<Block, ItemBlock>> {
        val builder = BlockBuilder().apply {
            material = Material.ROCK
            creativeTab = CreativeTabMg
            states = OreType.values().toList()
        }

        ores = builder.withName("ores").build()

        ores.setHarvestLevel("pickaxe", 1, OreType.COPPER.getBlockState(ores))
        ores.setHarvestLevel("pickaxe", 1, OreType.LEAD.getBlockState(ores))
        ores.setHarvestLevel("pickaxe", 2, OreType.COBALT.getBlockState(ores))
        ores.setHarvestLevel("pickaxe", 2, OreType.TUNGSTEN.getBlockState(ores))
        ores.setHarvestLevel("pickaxe", 1, OreType.PYRITE.getBlockState(ores))

        return itemBlockListOf(ores)
    }

    enum class OreType(override val stateName: String,
                             override val isVisible: Boolean) : IStatesEnum, IStringSerializable {

        COPPER("copper", true),
        LEAD("lead", true),
        COBALT("cobalt", true),
        TUNGSTEN("tungsten", true),
        PYRITE("pyrite", true);

        override fun getName() = name.toLowerCase()
        override val properties: List<IProperty<*>> get() = listOf(PROPERTY_ORE_TYPE)

        override fun getBlockState(block: Block): IBlockState {
            return block.defaultState.withProperty(PROPERTY_ORE_TYPE, this)
        }
    }
}