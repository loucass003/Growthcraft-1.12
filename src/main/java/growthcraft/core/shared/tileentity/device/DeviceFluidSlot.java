package growthcraft.core.shared.tileentity.device;

import growthcraft.core.shared.fluids.IFluidTanks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class DeviceFluidSlot
{
	private IFluidTanks tanks;
	private int index;

	/**
	 * @param src - source fluid tanks
	 * @param idx - fluid tank index
	 */
	public DeviceFluidSlot(IFluidTanks src, int idx)
	{
		this.tanks = src;
		this.index = idx;
	}

	public FluidStack get()
	{
		return tanks.getFluidStack(index);
	}

	public Fluid getFluid()
	{
		final FluidStack stack = get();
		if (stack == null) return null;
		return stack.getFluid();
	}

	public int getAmount()
	{
		final FluidStack stack = get();
		if (stack == null) return 0;
		return stack.amount;
	}

	public int getCapacity()
	{
		return tanks.getFluidTank(index).getCapacity();
	}

	public int getAvailableCapacity()
	{
		return getCapacity() - getAmount();
	}

	/**
	 * Sets the fluid slot's fluid stack
	 */
	public void set(FluidStack newStack)
	{
		tanks.setFluidStack(index, newStack);
	}

	/**
	 * Clears the fluid slot
	 */
	public void clear()
	{
		tanks.clearTank(index);
	}

	/**
	 * Does the slot have ANY fluid content?
	 *
	 * @return true, the slot contains a fluid, false otherwise.
	 */
	public boolean hasContent()
	{
		return tanks.isFluidTankFilled(index);
	}

	/**
	 * Is the slot full?
	 *
	 * @return true, the slot has reached its maximum capacity, false otherwise.
	 */
	public boolean isFull()
	{
		return tanks.isFluidTankFull(index);
	}

	/**
	 * Is the slot empty?
	 *
	 * @return true, the slot has no valid fluid, false otherwise.
	 */
	public boolean isEmpty()
	{
		return tanks.isFluidTankEmpty(index);
	}

	/**
	 * Does the provided fluid match the one in the slot?
	 *
	 * @param stack - fluid stack to test
	 * @return true, it has the same fluid, false otherwise
	 */
	public boolean hasMatching(FluidStack stack)
	{
		final FluidStack s = get();
		if (stack == null)
		{
			return s == null;
		}
		else
		{
			if (s != null) return stack.isFluidEqual(s);
		}
		return true;
	}

	/**
	 * Does the slot have the same fluid, and the capacity to hold the stack?
	 *
	 * @param stack - fluid stack to test
	 * @return true, it has the same fluid and has capacity, false otherwise
	 */
	public boolean hasMatchingWithCapacity(FluidStack stack)
	{
		if (!isEmpty())
		{
			if (!hasMatching(stack)) return false;
		}
		return getAvailableCapacity() >= stack.amount;
	}

	/**
	 * This is a variation of hasMatchingWithCapacity, this version will accept
	 * the stack if the internal stack is null unlike the former.
	 *
	 * @param stack - fluid stack to test
	 * @return true, the slot has capacity for the provided stack
	 */
	public boolean hasCapacityFor(FluidStack stack)
	{
		if (hasContent())
		{
			if (!hasMatching(stack)) return false;
		}
		return getAvailableCapacity() >= stack.amount;
	}

	/**
	 * Does the slot contain the same fluid, and its amount is greater or equal to the given?
	 *
	 * @param stack - fluid stack to test
	 * @return true, it has the same fluid and has greater than or equal to the stack size;
	 */
	public boolean hasEnough(FluidStack stack)
	{
		if (!hasMatching(stack)) return false;
		final FluidStack s = get();
		if (s.amount >= stack.amount)
		{
			return true;
		}
		return false;
	}

	public boolean hasEnough(int amount)
	{
		return getAmount() >= amount;
	}

	public FluidStack consume(int amount, boolean doDrain)
	{
		return tanks.drainFluidTank(index, amount, doDrain);
	}

	public FluidStack consume(FluidStack stack, boolean doDrain)
	{
		if (hasMatching(stack))
		{
			return consume(stack.amount, doDrain);
		}
		return null;
	}

	public int fill(FluidStack fluid, boolean doFill)
	{
		return tanks.fillFluidTank(index, fluid, doFill);
	}
}
