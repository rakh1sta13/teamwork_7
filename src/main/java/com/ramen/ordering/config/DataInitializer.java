package com.ramen.ordering.config;

import com.ramen.ordering.dto.BranchDTO;
import com.ramen.ordering.dto.CategoryDTO;
import com.ramen.ordering.dto.ProductDTO;
import com.ramen.ordering.entity.enums.UserRole;
import com.ramen.ordering.service.BranchService;
import com.ramen.ordering.service.CategoryService;
import com.ramen.ordering.service.ProductService;
import com.ramen.ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final BranchService branchService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        // Create initial superadmin user
        if (!userService.existsByUsername("superadmin")) {
            userService.createUser("superadmin", "admin123", UserRole.SUPERADMIN);
            log.info("Superadmin user created: username='superadmin', password='admin123'");
        } else {
            log.info("Superadmin user already exists");
        }

        // Create initial admin user
        if (!userService.existsByUsername("admin")) {
            userService.createUser("admin", "admin123", UserRole.ADMIN);
            log.info("Admin user created: username='admin', password='admin123'");
        } else {
            log.info("Admin user already exists");
        }

        log.info("User initialization completed");

        // Initialize branches
        initializeBranches();

        // Initialize categories
        initializeCategories();

        // Initialize products
        initializeProducts();
    }

    private void initializeBranches() {
        List<BranchDTO> existingBranches = branchService.getAllBranches();
        if (!existingBranches.isEmpty()) {
            log.info("Branches already exist, skipping initialization");
            return;
        }

        List<String> branchNames = List.of(
            "Ramen Central Tashkent",
            "Ramen Garden Chilonzor",
            "Ramen Corner Yunusabad",
            "Ramen Delight Sergeli"
        );

        List<String> addresses = List.of(
            "Mashinasozlar street 15, Tashkent, Uzbekistan",
            "Chilonzor district, 8A, Tashkent, Uzbekistan",
            "Yunusabad district, 2B, Tashkent, Uzbekistan",
            "Sergeli district, 56, Tashkent, Uzbekistan"
        );

        List<String> phones = List.of(
            "+998 78 123 45 67",
            "+998 78 234 56 78",
            "+998 78 345 67 89",
            "+998 78 456 78 90"
        );

        List<String> workingHours = List.of(
            "09:00 - 23:00",
            "10:00 - 22:00",
            "08:00 - 24:00",
            "11:00 - 23:00"
        );

        List<Double[]> coordinates = List.of(
            new Double[]{41.3111, 69.2406}, // Tashkent
            new Double[]{41.3276, 69.2321}, // Chilonzor
            new Double[]{41.3567, 69.2789}, // Yunusabad
            new Double[]{41.2987, 69.3456}  // Sergeli
        );

        for (int i = 0; i < branchNames.size(); i++) {
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setName(branchNames.get(i));
            branchDTO.setAddress(addresses.get(i));
            branchDTO.setPhone(phones.get(i));
            branchDTO.setWorkingHours(workingHours.get(i));
            branchDTO.setLatitude(coordinates.get(i)[0]);
            branchDTO.setLongitude(coordinates.get(i)[1]);
            branchDTO.setIsActive(true);

            BranchDTO createdBranch = branchService.createBranch(branchDTO);
            log.info("Created branch: {}", createdBranch.getName());
        }
    }

    private void initializeCategories() {
        List<CategoryDTO> existingCategories = categoryService.getAllCategories();
        if (!existingCategories.isEmpty()) {
            log.info("Categories already exist, skipping initialization");
            return;
        }

        List<String> categoryNames = List.of(
            "Classic Ramen",
            "Spicy Ramen",
            "Vegetarian Ramen",
            "Seafood Ramen",
            "Specialty Bowls",
            "Appetizers",
            "Desserts",
            "Beverages"
        );

        List<String> descriptions = List.of(
            "Traditional ramen with classic broth and toppings",
            "Hot and spicy ramen varieties for spice lovers",
            "Plant-based ramen options without animal products",
            "Fresh seafood-based ramen with fish and shellfish",
            "Chef's special signature bowls with unique ingredients",
            "Delicious starters to complement your meal",
            "Sweet treats to end your ramen experience",
            "Refreshing drinks to pair with your meal"
        );

        for (int i = 0; i < categoryNames.size(); i++) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(categoryNames.get(i));
            categoryDTO.setDescription(descriptions.get(i));

            CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
            log.info("Created category: {}", createdCategory.getName());
        }
    }

    private void initializeProducts() {
        List<ProductDTO> existingProducts = productService.getAllProducts();
        if (!existingProducts.isEmpty()) {
            log.info("Products already exist, skipping initialization");
            return;
        }

        // Get all branches and categories
        List<BranchDTO> branches = branchService.getAllBranches();
        List<CategoryDTO> categories = categoryService.getAllCategories();

        if (branches.isEmpty() || categories.isEmpty()) {
            log.warn("Branches or categories not found, cannot initialize products");
            return;
        }

        // Create a mapping of category names to IDs for easier reference
        Map<String, Long> categoryNameToId = new HashMap<>();
        for (CategoryDTO category : categories) {
            categoryNameToId.put(category.getName(), category.getId());
        }

        // Create 80+ products data
        List<Map<String, Object>> productData = new ArrayList<>();

        // Classic Ramen products
        String[] classicRamen = {"Shoyu Ramen", "Miso Ramen", "Shio Ramen", "Tonkotsu Ramen", "Tsukemen",
                                "Hakodate Ramen", "Sapporo Ramen", "Kitakata Ramen", "Nagi Ramen", "Ichiran Ramen",
                                "Hakata Ramen", "Kyushu Ramen", "Yokohama Ramen", "Wonton Men", "Taiwan Ramen",
                                "Himokawa Ramen", "Tokyo Shoyu", "Asahikawa Miso", "Nagahama Ramen", "Chiba Shio"};
        String[] classicDescriptions = {
            "Soy sauce-based ramen with chashu pork and soft-boiled egg",
            "Fermented soybean paste ramen with corn and butter",
            "Salt-based clear broth ramen with vegetables",
            "Pork bone broth ramen with thin noodles",
            "Dipping ramen with thick noodles and rich broth",
            "Northern style with miso and seafood broth",
            "Miso-based with seafood and vegetables",
            "Thick noodles in soy sauce broth",
            "Rich tonkotsu with chashu and nori",
            "Tonkotsu with special red miso blend",
            "Authentic Hakata-style with extra noodles",
            "Kyushu specialty with rich tonkotsu",
            "Yokohama-style with seafood notes",
            "Ramen with wonton dumplings",
            "Spicy Taiwanese-style ground pork",
            "Himokawa's special miso blend",
            "Tokyo-style light shoyu broth",
            "Asahikawa's thick miso specialty",
            "Nagahama's tonkotsu with extra garlic",
            "Chiba's light salt-based broth"
        };

        // Spicy Ramen products
        String[] spicyRamen = {"Kimchi Ramen", "Spicy Miso Ramen", "Chili Ramen", "Jalapeño Ramen", "Fire Bowl",
                              "Ghost Pepper Ramen", "Szechuan Ramen", "Korean Spicy Ramen", "Spicy Seafood Ramen", "Hot Chicken Ramen",
                              "Devil's Ramen", "Blazing Bowl", "Naga Viper Ramen", "Sriracha Ramen", "Spicy Pork Ramen"};
        String[] spicyDescriptions = {
            "Korean kimchi with vegetables and soft-boiled egg",
            "Spicy fermented miso with vegetables",
            "Chili-based broth with vegetables and meat",
            "Fresh jalapeños with herbs and spices",
            "Fiery hot with special chili oil",
            "Extreme spicy with ghost pepper infusion",
            "Szechuan spicy with numbing peppercorns",
            "Korean gochujang with vegetables",
            "Spicy seafood with shellfish",
            "Hot chicken broth with spices",
            "Extreme spicy with multiple hot peppers",
            "Blazing hot with ghost pepper oil",
            "Naga Viper pepper infused broth",
            "Sriracha-based spicy ramen",
            "Spicy pork-based with extra chili"
        };

        // Vegetarian Ramen products
        String[] vegRamen = {"Veggie Mushroom Ramen", "Tofu Ramen", "Seaweed Bowl", "Truffle Ramen", "Pumpkin Ramen",
                            "Avocado Ramen", "Lentil Ramen", "Spinach Ramen", "Sweet Potato Ramen", "Broccoli Ramen",
                            "Cauliflower Ramen", "Kale Bowl", "Quinoa Ramen", "Tempeh Bowl", "Veggie Fusion"};
        String[] vegDescriptions = {
            "Mushroom-based broth with mixed vegetables",
            "Tofu with vegetables in light broth",
            "Seaweed-based with vegetables and nori",
            "Truffle oil with mushrooms and herbs",
            "Roasted pumpkin with coconut milk",
            "Avocado with vegetables and soft-boiled egg",
            "Lentil-based with vegetables",
            "Spinach with tofu and herbs",
            "Sweet potato with vegetables",
            "Broccoli with mushrooms and tofu",
            "Cauliflower-based with vegetables",
            "Kale and vegetable medley",
            "Quinoa protein with vegetables",
            "Tempeh with Asian vegetables",
            "Veggie fusion with mixed ingredients"
        };

        // Seafood Ramen products
        String[] seafoodRamen = {"Seafood Special", "Shrimp Ramen", "Crab Ramen", "Fish Ramen", "Tuna Ramen",
                                "Prawn Ramen", "Octopus Ramen", "Mussels Ramen", "Clam Ramen", "Mixed Seafood Bowl",
                                "Lobster Ramen", "Squid Ink Ramen", "Abalone Bowl", "Sea Urchin Ramen", "Scallop Delight",
                                "Sea Bass Ramen", "Mackerel Bowl", "Sardine Special", "Shrimp Gyoza Bowl", "Sea Vegetable Ramen"};
        String[] seafoodDescriptions = {
            "Mixed seafood with shellfish and fish",
            "Fresh shrimp with vegetables",
            "Crab meat with vegetables",
            "Fish-based broth with vegetables",
            "Tuna-based with vegetables",
            "Prawn stock with vegetables",
            "Octopus with vegetables and nori",
            "Mussels in rich broth",
            "Clam-based with vegetables",
            "Variety of seafood in rich broth",
            "Lobster-based rich seafood ramen",
            "Squid ink with seafood",
            "Abalone with vegetables",
            "Sea urchin with special preparation",
            "Scallops with light broth",
            "Sea bass with vegetables",
            "Mackerel-based with herbs",
            "Sardine-based with vegetables",
            "Shrimp gyoza with ramen",
            "Sea vegetables with light broth"
        };

        // Specialty Bowls
        String[] specialBowls = {"Chef's Special", "Ramen Fusion", "Signature Bowl", "Premium Ramen", "Gourmet Bowl",
                                "Artisan Ramen", "Executive Bowl", "Master Chef's Bowl", "Luxury Ramen", "Exclusive Bowl",
                                "Imperial Special", "Emperor's Bowl", "Royal Ramen", "Celestial Bowl", "Ambassador's Ramen"};
        String[] specialDescriptions = {
            "Chef's signature combination with premium ingredients",
            "Fusion of Japanese and local flavors",
            "Special ingredients and unique preparation",
            "Premium ingredients with special broth",
            "Gourmet style with special toppings",
            "Artisanal preparation with craft ingredients",
            "Executive style with premium toppings",
            "Master chef's personal creation",
            "Luxury ingredients and exclusive preparation",
            "Exclusive preparation with rare ingredients",
            "Imperial special with premium ingredients",
            "Emperor's choice with unique toppings",
            "Royal treatment with luxury ingredients",
            "Celestial flavors with special spices",
            "Ambassador's choice with exclusive ingredients"
        };

        // Appetizers products
        String[] appetizers = {"Gyoza", "Chicken Karaage", "Edamame", "Pork Buns", "Vegetable Tempura",
                              "Beef Tataki", "Seaweed Salad", "Rice Balls", "Onigiri", "Chicken Wings",
                              "Prawn Crackers", "Seaweed Wraps", "Takoyaki", "Dumplings", "Spring Rolls"};
        String[] appetizerDescriptions = {
            "Pan-fried pork dumplings with dipping sauce",
            "Japanese-style fried chicken pieces",
            "Steamed soybeans with sea salt",
            "Steamed pork buns with hoisin sauce",
            "Assorted vegetable tempura with tentsuyu",
            "Seared beef with ponzu sauce",
            "Fresh seaweed salad with sesame oil",
            "Triangular rice balls with various fillings",
            "Japanese rice balls wrapped in nori",
            "Spicy Japanese chicken wings",
            "Crispy prawn crackers with cocktail sauce",
            "Seaweed wrapped vegetables",
            "Octopus balls with special sauce",
            "Assorted steamed dumplings",
            "Crispy vegetable spring rolls"
        };

        // Desserts products
        String[] desserts = {"Mochi Ice Cream", "Dorayaki", "Taiyaki", "Anmitsu", "Mizu Yokan",
                            "Warabimochi", "Zenzai", "Imagawayaki", "Dango", "Kakigori",
                            "Castella", "Souffle Pancakes", "Matcha Parfait", "Rice Pudding", "Fruit Daifuku"};
        String[] dessertDescriptions = {
            "Sweet rice mochi with ice cream center",
            "Sweet pancakes filled with red bean paste",
            "Fish-shaped cake filled with sweet bean paste",
            "Traditional dessert with sweet beans and fruits",
            "Jelly-like dessert made from agar and sweet beans",
            "Sweet rice cake with kinako powder",
            "Sweet red bean soup with mochi",
            "Sweet buns cooked in a special pan",
            "Sweet rice dumplings on skewers",
            "Japanese shaved ice with sweet syrups",
            "Japanese sponge cake with honey",
            "Fluffy souffle pancakes with syrup",
            "Matcha ice cream layered with red bean",
            "Japanese-style rice pudding",
            "Mochi filled with seasonal fruits"
        };

        // Beverages products
        String[] beverages = {"Green Tea", "Matcha", "Barley Tea", "Genmaicha", "Sencha",
                             "Coffee", "Sake", "Japanese Whiskey", "Soda", "Fruit Juice",
                             "Beer", "Ramen Beer", "Calpis", "Ramune", "Japanese Cola"};
        String[] beverageDescriptions = {
            "Premium Japanese green tea",
            "Powdered green tea with ceremonial preparation",
            "Roasted barley tea, caffeine-free",
            "Green tea with roasted brown rice",
            "High-grade Japanese green tea",
            "Freshly brewed coffee",
            "Premium Japanese rice wine",
            "Aged Japanese whiskey",
            "Assorted Japanese sodas",
            "Fresh seasonal fruit juices",
            "Domestic Japanese beer",
            "Specialty ramen-flavored beer",
            "Milky soft drink from Japan",
            "Carbonated ball candy drink",
            "Traditional Japanese cola"
        };

        // Create products for each category distributed across branches
        // Classic Ramen - 20 products
        for (int i = 0; i < 20; i++) {
            int branchIndex = i % branches.size(); // Distribute across branches
            Map<String, Object> product = new HashMap<>();
            product.put("name", classicRamen[i]);
            product.put("description", classicDescriptions[i]);
            product.put("price", new BigDecimal(25.0 + (i % 5)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Classic Ramen"));
            product.put("imageUrl", "/images/classic_ramen_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Spicy Ramen - 15 products
        for (int i = 0; i < 15; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", spicyRamen[i]);
            product.put("description", spicyDescriptions[i]);
            product.put("price", new BigDecimal(27.0 + (i % 5)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Spicy Ramen"));
            product.put("imageUrl", "/images/spicy_ramen_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Vegetarian Ramen - 15 products
        for (int i = 0; i < 15; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", vegRamen[i]);
            product.put("description", vegDescriptions[i]);
            product.put("price", new BigDecimal(23.0 + (i % 5)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Vegetarian Ramen"));
            product.put("imageUrl", "/images/veg_ramen_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Seafood Ramen - 20 products
        for (int i = 0; i < 20; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", seafoodRamen[i]);
            product.put("description", seafoodDescriptions[i]);
            product.put("price", new BigDecimal(30.0 + (i % 5)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Seafood Ramen"));
            product.put("imageUrl", "/images/seafood_ramen_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Specialty Bowls - 15 products
        for (int i = 0; i < 15; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", specialBowls[i]);
            product.put("description", specialDescriptions[i]);
            product.put("price", new BigDecimal(35.0 + (i % 5)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Specialty Bowls"));
            product.put("imageUrl", "/images/specialty_bowl_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Appetizers - 15 products
        for (int i = 0; i < 15; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", appetizers[i]);
            product.put("description", appetizerDescriptions[i]);
            product.put("price", new BigDecimal(12.0 + (i % 3)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Appetizers"));
            product.put("imageUrl", "/images/appetizer_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Desserts - 15 products
        for (int i = 0; i < 15; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", desserts[i]);
            product.put("description", dessertDescriptions[i]);
            product.put("price", new BigDecimal(8.0 + (i % 3)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Desserts"));
            product.put("imageUrl", "/images/dessert_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Beverages - 15 products
        for (int i = 0; i < 15; i++) {
            int branchIndex = i % branches.size();
            Map<String, Object> product = new HashMap<>();
            product.put("name", beverages[i]);
            product.put("description", beverageDescriptions[i]);
            product.put("price", new BigDecimal(5.0 + (i % 3)));
            product.put("branchId", branches.get(branchIndex).getId());
            product.put("categoryId", categoryNameToId.get("Beverages"));
            product.put("imageUrl", "/images/beverage_" + (i+1) + ".jpg");
            productData.add(product);
        }

        // Create the products
        for (Map<String, Object> productInfo : productData) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName((String) productInfo.get("name"));
            productDTO.setDescription((String) productInfo.get("description"));
            productDTO.setPrice((BigDecimal) productInfo.get("price"));
            productDTO.setBranchId((Long) productInfo.get("branchId"));
            productDTO.setCategoryId((Long) productInfo.get("categoryId"));
            productDTO.setImageUrl((String) productInfo.get("imageUrl"));
            productDTO.setIsAvailable(true);

            ProductDTO createdProduct = productService.createProduct(productDTO);
            log.info("Created product: {} in branch: {}", createdProduct.getName(),
                    branches.stream().filter(b -> b.getId().equals(createdProduct.getBranchId()))
                           .findFirst().map(BranchDTO::getName).orElse("Unknown"));
        }

        log.info("Total products created: {}", productData.size());
    }
}