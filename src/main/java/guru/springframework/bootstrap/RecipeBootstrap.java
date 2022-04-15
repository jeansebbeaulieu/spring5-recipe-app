package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        log.debug("Created RecipeBootstrap");
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Context Refresh Event " + contextRefreshedEvent.toString());

        recipeRepository.saveAll(getRecipes());

        log.info("Context Refresh Event terminated");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        if(!eachUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!tableSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!teaSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if(!dashUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if(!pintUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if(!cupsUomOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();


        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if(!americanCategoryOptional.isPresent())
            throw new RuntimeException("Expected Category Not Found");

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanCategoryOptional.isPresent())
            throw new RuntimeException("Expected Category Not Found");

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setDirections("1. Cut the avocado\n"+
                "2. Mash the avocado flesh\n"+
                "3. Add remaining ingredient to taste\n"+
                "4. Serve immediately");

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        perfectGuacamoleNotes.setRecipe(perfectGuacamole);

        perfectGuacamole.setNotes(perfectGuacamoleNotes);

        perfectGuacamole.addIngredient(new Ingredient("ripe avocados", BigDecimal.valueOf(2), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.25), teaUom));
        perfectGuacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", BigDecimal.valueOf(2), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", BigDecimal.valueOf(1), tableSpoonUom));
        perfectGuacamole.addIngredient(new Ingredient("serrano (or jalapeño) chilis, stems and seeds removed, minced", BigDecimal.valueOf(2), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("cilantro", BigDecimal.valueOf(6), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("black pepper", BigDecimal.valueOf(7), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("tomato", BigDecimal.valueOf(6), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("red radish or jicama slices", BigDecimal.valueOf(2), eachUom));
        perfectGuacamole.addIngredient(new Ingredient("tortilla chips", BigDecimal.valueOf(5), eachUom));

        perfectGuacamole.getCategories().add(americanCategory);
        perfectGuacamole.getCategories().add(mexicanCategory);

        recipes.add(perfectGuacamole);


        Recipe spicyGrilledChickenTacos = new Recipe();
        spicyGrilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        spicyGrilledChickenTacos.setPrepTime(20);
        spicyGrilledChickenTacos.setCookTime(15);
        spicyGrilledChickenTacos.setDifficulty(Difficulty.MODERATE);
        spicyGrilledChickenTacos.setServings(6);
        spicyGrilledChickenTacos.setDirections(
                "1. Prepare a gas or charcoal grill for medium-high, direct heat\n"+
                "2. Make the marinade and coat the chicken\n"+
                "3. Grill the chicken\n"+
                "4. Warm the tortillas\n"+
                "5. Assemble the tacos\n"
        );

        Notes spicyGrilledChickenTacosNotes = new Notes();
        spicyGrilledChickenTacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        spicyGrilledChickenTacosNotes.setRecipe(spicyGrilledChickenTacos);

        spicyGrilledChickenTacos.setNotes(spicyGrilledChickenTacosNotes);

        spicyGrilledChickenTacos.addIngredient(new Ingredient("ancho chili powder", BigDecimal.valueOf(2), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("dried oregano", BigDecimal.valueOf(1), teaUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("dried cumin", BigDecimal.valueOf(1), teaUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.5), teaUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("clove garlic", BigDecimal.valueOf(0.5), eachUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("grated orange zest", BigDecimal.valueOf(1), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("fresh-squeezed orange juice", BigDecimal.valueOf(3), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("olive oil", BigDecimal.valueOf(2), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("boneless chicken thighs", BigDecimal.valueOf(1.25), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("corn tortillas", BigDecimal.valueOf(1.25), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("cornbaby arugula", BigDecimal.valueOf(1.25), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("ripe avocados", BigDecimal.valueOf(1.25), tableSpoonUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("radishes", BigDecimal.valueOf(4), eachUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("cherry tomatoes", BigDecimal.valueOf(0.5), pintUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("onion", BigDecimal.valueOf(0.25), eachUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("cilantro", BigDecimal.valueOf(0.25), eachUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("sour cream", BigDecimal.valueOf(1), cupsUom));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("lime", BigDecimal.valueOf(1), eachUom));

        spicyGrilledChickenTacos.getCategories().add(americanCategory);
        spicyGrilledChickenTacos.getCategories().add(mexicanCategory);

        recipes.add(spicyGrilledChickenTacos);

        System.out.println("Total number of recipes: " + recipeRepository.count());


        return recipes;
    }
}
