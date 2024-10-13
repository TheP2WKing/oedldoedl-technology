package net.thep2wking.oedldoedltechnology.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictItemStack {
    private final ItemStack itemStack;
    private final String oreDictEntry;
    private final int count;

    public OreDictItemStack(@Nonnull ItemStack itemStack, int count) {
        this.itemStack = itemStack;
        this.oreDictEntry = null;
        this.count = count;
    }

    public OreDictItemStack(@Nonnull String oreDictEntry, int count) {
        this.itemStack = ItemStack.EMPTY;
        this.oreDictEntry = oreDictEntry;
        this.count = count;
    }

    public boolean isOreDict() {
        return oreDictEntry != null;
    }

    @Nonnull
    public List<ItemStack> getItemStackList() {
        List<ItemStack> stacks = new ArrayList<>();
        if (isOreDict()) {
            List<ItemStack> oreDictItems = OreDictionary.getOres(oreDictEntry);
            for (ItemStack stack : oreDictItems) {
                ItemStack copy = stack.copy();
                copy.setCount(count);
                stacks.add(copy);
            }
        } else {
            ItemStack stack = itemStack.copy();
            stack.setCount(count);
            stacks.add(stack);
        }
        return stacks;
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getOreDictEntry() {
        return oreDictEntry;
    }

    public int getCount() {
        return count;
    }
}