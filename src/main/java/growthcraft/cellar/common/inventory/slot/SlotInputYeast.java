package growthcraft.cellar.common.inventory.slot;

import growthcraft.core.shared.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputYeast extends SlotInput
{
	public SlotInputYeast(IInventory inv, int x, int y, int z)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}
}
