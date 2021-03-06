package mechconstruct.gui.blueprint;

import mechconstruct.gui.MechContainer;
import mechconstruct.util.EnergyHandler;
import mechconstruct.util.FluidHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public interface IBlueprintProvider {

	public boolean hasItemInventory();
	public boolean hasUpgradeInventory();
	public boolean hasEnergyInventory();
	public boolean hasFluidInventory();
	public ItemStackHandler getItemInventory();
	public ItemStackHandler getUpgradeInventory();
	public EnergyHandler getEnergyInventory();
	public FluidHandler getFluidInventory();

	public String getNameToDisplay();

	public List<GuiTabBlueprint> getGuiTabBlueprints();

	public GuiTabBlueprint getCurrentTab();

	public void setCurrentTab(GuiTabBlueprint blueprint);

	public void setCurrentTab(int tabId);

	public ProviderType getProviderType();

	public boolean canInteractWith(EntityPlayer player);

	public MechContainer getContainer(GuiTabBlueprint blueprint, EntityPlayer player);

	public enum ProviderType {
		MACHINE, ITEM
	}
}
