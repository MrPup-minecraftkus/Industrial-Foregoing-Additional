package net.mrpup.industrialforegoingadditional.module;

import com.buuz135.industrial.block.IndustrialBlockItem;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import com.hrznstudio.titanium.recipe.serializer.CodecRecipeSerializer;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.mrpup.industrialforegoingadditional.block.core.*;
import net.mrpup.industrialforegoingadditional.recipe.FactoryConstructorRecipe;
import net.mrpup.industrialforegoingadditional.recipe.PolishingMachineRecipe;
import net.mrpup.industrialforegoingadditional.recipe.SolidifierRecipe;
import net.mrpup.industrialforegoingadditional.recipe.UpgradedConstructorRecipe;
import net.neoforged.neoforge.registries.DeferredHolder;


public class ModuleCoreAdditional implements IModuleAdditional  {
    public static BlockWithTile FACTORY_CONSTRUCTOR;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FACTORY_CONSTRUCTOR_SERIALIZER;
    public static DeferredHolder<RecipeType<?>, RecipeType<?>> FACTORY_CONSTRUCTOR_TYPE;

    public static BlockWithTile UPGRADED_CONSTRUCTOR;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> UPGRADED_CONSTRUCTOR_SERIALIZER;
    public static DeferredHolder<RecipeType<?>, RecipeType<?>> UPGRADED_CONSTRUCTOR_TYPE;

    public static BlockWithTile POLISHING_MACHINE;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> POLISHING_MACHINE_SERIALIZER;
    public static DeferredHolder<RecipeType<?>, RecipeType<?>> POLISHING_MACHINE_TYPE;

    public static BlockWithTile SOLIDIFIER;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SOLIDIFIER_SERIALIZER;
    public static DeferredHolder<RecipeType<?>, RecipeType<?>> SOLIDIFIER_TYPE;

    public static BlockWithTile REPAIR_MACHINE;
    public static DeferredHolder<RecipeType<?>, RecipeType<?>> REPAIR_MACHINE_TYPE;

    public static TitaniumTab TAB_CORE = new TitaniumTab(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "core"));

    public void generateFeatures(DeferredRegistryHelper helper) {
        FACTORY_CONSTRUCTOR = helper.registerBlockWithTileItem("factory_constructor", FactoryConstructorBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE), TAB_CORE);
        FACTORY_CONSTRUCTOR_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "factory_constructor", () -> new CodecRecipeSerializer(FactoryConstructorRecipe.class, FACTORY_CONSTRUCTOR_TYPE, FactoryConstructorRecipe.CODEC));
        FACTORY_CONSTRUCTOR_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "factory_constructor", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "factory_constructor")));

        UPGRADED_CONSTRUCTOR = helper.registerBlockWithTileItem("upgraded_constructor", UpgradedConstructorBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE), TAB_CORE);
        UPGRADED_CONSTRUCTOR_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "upgraded_constructor", () -> new CodecRecipeSerializer(UpgradedConstructorRecipe.class, UPGRADED_CONSTRUCTOR_TYPE, UpgradedConstructorRecipe.CODEC));
        UPGRADED_CONSTRUCTOR_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "upgraded_constructor", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "upgraded_constructor")));

        POLISHING_MACHINE = helper.registerBlockWithTileItem("polishing_machine", PolishingMachineBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE), TAB_CORE);
        POLISHING_MACHINE_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "polishing_machine", () -> new CodecRecipeSerializer(PolishingMachineRecipe.class, POLISHING_MACHINE_TYPE, PolishingMachineRecipe.CODEC));
        POLISHING_MACHINE_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "polishing_machine", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "polishing_machine")));


        REPAIR_MACHINE = helper.registerBlockWithTileItem("repair_machine", RepairMachineBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE), TAB_CORE);
        REPAIR_MACHINE_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "repair_machine", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "repair_machine")));

        SOLIDIFIER = helper.registerBlockWithTileItem("solidifier", SolidifierBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE), TAB_CORE);
        SOLIDIFIER_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "solidifier", () -> new CodecRecipeSerializer(SolidifierRecipe.class, SOLIDIFIER_TYPE, SolidifierRecipe.CODEC));
        SOLIDIFIER_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "solidifier", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "solidifier")));
    }
}
