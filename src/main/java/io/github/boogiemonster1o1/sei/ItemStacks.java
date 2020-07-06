package io.github.boogiemonster1o1.sei;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemStacks {
    public List<ItemStack> itemList = new ArrayList<>();
    public Set<String> itemNameSet = new HashSet<>();

    public ItemStacks() {
        for (Block block : Block.REGISTRY) {
            this.addStack(new ItemStack(block));
        }

        for (Item item : Item.REGISTRY) {
            this.addStack(new ItemStack(item));
        }
    }

    public void addItemAndSubItems(Item item) {
        if (item == null)
            return;

        List<ItemStack> subItems = new ArrayList<>();
        item.addToItemGroup(item, null, subItems);
        this.addItemStacks(subItems);

        if (subItems.isEmpty()) {
            ItemStack stack = new ItemStack(item);
            if (stack.getItem() == null) {
                return;
            }
            this.addItemStack(stack);
        }
    }

    public void addBlockAndSubBlocks(Block block) {
        if (block == null)
            return;

        Item item = Item.fromBlock(block);

        List<ItemStack> subItems = new ArrayList<>();
        if (item != null) {
            block.appendStacks(item, null, subItems);
            this.addItemStacks(subItems);
        }

        if (subItems.isEmpty()) {
            ItemStack stack = new ItemStack(block);
            if (stack.getItem() == null) {
                return;
            }
            addItemStack(stack);
        }
    }

    public void addStack(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        Item item = itemStack.getItem();

        if (item == null) {
            return;
        }

        if (itemStack.method_8391()) {
            List<ItemStack> subItems = new ArrayList<>();
            item.addToItemGroup(item, null, subItems);
            this.addItemStacks(subItems);
        } else {
            this.addItemStack(itemStack);
        }
    }

    public void addItemStacks(Iterable<ItemStack> stacks) {
//        for (ItemStack stack : stacks) {
//            this.addItemStack(stack);
//        }
        stacks.forEach(this::addItemStack);
    }

    public void addItemStack(ItemStack stack) {
//        if (itemNameSet.contains(stack.getTranslationKey()))
//            return;
//        itemNameSet.add(stack.getTranslationKey());
//        itemList.add(stack);
        String itemKey = uniqueIdentifierForStack(stack);
        if (stack.hasTag())
            itemKey += stack.getTag();

        if (itemNameSet.contains(itemKey))
            return;
        itemNameSet.add(itemKey);
        itemList.add(stack);
    }

    private String uniqueIdentifierForStack(ItemStack stack) {
        StringBuilder itemKey = new StringBuilder(stack.getTranslationKey());
        if (stack.hasTag())
            itemKey.append(":").append(stack.getTag().toString());
        return itemKey.toString();
    }
}
