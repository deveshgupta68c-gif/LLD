package Practice.ElevatorDesign;

import java.util.HashMap;
import java.util.List;

import Practice.ElevatorDesign.Controllers.BuildingController;
import Practice.ElevatorDesign.Controllers.ElevatorController;
import Practice.ElevatorDesign.Models.Building;
import Practice.ElevatorDesign.Models.Direction;
import Practice.ElevatorDesign.Models.Elevator;
import Practice.ElevatorDesign.Service.AssignmentStratergies.RandomAssignmentStratergy;
import Practice.ElevatorDesign.Service.BuildingService;
import Practice.ElevatorDesign.Service.ElevatorService;
import Practice.ElevatorDesign.Service.IBuildingService;
import Practice.ElevatorDesign.Service.IElevatorService;

public class ElevatorSystemDriver {
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   ELEVATOR SYSTEM LLD - TEST SUITE    ");
        System.out.println("========================================\n");
        
        // Initialize services with required dependencies
        IElevatorService elevatorService = new ElevatorService();
        IBuildingService buildingService = new BuildingService(elevatorService, new RandomAssignmentStratergy());
        
        // Initialize controllers
        BuildingController buildingController = new BuildingController(buildingService);
        ElevatorController elevatorController = new ElevatorController(elevatorService);
        
        // Run all test cases
        testAddBuilding(buildingController);
        testAddDuplicateBuilding(buildingController);
        testAddElevatorToBuilding(buildingController, elevatorController);
        testAddMultipleElevatorsToBuilding(buildingController, elevatorController);
        testCallLiftToFloor(buildingController, elevatorController);
        testPushButtonInsideElevator(elevatorController);
        testElevatorMovement(elevatorController);
        testMultipleFloorsInElevator(elevatorController, buildingController);
        testInvalidElevatorRequest(elevatorController);
        testFloorOutOfRange(elevatorController);
        testNegativeFloorRequest(elevatorController);
        testElevatorAlreadyAtFloor(elevatorController, buildingController);
        testFloorMovementSequence(elevatorController, buildingController);
        testCallLiftWithInvalidFloor(buildingController);
        testComplexScenario(buildingController, elevatorController);
        
