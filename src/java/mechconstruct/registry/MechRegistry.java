package mechconstruct.registry;

import mechconstruct.MechConstruct;
import mechconstruct.block.BlockMachine;
import mechconstruct.blockentities.BlockEntityMachine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MechConstruct.MOD_ID)
public class MechRegistry {
    private static ArrayList<Item> items = new ArrayList<>();
    private static ArrayList<Block> blocks = new ArrayList<>();
    private static ArrayList<Class<? extends BlockEntityMachine>> blockEntities = new ArrayList<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Class clazz = MechItems.class;
        for (Field field : clazz.getFields()) {
            if (Item.class.isAssignableFrom(field.getType())) {
                try {
                    Item item = (Item) field.get(clazz);
                    if (item.getRegistryName() == null) {
                        item.setRegistryName(MechConstruct.MOD_ID, field.getName().toLowerCase());
                    }
                    if (item.getUnlocalizedName().equals("item.null")) {
                        item.setUnlocalizedName(MechConstruct.MOD_ID + "." + field.getName().toLowerCase());
                    }
                    item.setCreativeTab(MechConstruct.TAB);
                    event.getRegistry().register(item);
                    items.add(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Block block : blocks) {
            if (block instanceof BlockMachine) {
                ItemBlock itemBlock = new ItemBlock(block);
                itemBlock.setRegistryName(block.getRegistryName());
                itemBlock.setUnlocalizedName(block.getUnlocalizedName());
                itemBlock.setCreativeTab(MechConstruct.TAB);
                event.getRegistry().register(itemBlock);
                items.add(itemBlock);
            }
        }
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Class clazz = MechBlocks.class;
        for (Field field : clazz.getFields()) {
            if (Block.class.isAssignableFrom(field.getType())) {
                try {
                    Block block = (Block) field.get(clazz);
                    if (block instanceof BlockMachine) {
                        if (((BlockMachine) block).entityClass == null) {
                            continue;
                        }
                        blockEntities.add(((BlockMachine) block).entityClass);
                    }
                    if (block.getRegistryName() == null) {
                        block.setRegistryName(MechConstruct.MOD_ID, field.getName().toLowerCase());
                    }
                    if (block.getUnlocalizedName().equals("tile.null")) {
                        block.setUnlocalizedName(MechConstruct.MOD_ID + "." + field.getName().toLowerCase());
                    }
                    block.setCreativeTab(MechConstruct.TAB);
                    event.getRegistry().register(block);
                    blocks.add(block);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void registerBlockEntities() {
        for (Class<? extends BlockEntityMachine> bem : blockEntities) {
            GameRegistry.registerTileEntity(bem, bem.getName());
        }
    }
}
