package mechconstruct.registry;

import mechconstruct.MechConstruct;
import mechconstruct.blockentities.BlockEntityMachine;
import mechconstruct.registry.recipes.basic.BasicFurnaceRecipes;
import mechconstruct.util.shootingstar.ShootingStar;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MechConstruct.MOD_ID)
public class MechRegistry {
	private static ArrayList<Item> items = new ArrayList<>();
	private static ArrayList<Block> blocks = new ArrayList<>();
	private static ArrayList<Class<? extends BlockEntityMachine>> blockEntities = new ArrayList<>();

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ShootingStar.registerModels(MechConstruct.MOD_ID);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : MechItems.ITEMS) {
			event.getRegistry().register(item);
		}
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block block : MechBlocks.BLOCKS) {
			event.getRegistry().register(block);
		}
	}

	public static void registerBlockEntities() {
		for (String entityName : MechBlocks.BLOCK_ENTITIES.keySet()) {
			GameRegistry.registerTileEntity(MechBlocks.BLOCK_ENTITIES.get(entityName), entityName);
		}
	}

	public static void registerRecipes() {
		BasicFurnaceRecipes.register();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void tooltipRender(RenderTooltipEvent.Pre event) {
		event.setCanceled(true);
		List<String> textLines = new ArrayList<>();
		textLines.addAll(event.getLines());
		int mouseX = event.getX();
		int mouseY = event.getY();
		int screenWidth = event.getScreenWidth();
		int screenHeight = event.getScreenHeight();
		int maxTextWidth = event.getMaxWidth();
		FontRenderer font = event.getFontRenderer();

		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		int tooltipTextWidth = 0;

		for (String textLine : textLines) {
			int textLineWidth = font.getStringWidth(textLine);

			if (textLineWidth > tooltipTextWidth) {
				tooltipTextWidth = textLineWidth;
			}
		}

		boolean needsWrap = false;

		int titleLinesCount = 1;
		int tooltipX = mouseX + 12;
		if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
			tooltipX = mouseX - 16 - tooltipTextWidth;
			if (tooltipX < 4) // if the tooltip doesn't fit on the screen
			{
				if (mouseX > screenWidth / 2) {
					tooltipTextWidth = mouseX - 12 - 8;
				} else {
					tooltipTextWidth = screenWidth - 16 - mouseX;
				}
				needsWrap = true;
			}
		}

		if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
			tooltipTextWidth = maxTextWidth;
			needsWrap = true;
		}

		if (needsWrap) {
			int wrappedTooltipWidth = 0;
			List<String> wrappedTextLines = new ArrayList<String>();
			for (int i = 0; i < textLines.size(); i++) {
				String textLine = textLines.get(i);
				List<String> wrappedLine = font.listFormattedStringToWidth(textLine, tooltipTextWidth);
				if (i == 0) {
					titleLinesCount = wrappedLine.size();
				}

				for (String line : wrappedLine) {
					int lineWidth = font.getStringWidth(line);
					if (lineWidth > wrappedTooltipWidth) {
						wrappedTooltipWidth = lineWidth;
					}
					wrappedTextLines.add(line);
				}
			}
			tooltipTextWidth = wrappedTooltipWidth;
			textLines = wrappedTextLines;

			if (mouseX > screenWidth / 2) {
				tooltipX = mouseX - 16 - tooltipTextWidth;
			} else {
				tooltipX = mouseX + 12;
			}
		}

		int tooltipY = mouseY - 12;
		int tooltipHeight = 8;

		if (textLines.size() > 1) {
			tooltipHeight += (textLines.size() - 1) * 10;
			if (textLines.size() > titleLinesCount) {
				tooltipHeight += 2; // gap between title lines and next lines
			}
		}

		if (tooltipY + tooltipHeight + 6 > screenHeight) {
			tooltipY = screenHeight - tooltipHeight - 6;
		}

		final int zLevel = 300;
		int backgroundX = tooltipX - 3;
		int backgroundY = tooltipY - 3;
		int backgroundWidth = tooltipTextWidth + 5;
		int backgroundHeight = tooltipHeight + 6;
		GuiUtils.drawGradientRect(zLevel, backgroundX, backgroundY, backgroundX + backgroundWidth, backgroundY + backgroundHeight, 0xF0930000, 0xF0930000);
		GuiUtils.drawGradientRect(zLevel, backgroundX - 1, backgroundY, backgroundX, backgroundY + backgroundHeight, 0xF01B0202, 0xF01B0202);
		GuiUtils.drawGradientRect(zLevel, backgroundX, backgroundY - 1, backgroundX + backgroundWidth, backgroundY, 0xF01B0202, 0xF01B0202);
		GuiUtils.drawGradientRect(zLevel, backgroundX + backgroundWidth, backgroundY, backgroundX + backgroundWidth + 1, backgroundY + backgroundHeight, 0xF01B0202, 0xF01B0202);
		GuiUtils.drawGradientRect(zLevel, backgroundX, backgroundY + backgroundHeight, backgroundX + backgroundWidth, backgroundY + backgroundHeight + 1, 0xF01B0202, 0xF01B0202);
		GuiUtils.drawGradientRect(zLevel, backgroundX + 1, backgroundY + 1, backgroundX + backgroundWidth - 1, backgroundY + backgroundHeight - 1, 0xF01B0202, 0xF01B0202);
		int tooltipTop = tooltipY;

		for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
			String line = textLines.get(lineNumber);
			font.drawStringWithShadow(line, tooltipX, tooltipY, -1);

			if (lineNumber + 1 == titleLinesCount) {
				tooltipY += 2;
			}

			tooltipY += 10;
		}

		MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostText(event.getStack(), textLines, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));

		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableRescaleNormal();
	}
}