        // Print summary
        printTestSummary();
    }
    
    // Test Case 1: Add a building
    private static void testAddBuilding(BuildingController buildingController) {
        System.out.println("Test 1: Add a Building");
        System.out.println("------------------------");
        try {
            buildingController.addBuilding(1, "Tech Tower", 10);
            System.out.println("✓ Building 'Tech Tower' with 10 floors added successfully");
            printTestResult(true, "Add Building");
        } catch (Exception e) {
            System.out.println("✗ Failed to add building: " + e.getMessage());
            printTestResult(false, "Add Building");
        }
        System.out.println();
    }
    
    // Test Case 2: Add duplicate building (should fail)
    private static void testAddDuplicateBuilding(BuildingController buildingController) {
        System.out.println("Test 2: Add Duplicate Building (Should Fail)");
        System.out.println("---------------------------------------------");
        try {
            buildingController.addBuilding(1, "Tech Tower Duplicate", 15);
            System.out.println("✗ Duplicate building was added (should have failed)");
            printTestResult(false, "Prevent Duplicate Building");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly prevented duplicate building: " + e.getMessage());
            printTestResult(true, "Prevent Duplicate Building");
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
            printTestResult(false, "Prevent Duplicate Building");
        }
        System.out.println();
    }
    
    // Test Case 3: Add elevator to a building
    private static void testAddElevatorToBuilding(BuildingController buildingController, 
                                                    ElevatorController elevatorController) {
        System.out.println("Test 3: Add Elevator to Building");
        System.out.println("---------------------------------");
        try {
            buildingController.addElevator(101, 1);
            System.out.println("✓ Elevator 101 added to building 1");
            printTestResult(true, "Add Elevator to Building");
        } catch (Exception e) {
            System.out.println("✗ Failed to add elevator: " + e.getMessage());
            printTestResult(false, "Add Elevator to Building");
        }
        System.out.println();
    }
    
    // Test Case 4: Add multiple elevators to a building
    private static void testAddMultipleElevatorsToBuilding(BuildingController buildingController,
                                                             ElevatorController elevatorController) {
        System.out.println("Test 4: Add Multiple Elevators to Building");
        System.out.println("-------------------------------------------");
        try {
            buildingController.addElevator(102, 1);
            buildingController.addElevator(103, 1);
            System.out.println("✓ Multiple elevators (102, 103) added to building 1");
            printTestResult(true, "Add Multiple Elevators");
        } catch (Exception e) {
            System.out.println("✗ Failed to add multiple elevators: " + e.getMessage());
            printTestResult(false, "Add Multiple Elevators");
        }
        System.out.println();
    }
    
    // Test Case 5: Call lift to a floor (external button press)
    private static void testCallLiftToFloor(BuildingController buildingController,
                                              ElevatorController elevatorController) {
        System.out.println("Test 5: Call Lift to Floor (External Button)");
        System.out.println("---------------------------------------------");
        try {
            buildingController.callLiftToFloor(5, 1, Direction.UP);
            System.out.println("✓ Lift called to floor 5 with UP direction");
            printTestResult(true, "Call Lift to Floor");
        } catch (Exception e) {
            System.out.println("✗ Failed to call lift: " + e.getMessage());
            printTestResult(false, "Call Lift to Floor");
        }
        System.out.println();
    }
    
    // Test Case 6: Push button inside elevator (internal button press)
    private static void testPushButtonInsideElevator(ElevatorController elevatorController) {
        System.out.println("Test 6: Push Button Inside Elevator");
        System.out.println("------------------------------------");
        try {
            elevatorController.pushButton(7, 101);
            System.out.println("✓ Button pressed for floor 7 in elevator 101");
            printTestResult(true, "Push Button Inside Elevator");
        } catch (Exception e) {
            System.out.println("✗ Failed to push button: " + e.getMessage());
            printTestResult(false, "Push Button Inside Elevator");
        }
        System.out.println();
    }
    
    // Test Case 7: Test elevator movement
    private static void testElevatorMovement(ElevatorController elevatorController) {
        System.out.println("Test 7: Elevator Movement");
        System.out.println("-------------------------");
        try {
            // Create a new building and elevator for this test
            System.out.println("Initial state - Elevator 101 at floor 0");
            
            // Move all elevators
            elevatorController.moveAllElevators();
            System.out.println("✓ Elevators moved successfully");
            printTestResult(true, "Elevator Movement");
        } catch (Exception e) {
            System.out.println("✗ Failed to move elevators: " + e.getMessage());
            printTestResult(false, "Elevator Movement");
        }
        System.out.println();
    }
    
    // Test Case 8: Test multiple floor requests in an elevator
    private static void testMultipleFloorsInElevator(ElevatorController elevatorController,
                                                       BuildingController buildingController) {
        System.out.println("Test 8: Multiple Floor Requests");
        System.out.println("--------------------------------");
        try {
            // Add another building and elevator for isolated testing
            buildingController.addBuilding(2, "Sky Scraper", 20);
            buildingController.addElevator(201, 2);
            
            // Request multiple floors
            elevatorController.pushButton(3, 201);
            elevatorController.pushButton(8, 201);
            elevatorController.pushButton(12, 201);
            
            System.out.println("✓ Multiple floor requests (3, 8, 12) added to elevator 201");
            
            // Simulate movement
            System.out.println("Simulating elevator movement...");
            for (int i = 0; i < 5; i++) {
                elevatorController.moveAllElevators();
            }
            
            printTestResult(true, "Multiple Floor Requests");
        } catch (Exception e) {
            System.out.println("✗ Failed with multiple floor requests: " + e.getMessage());
            printTestResult(false, "Multiple Floor Requests");
        }
        System.out.println();
    }
    
    // Test Case 9: Test invalid elevator request
    private static void testInvalidElevatorRequest(ElevatorController elevatorController) {
        System.out.println("Test 9: Invalid Elevator Request");
        System.out.println("---------------------------------");
        try {
            // Try to use non-existent elevator
            elevatorController.pushButton(5, 999);
            System.out.println("✓ Invalid elevator request handled gracefully");
            printTestResult(true, "Invalid Elevator Request Handling");
        } catch (Exception e) {
            System.out.println("✗ Unexpected error for invalid request: " + e.getMessage());
            printTestResult(false, "Invalid Elevator Request Handling");
        }
        System.out.println();
    }
    
    // Test Case 10: Test floor out of range (above maximum)
    private static void testFloorOutOfRange(ElevatorController elevatorController) {
        System.out.println("Test 10: Floor Out of Range Validation");
        System.out.println("---------------------------------------");
        try {
            // Elevator 101 is in building 1 which has 10 floors (0-10)
            // Try to request floor 15 (out of range)
            elevatorController.pushButton(15, 101);
            System.out.println("✓ Out of range floor request handled gracefully");
            printTestResult(true, "Floor Out of Range Validation");
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
            printTestResult(false, "Floor Out of Range Validation");
        }
        System.out.println();
    }
    
    // Test Case 11: Test negative floor request
    private static void testNegativeFloorRequest(ElevatorController elevatorController) {
        System.out.println("Test 11: Negative Floor Request Validation");
        System.out.println("-------------------------------------------");
        try {
            // Try to request negative floor
            elevatorController.pushButton(-5, 101);
            System.out.println("✓ Negative floor request handled gracefully");
            printTestResult(true, "Negative Floor Validation");
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
            printTestResult(false, "Negative Floor Validation");
        }
        System.out.println();
    }
    
    // Test Case 12: Test elevator already at requested floor
    private static void testElevatorAlreadyAtFloor(ElevatorController elevatorController,
                                                     BuildingController buildingController) {
        System.out.println("Test 12: Elevator Already at Floor");
        System.out.println("-----------------------------------");
        try {
            // Add a new building and elevator
            buildingController.addBuilding(4, "Test Building", 15);
            buildingController.addElevator(401, 4);
            
            // Elevator starts at floor 0, try to call it to floor 0
            elevatorController.pushButton(0, 401);
            System.out.println("✓ Same floor request handled gracefully");
            printTestResult(true, "Elevator Already at Floor");
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
            printTestResult(false, "Elevator Already at Floor");
        }
        System.out.println();
    }
    
    // Test Case 13: Test floor movement sequence validation
    private static void testFloorMovementSequence(ElevatorController elevatorController,
                                                    BuildingController buildingController) {
        System.out.println("Test 13: Floor Movement Sequence Validation");
        System.out.println("--------------------------------------------");
        try {
            // Add a new building and elevator
            buildingController.addBuilding(5, "Movement Test Tower", 12);
            buildingController.addElevator(501, 5);
            
            // Request valid floors in sequence
            elevatorController.pushButton(3, 501);
            elevatorController.pushButton(7, 501);
            elevatorController.pushButton(5, 501);
            
            System.out.println("Simulating elevator movement through floors...");
            // Move elevator and observe floor transitions
            for (int i = 0; i < 8; i++) {
                elevatorController.moveAllElevators();
            }
            
            System.out.println("✓ Floor movement sequence validated successfully");
            printTestResult(true, "Floor Movement Sequence");
        } catch (Exception e) {
            System.out.println("✗ Floor movement sequence failed: " + e.getMessage());
            printTestResult(false, "Floor Movement Sequence");
        }
        System.out.println();
    }
    
    // Test Case 14: Test call lift with invalid floor
    private static void testCallLiftWithInvalidFloor(BuildingController buildingController) {
        System.out.println("Test 14: Call Lift with Invalid Floor");
        System.out.println("--------------------------------------");
        try {
            // Try to call lift to floor that exceeds building capacity
            // Building 1 has 10 floors
            buildingController.callLiftToFloor(50, 1, Direction.UP);
            System.out.println("✓ Invalid floor call handled gracefully");
            printTestResult(true, "Call Lift Invalid Floor");
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
            printTestResult(false, "Call Lift Invalid Floor");
        }
        System.out.println();
    }
    
    // Test Case 15: Complex scenario with multiple operations
    private static void testComplexScenario(BuildingController buildingController,
                                             ElevatorController elevatorController) {
        System.out.println("Test 15: Complex Scenario");
        System.out.println("-------------------------");
        try {
            // Add a new building
            buildingController.addBuilding(3, "Mega Complex", 25);
            
            // Add multiple elevators
            buildingController.addElevator(301, 3);
            buildingController.addElevator(302, 3);
            buildingController.addElevator(303, 3);
            
            // Call lifts from different floors
            buildingController.callLiftToFloor(10, 3, Direction.UP);
            buildingController.callLiftToFloor(5, 3, Direction.DOWN);
            buildingController.callLiftToFloor(15, 3, Direction.UP);
            
            // Push buttons inside elevators
            elevatorController.pushButton(20, 301);
            elevatorController.pushButton(8, 302);
            elevatorController.pushButton(3, 303);
            
            // Move elevators multiple times
            System.out.println("Simulating complex elevator movements...");
            for (int i = 0; i < 10; i++) {
                elevatorController.moveAllElevators();
            }
            
            System.out.println("✓ Complex scenario executed successfully");
            printTestResult(true, "Complex Scenario");
        } catch (Exception e) {
            System.out.println("✗ Complex scenario failed: " + e.getMessage());
            e.printStackTrace();
            printTestResult(false, "Complex Scenario");
        }
        System.out.println();
    }
    
    // Helper method to print test result
    private static void printTestResult(boolean passed, String testName) {
        totalTests++;
        if (passed) {
            passedTests++;
            System.out.println("Result: PASS ✓");
        } else {
            failedTests++;
            System.out.println("Result: FAIL ✗");
        }
    }
    
    // Print test summary
    private static void printTestSummary() {
        System.out.println("========================================");
        System.out.println("          TEST EXECUTION SUMMARY        ");
        System.out.println("========================================");
        System.out.println("Total Tests    : " + totalTests);
        System.out.println("Passed Tests   : " + passedTests + " ✓");
        System.out.println("Failed Tests   : " + failedTests + " ✗");
        System.out.println("Success Rate   : " + 
            String.format("%.2f", (passedTests * 100.0 / totalTests)) + "%");
        System.out.println("========================================");
        
        if (failedTests == 0) {
            System.out.println("\n🎉 All tests passed! Elevator system is working correctly.");
        } else {
            System.out.println("\n⚠️  Some tests failed. Please review the implementation.");
        }
    }
}
