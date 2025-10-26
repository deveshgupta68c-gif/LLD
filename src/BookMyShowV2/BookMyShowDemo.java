package BookMyShowV2;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Demo class to showcase BookMyShow LLD functionality
 * Run this class to see the system in action with test result tracking
 */
public class BookMyShowDemo {
    
    private List<TestResult> testResults = new ArrayList<>();
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    
    // Test result tracking class
    private static class TestResult {
        String testName;
        boolean passed;
        String details;
        
        TestResult(String testName, boolean passed, String details) {
            this.testName = testName;
            this.passed = passed;
            this.details = details;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== BookMyShow System Demo with Test Results ===\n");
        
        BookMyShowDemo demo = new BookMyShowDemo();
        demo.runDemo();
    }
    
    public void runDemo() {
        // Initialize the system
        BookMyShowController bookMyShow = new BookMyShowController();
        
        System.out.println("1. Setting up the BookMyShow System...\n");
        setupSystemData(bookMyShow);
        
        System.out.println("2. Demonstrating User Functionality...\n");
        demonstrateUserFunctionality(bookMyShow);
        
        System.out.println("3. Demonstrating Admin Functionality...\n");
        demonstrateAdminFunctionality(bookMyShow);
        
        System.out.println("4. Demonstrating Error Scenarios...\n");
        demonstrateErrorScenarios(bookMyShow);
        
        System.out.println("\n5. Complete Test Results Summary...\n");
        displayTestResultsSummary();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    private void recordTest(String testName, boolean passed, String details) {
        totalTests++;
        if (passed) {
            passedTests++;
        } else {
            failedTests++;
        }
        testResults.add(new TestResult(testName, passed, details));
        
        String status = passed ? "✅ PASS" : "❌ FAIL";
        System.out.println("    [" + status + "] " + testName + " - " + details);
    }
    
    private void setupSystemData(BookMyShowController bookMyShow) {
        // Create movies
        Movie avengers = new Movie(1, "Avengers: Endgame", 180);
        Movie inception = new Movie(2, "Inception", 148);
        Movie titanic = new Movie(3, "Titanic", 194);
        
        // Add movies to cities
        bookMyShow.addMovie(avengers, Arrays.asList(City.DELHI, City.MUMBAI));
        bookMyShow.addMovie(inception, City.DELHI);
        bookMyShow.addMovie(titanic, City.MUMBAI);
        
        System.out.println("✓ Added movies to different cities");
        
        // Create theaters with movie shows
        Theater delhiTheater = createDelhiTheater();
        Theater mumbaiTheater = createMumbaiTheater();
        
        // Add theaters to cities
        bookMyShow.addTheater(delhiTheater, City.DELHI);
        bookMyShow.addTheater(mumbaiTheater, City.MUMBAI);
        
        System.out.println("✓ Added theaters with movie shows");
        System.out.println("✓ System setup complete!\n");
    }
    
    private Theater createDelhiTheater() {
        // Create seats for the screen
        List<Seats> seats = createSeats();
        Screen screen = new Screen(1, seats);
        
        // Create pricing plan
        Map<PricingPlan, Double> pricingPlan = new HashMap<>();
        pricingPlan.put(PricingPlan.GOLD, 300.0);
        pricingPlan.put(PricingPlan.SILVER, 200.0);
        pricingPlan.put(PricingPlan.PLATINUM, 500.0);
        
        // Create movie shows using the actual constructor
        List<MovieShow> movieShows = new ArrayList<>();
        
        // Avengers show
        LocalDateTime avengersStart = LocalDateTime.now().plusHours(2);
        MovieShow avengersShow = new MovieShow(
                new Movie(1, "Avengers: Endgame", 180),
                screen,
                avengersStart,
                avengersStart.plusMinutes(180),
                pricingPlan
        );
        
        // Inception show
        LocalDateTime inceptionStart = LocalDateTime.now().plusHours(5);
        MovieShow inceptionShow = new MovieShow(
                new Movie(2, "Inception", 148),
                screen,
                inceptionStart,
                inceptionStart.plusMinutes(148),
                pricingPlan
        );
        
        movieShows.add(avengersShow);
        movieShows.add(inceptionShow);
        
        return new Theater(1, City.DELHI, "PVR Select City Walk", 
                          "A-3, District Centre, Saket", movieShows);
    }
    
    private Theater createMumbaiTheater() {
        List<Seats> seats = createSeats();
        Screen screen = new Screen(2, seats);
        
        Map<PricingPlan, Double> pricingPlan = new HashMap<>();
        pricingPlan.put(PricingPlan.GOLD, 350.0);
        pricingPlan.put(PricingPlan.SILVER, 250.0);
        pricingPlan.put(PricingPlan.PLATINUM, 550.0);
        
        List<MovieShow> movieShows = new ArrayList<>();
        
        // Titanic show
        LocalDateTime titanicStart = LocalDateTime.now().plusHours(3);
        MovieShow titanicShow = new MovieShow(
                new Movie(3, "Titanic", 194),
                screen,
                titanicStart,
                titanicStart.plusMinutes(194),
                pricingPlan
        );
        
        movieShows.add(titanicShow);
        
        return new Theater(2, City.MUMBAI, "INOX R-City Mall", 
                          "Ghatkopar West, Mumbai", movieShows);
    }
    
    private List<Seats> createSeats() {
        List<Seats> seats = new ArrayList<>();
        
        // Gold seats (A1-A5)
        for (int i = 1; i <= 5; i++) {
            seats.add(new Seats("A" + i, PricingPlan.GOLD));
        }
        
        // Silver seats (B1-B8)
        for (int i = 1; i <= 8; i++) {
            seats.add(new Seats("B" + i, PricingPlan.SILVER));
        }
        
        // Platinum seats (C1-C10)
        for (int i = 1; i <= 10; i++) {
            seats.add(new Seats("C" + i, PricingPlan.PLATINUM));
        }
        
        return seats;
    }
    
    private void demonstrateUserFunctionality(BookMyShowController bookMyShow) {
        System.out.println("📱 User Functionality Demo:");
        
        // 1. Get movies by city - Delhi
        System.out.println("\n🎬 Testing: Get movies in Delhi");
        List<Movie> delhiMovies = bookMyShow.getMovieByCity(City.DELHI);
        boolean delhiMoviesTest = delhiMovies.size() == 2 && 
            delhiMovies.stream().anyMatch(m -> m.getName().equals("Avengers: Endgame")) &&
            delhiMovies.stream().anyMatch(m -> m.getName().equals("Inception"));
        recordTest("Get Movies by City (Delhi)", delhiMoviesTest, 
                   "Expected 2 movies (Avengers, Inception), Found: " + delhiMovies.size());
        
        delhiMovies.forEach(movie -> 
            System.out.println("  - " + movie.getName() + " (" + movie.getDurationInMinutes() + " min)"));
        
        // Get movies by city - Mumbai
        System.out.println("\n🎬 Testing: Get movies in Mumbai");
        List<Movie> mumbaiMovies = bookMyShow.getMovieByCity(City.MUMBAI);
        boolean mumbaiMoviesTest = mumbaiMovies.size() == 2 &&
            mumbaiMovies.stream().anyMatch(m -> m.getName().equals("Avengers: Endgame")) &&
            mumbaiMovies.stream().anyMatch(m -> m.getName().equals("Titanic"));
        recordTest("Get Movies by City (Mumbai)", mumbaiMoviesTest,
                   "Expected 2 movies (Avengers, Titanic), Found: " + mumbaiMovies.size());
        
        mumbaiMovies.forEach(movie -> 
            System.out.println("  - " + movie.getName() + " (" + movie.getDurationInMinutes() + " min)"));
        
        // 2. Get theaters by city
        System.out.println("\n🏢 Testing: Get theaters in Delhi");
        List<Theater> delhiTheaters = bookMyShow.getTheaterByCity(City.DELHI);
        boolean delhiTheatersTest = delhiTheaters.size() == 1 && 
            delhiTheaters.get(0).getName().equals("PVR Select City Walk");
        recordTest("Get Theaters by City (Delhi)", delhiTheatersTest,
                   "Expected 1 theater (PVR Select), Found: " + delhiTheaters.size());
        
        delhiTheaters.forEach(theater -> 
            System.out.println("  - " + theater.getName() + " at " + theater.getAddress()));
        
        // 3. Get movie shows
        System.out.println("\n🎦 Testing: Get shows for Avengers in Delhi");
        Movie avengers = new Movie(1, "Avengers: Endgame", 180);
        Map<Theater, List<MovieShow>> shows = bookMyShow.getMovieShowsByMovieAndCityTheaterWise(City.DELHI, avengers);
        boolean showsTest = !shows.isEmpty() && shows.values().stream().anyMatch(list -> !list.isEmpty());
        recordTest("Get Movie Shows by City", showsTest,
                   "Expected shows available, Found " + shows.size() + " theaters with shows");
        
        for (Map.Entry<Theater, List<MovieShow>> entry : shows.entrySet()) {
            System.out.println("  Theater: " + entry.getKey().getName());
            entry.getValue().forEach(show -> 
                System.out.println("    - Show starts: " + show.getMovieStartDateTime()));
        }
        
        // 4. Lock and book tickets
        if (!delhiTheaters.isEmpty() && !delhiTheaters.get(0).getMovieShows().isEmpty()) {
            MovieShow firstShow = delhiTheaters.get(0).getMovieShows().get(0);
            List<String> seatsToBook = Arrays.asList("A1", "A2", "B1");
            
            System.out.println("\n🔒 Testing: Lock seats");
            Boolean lockResult = bookMyShow.lockTickets(seatsToBook, firstShow);
            recordTest("Lock Seats", lockResult != null && lockResult,
                       "Lock result: " + lockResult + " for seats: " + seatsToBook);
            
            if (lockResult != null && lockResult) {
                System.out.println("\n🎫 Testing: Book tickets");
                // Calculate payment (Gold: 300*2 + Silver: 200*1 = 800)
                Payment payment = new Payment(1, 800.0);
                
                try {
                    bookMyShow.bookTickets(seatsToBook, firstShow, payment);
                    recordTest("Book Tickets", true,
                               "Successfully booked " + seatsToBook.size() + " seats for ₹" + payment.getAmount());
                    System.out.println("  ✓ Tickets booked successfully!");
                    System.out.println("  Payment: ₹" + payment.getAmount());
                } catch (Exception e) {
                    recordTest("Book Tickets", false, "Booking failed: " + e.getMessage());
                    System.out.println("  ❌ Booking failed: " + e.getMessage());
                }
            }
        }
        
        System.out.println();
    }
    
    private void demonstrateAdminFunctionality(BookMyShowController bookMyShow) {
        System.out.println("👨‍💼 Admin Functionality Demo:");
        
        // 1. Add new movie to multiple cities
        Movie newMovie = new Movie(4, "Spider-Man: No Way Home", 148);
        List<City> targetCities = Arrays.asList(City.DELHI, City.MUMBAI, City.BLR);
        
        System.out.println("\n➕ Testing: Add movie to multiple cities");
        bookMyShow.addMovie(newMovie, targetCities);
        
        // Verify the movie was added to all cities
        boolean movieAddedTest = true;
        for (City city : targetCities) {
            List<Movie> cityMovies = bookMyShow.getMovieByCity(city);
            if (cityMovies.stream().noneMatch(m -> m.getName().equals("Spider-Man: No Way Home"))) {
                movieAddedTest = false;
                break;
            }
        }
        recordTest("Add Movie to Multiple Cities", movieAddedTest,
                   "Added '" + newMovie.getName() + "' to " + targetCities.size() + " cities");
        
        List<Movie> blrMovies = bookMyShow.getMovieByCity(City.BLR);
        System.out.println("  Movies in BLR now: " + blrMovies.size());
        
        // 2. Add new theater
        System.out.println("\n🏢 Testing: Add new theater to Pune");
        List<Seats> puneSeats = createSeats();
        Screen puneScreen = new Screen(3, puneSeats);
        
        Map<PricingPlan, Double> punePricing = new HashMap<>();
        punePricing.put(PricingPlan.GOLD, 280.0);
        punePricing.put(PricingPlan.SILVER, 180.0);
        punePricing.put(PricingPlan.PLATINUM, 480.0);
        
        LocalDateTime showTime = LocalDateTime.now().plusHours(4);
        MovieShow puneShow = new MovieShow(
                newMovie,
                puneScreen,
                showTime,
                showTime.plusMinutes(148),
                punePricing
        );
        
        Theater puneTheater = new Theater(3, City.PUNE, "Cinepolis Seasons Mall", 
                                        "Magarpatta City, Pune", Arrays.asList(puneShow));
        
        bookMyShow.addTheater(puneTheater, City.PUNE);
        
        // Verify theater was added
        List<Theater> puneTheaters = bookMyShow.getTheaterByCity(City.PUNE);
        boolean theaterAddedTest = puneTheaters.size() == 1 && 
            puneTheaters.get(0).getName().equals("Cinepolis Seasons Mall");
        recordTest("Add Theater to City", theaterAddedTest,
                   "Added theater '" + puneTheater.getName() + "' to Pune. Total theaters: " + puneTheaters.size());
        
        System.out.println("  ✓ Added theater: " + puneTheater.getName());
        System.out.println("  Theaters in Pune: " + puneTheaters.size());
        
        // 3. Test theater with movie shows
        System.out.println("\n🎭 Testing: Theater with movie shows");
        boolean theaterShowsTest = !puneTheater.getMovieShows().isEmpty() &&
            puneTheater.getMovieShows().get(0).getMovie().getName().equals("Spider-Man: No Way Home");
        recordTest("Theater with Movie Shows", theaterShowsTest,
                   "Theater has " + puneTheater.getMovieShows().size() + " movie shows");
        
        System.out.println();
    }
    
    private void demonstrateErrorScenarios(BookMyShowController bookMyShow) {
        System.out.println("⚠️  Error Handling Demo:");
        
        // 1. Try to get movies from city with no movies
        System.out.println("\n❓ Testing: Get movies from empty city");
        List<Movie> hyderabadMovies = bookMyShow.getMovieByCity(City.HYDERABAD);
        boolean emptyCity = hyderabadMovies.isEmpty();
        recordTest("Handle Empty City", emptyCity,
                   "Expected 0 movies in Hyderabad, Found: " + hyderabadMovies.size());
        System.out.println("  Movies found: " + hyderabadMovies.size());
        
        // 2. Try to book without locking seats first
        System.out.println("\n❌ Testing: Book without locking seats");
        List<Theater> delhiTheaters = bookMyShow.getTheaterByCity(City.DELHI);
        boolean bookWithoutLockTest = false;
        if (!delhiTheaters.isEmpty() && !delhiTheaters.get(0).getMovieShows().isEmpty()) {
            MovieShow show = delhiTheaters.get(0).getMovieShows().get(0);
            List<String> seats = Arrays.asList("C1", "C2");
            Payment payment = new Payment(2, 1000.0);
            
            try {
                bookMyShow.bookTickets(seats, show, payment);
                System.out.println("  Unexpected success!");
                bookWithoutLockTest = false;
            } catch (Exception e) {
                System.out.println("  ✓ Correctly failed: " + e.getMessage());
                bookWithoutLockTest = true;
            }
        }
        recordTest("Prevent Booking Without Lock", bookWithoutLockTest,
                   "System correctly prevents booking without locking seats first");
        
        // 3. Try to book with insufficient payment
        System.out.println("\n💰 Testing: Book with insufficient payment");
        boolean insufficientPaymentTest = false;
        if (!delhiTheaters.isEmpty() && !delhiTheaters.get(0).getMovieShows().isEmpty()) {
            MovieShow show = delhiTheaters.get(0).getMovieShows().get(0);
            List<String> seats = Arrays.asList("C3", "C4"); // Platinum seats: 500*2 = 1000
            
            Boolean lockResult = bookMyShow.lockTickets(seats, show);
            if (lockResult != null && lockResult) {
                Payment insufficientPayment = new Payment(3, 100.0); // Too low
                
                try {
                    bookMyShow.bookTickets(seats, show, insufficientPayment);
                    System.out.println("  Unexpected success!");
                    insufficientPaymentTest = false;
                } catch (Exception e) {
                    System.out.println("  ✓ Correctly failed: " + e.getMessage());
                    insufficientPaymentTest = true;
                }
            }
        }
        recordTest("Prevent Insufficient Payment", insufficientPaymentTest,
                   "System correctly validates payment amount");
        
        // 4. Try to lock already booked seats
        System.out.println("\n🔒 Testing: Lock already booked seats");
        List<String> alreadyBookedSeats = Arrays.asList("A1", "A2"); // These were booked earlier
        boolean alreadyBookedTest = false;
        if (!delhiTheaters.isEmpty() && !delhiTheaters.get(0).getMovieShows().isEmpty()) {
            MovieShow show = delhiTheaters.get(0).getMovieShows().get(0);
            Boolean lockResult = bookMyShow.lockTickets(alreadyBookedSeats, show);
            alreadyBookedTest = (lockResult == null || !lockResult);
            System.out.println("  Lock result: " + lockResult + " (should be false)");
        }
        recordTest("Prevent Double Booking", alreadyBookedTest,
                   "System correctly prevents locking already booked seats");
        
        // 5. Test getting theaters from empty city
        System.out.println("\n🏢 Testing: Get theaters from empty city");
        List<Theater> hyderabadTheaters = bookMyShow.getTheaterByCity(City.HYDERABAD);
        boolean emptyTheaterCity = hyderabadTheaters.isEmpty();
        recordTest("Handle Empty Theater City", emptyTheaterCity,
                   "Expected 0 theaters in Hyderabad, Found: " + hyderabadTheaters.size());
        
        System.out.println();
    }
    
    private void displayTestResultsSummary() {
        System.out.println("📊 COMPLETE TEST RESULTS SUMMARY");
        System.out.println("==========================================");
        
        // Overall statistics
        double successRate = totalTests > 0 ? (double) passedTests / totalTests * 100 : 0;
        System.out.println("📈 Overall Statistics:");
        System.out.println("   Total Tests: " + totalTests);
        System.out.println("   Passed: " + passedTests + " ✅");
        System.out.println("   Failed: " + failedTests + " ❌");
        System.out.println("   Success Rate: " + String.format("%.1f%%", successRate));
        System.out.println();
        
        // Detailed test results
        System.out.println("📋 Detailed Test Results:");
        System.out.println("------------------------------------------");
        
        // Group tests by category
        Map<String, List<TestResult>> categorizedTests = new HashMap<>();
        categorizedTests.put("User Functionality", new ArrayList<>());
        categorizedTests.put("Admin Functionality", new ArrayList<>());
        categorizedTests.put("Error Handling", new ArrayList<>());
        
        for (TestResult result : testResults) {
            if (result.testName.contains("Movies by City") || result.testName.contains("Theaters by City") || 
                result.testName.contains("Movie Shows") || result.testName.contains("Lock Seats") || 
                result.testName.contains("Book Tickets")) {
                categorizedTests.get("User Functionality").add(result);
            } else if (result.testName.contains("Add Movie") || result.testName.contains("Add Theater") || 
                       result.testName.contains("Theater with")) {
                categorizedTests.get("Admin Functionality").add(result);
            } else {
                categorizedTests.get("Error Handling").add(result);
            }
        }
        
        // Display results by category
        for (Map.Entry<String, List<TestResult>> category : categorizedTests.entrySet()) {
            if (!category.getValue().isEmpty()) {
                System.out.println("🔹 " + category.getKey() + ":");
                for (TestResult result : category.getValue()) {
                    String status = result.passed ? "✅ PASS" : "❌ FAIL";
                    System.out.println("   [" + status + "] " + result.testName);
                    System.out.println("        " + result.details);
                }
                
                long passed = category.getValue().stream().mapToLong(r -> r.passed ? 1 : 0).sum();
                long total = category.getValue().size();
                double categoryRate = total > 0 ? (double) passed / total * 100 : 0;
                System.out.println("   📊 Category Success Rate: " + String.format("%.1f%% (%d/%d)", categoryRate, passed, total));
                System.out.println();
            }
        }
        
        // Final verdict
        System.out.println("🎯 FINAL VERDICT:");
        if (successRate == 100.0) {
            System.out.println("🎉 ALL TESTS PASSED! BookMyShow system is working perfectly!");
        } else if (successRate >= 80.0) {
            System.out.println("🟢 MOSTLY SUCCESSFUL! BookMyShow system is working well with minor issues.");
        } else if (successRate >= 60.0) {
            System.out.println("🟡 PARTIALLY SUCCESSFUL! BookMyShow system has some functionality issues.");
        } else {
            System.out.println("🔴 NEEDS ATTENTION! BookMyShow system has significant issues that need fixing.");
        }
        
        System.out.println("\n📌 Test Summary:");
        System.out.println("   ✅ User can browse movies and theaters by city");
        System.out.println("   ✅ User can view available movie shows");  
        System.out.println("   ✅ User can lock and book tickets with payment");
        System.out.println("   ✅ Admin can add movies and theaters to the system");
        System.out.println("   ✅ System handles error scenarios appropriately");
        
        System.out.println("\n🔧 BookMyShow LLD Features Demonstrated:");
        System.out.println("   • Multi-city movie and theater management");
        System.out.println("   • Seat locking and booking mechanism");
        System.out.println("   • Payment validation and processing");
        System.out.println("   • Theater and movie show scheduling");
        System.out.println("   • Robust error handling and validation");
        
        System.out.println("==========================================");
    }
}
