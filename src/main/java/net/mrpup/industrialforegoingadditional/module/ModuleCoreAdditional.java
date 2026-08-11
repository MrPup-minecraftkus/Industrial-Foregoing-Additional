package net.mrpup.industrialforegoingadditional.module;

import com.buuz135.industrial.block.IndustrialBlockItem;
import com.hrznstudio.titanium.fluid.ClientFluidTypeExtensions;
import com.hrznstudio.titanium.fluid.TitaniumFluidInstance;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.DeferredRegistryHelper;
import com.hrznstudio.titanium.recipe.serializer.CodecRecipeSerializer;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.mrpup.industrialforegoingadditional.IndustrialForegoingAdditional;
import net.mrpup.industrialforegoingadditional.block.core.*;
import net.mrpup.industrialforegoingadditional.block.survival.BlockDetectorBlock;
import net.mrpup.industrialforegoingadditional.block.survival.RepairMachineBlock;
import net.mrpup.industrialforegoingadditional.block.survival.SolidifierBlock;
import net.mrpup.industrialforegoingadditional.recipe.core.FactoryConstructorRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.PolishingMachineRecipe;
import net.mrpup.industrialforegoingadditional.recipe.survival.SolidifierRecipe;
import net.mrpup.industrialforegoingadditional.recipe.core.UpgradedConstructorRecipe;
// import net.mrpup.industrialforegoingadditional.block.survival.SifterMachineBlock;
//import net.mrpup.industrialforegoingadditional.recipe.survival.SifterMachineRecipe;
import net.neoforged.neoforge.fluids.FluidType;
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

    public static BlockWithTile BLOCK_DETECTOR;

    //public static BlockWithTile SIFTER_MACHINE;
    //public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> SIFTER_MACHINE_SERIALIZER;
    //public static DeferredHolder<RecipeType<?>, RecipeType<?>> SIFTER_MACHINE_TYPE;

    public static TitaniumFluidInstance DARKEST_VOID;
    public static TitaniumFluidInstance DIRTY_MINERAL_WATER;

    public static TitaniumTab TAB_CORE_ADDITIONAL = new TitaniumTab(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "core"));
    public static TitaniumTab TAB_SURVIVAL = new TitaniumTab(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "survival"));

    public void generateFeatures(DeferredRegistryHelper helper) {

        DARKEST_VOID = new TitaniumFluidInstance(helper, "darkest_void", FluidType.Properties.create().density(1000), new ClientFluidTypeExtensions(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "block/fluids/darkest_void_still"), ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "block/fluids/darkest_void_flow")), TAB_CORE_ADDITIONAL);
        DIRTY_MINERAL_WATER = new TitaniumFluidInstance(helper, "dirty_mineral_water", FluidType.Properties.create().density(1000), new ClientFluidTypeExtensions(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "block/fluids/dirty_mineral_water_still"), ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "block/fluids/dirty_mineral_water_flow")), TAB_CORE_ADDITIONAL);

        FACTORY_CONSTRUCTOR = helper.registerBlockWithTileItem("factory_constructor", FactoryConstructorBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE_ADDITIONAL), TAB_CORE_ADDITIONAL);
        FACTORY_CONSTRUCTOR_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "factory_constructor", () -> new CodecRecipeSerializer(FactoryConstructorRecipe.class, FACTORY_CONSTRUCTOR_TYPE, FactoryConstructorRecipe.CODEC));
        FACTORY_CONSTRUCTOR_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "factory_constructor", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "factory_constructor")));

        UPGRADED_CONSTRUCTOR = helper.registerBlockWithTileItem("upgraded_constructor", UpgradedConstructorBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE_ADDITIONAL), TAB_CORE_ADDITIONAL);
        UPGRADED_CONSTRUCTOR_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "upgraded_constructor", () -> new CodecRecipeSerializer(UpgradedConstructorRecipe.class, UPGRADED_CONSTRUCTOR_TYPE, UpgradedConstructorRecipe.CODEC));
        UPGRADED_CONSTRUCTOR_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "upgraded_constructor", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "upgraded_constructor")));

        POLISHING_MACHINE = helper.registerBlockWithTileItem("polishing_machine", PolishingMachineBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_CORE_ADDITIONAL), TAB_CORE_ADDITIONAL);
        POLISHING_MACHINE_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "polishing_machine", () -> new CodecRecipeSerializer(PolishingMachineRecipe.class, POLISHING_MACHINE_TYPE, PolishingMachineRecipe.CODEC));
        POLISHING_MACHINE_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "polishing_machine", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "polishing_machine")));


        REPAIR_MACHINE = helper.registerBlockWithTileItem("repair_machine", RepairMachineBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_SURVIVAL), TAB_SURVIVAL);
        REPAIR_MACHINE_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "repair_machine", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "repair_machine")));

        SOLIDIFIER = helper.registerBlockWithTileItem("solidifier", SolidifierBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_SURVIVAL), TAB_SURVIVAL);
        SOLIDIFIER_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "solidifier", () -> new CodecRecipeSerializer(SolidifierRecipe.class, SOLIDIFIER_TYPE, SolidifierRecipe.CODEC));
        SOLIDIFIER_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "solidifier", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "solidifier")));

        BLOCK_DETECTOR = IndustrialForegoingAdditional.INSTANCE.getRegistries().registerBlockWithTileItem("block_detector", () -> new BlockDetectorBlock(), (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_SURVIVAL), TAB_SURVIVAL);

        //SIFTER_MACHINE = helper.registerBlockWithTileItem("sifter_machine", SifterMachineBlock::new, (blockRegistryObject) -> () -> new IndustrialBlockItem((Block)blockRegistryObject.get(), TAB_SURVIVAL), TAB_SURVIVAL);
        //SIFTER_MACHINE_SERIALIZER = helper.registerGeneric(Registries.RECIPE_SERIALIZER, "sifter_machine", () -> new CodecRecipeSerializer(SifterMachineRecipe.class, SIFTER_MACHINE_TYPE, SifterMachineRecipe.CODEC));
        //SIFTER_MACHINE_TYPE = helper.registerGeneric(Registries.RECIPE_TYPE, "sifter_machine", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath("industrialforegoingadditional", "sifter_machine")));

    }
}
