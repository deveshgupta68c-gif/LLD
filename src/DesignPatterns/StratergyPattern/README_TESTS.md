# Strategy Pattern Test Suite

## Test Class
`PaymentStrategyTest.java` - Comprehensive test suite for the Strategy Pattern implementation

## Running the Tests

### Option 1: Using IntelliJ IDEA (Recommended)
1. Open the file `PaymentStrategyTest.java` in IntelliJ IDEA
2. Right-click anywhere in the file
3. Select "Run 'PaymentStrategyTest.main()'"

OR

1. Click the green play button (▶) next to the `main` method (line 9)
2. Select "Run 'PaymentStrategyTest.main()'"

### Option 2: Using Command Line
Since the project uses Lombok, you need to include the Lombok JAR during compilation.

**For Windows PowerShell:**
```powershell
# Navigate to project root
cd C:\Users\deves\IdeaProjects\LLD

# Compile (requires Lombok JAR path)
javac -cp "$env:USERPROFILE\.m2\repository\org\projectlombok\lombok\1.18.38\lombok-1.18.38.jar" -d out -sourcepath src src\DesignPatterns\StratergyPattern\*.java src\DesignPatterns\StratergyPattern\DTO\*.java src\DesignPatterns\StratergyPattern\StratergyImplementation\*.java

# Run
java -cp out DesignPatterns.StratergyPattern.PaymentStrategyTest
```

## Test Coverage

The test suite includes **10 comprehensive test cases**:

### Credit Card Payment Tests (7 tests)
1. ✓ **Valid Credit Card Payment** - Tests successful payment with all valid details
2. ✓ **Invalid Card Number** - Validates rejection of empty/null card numbers
3. ✓ **Invalid Payment Mode** - Ensures only credit_card mode is accepted
4. ✓ **Invalid Expiry Date** - Validates expiry date presence
5. ✓ **Invalid CVV** - Checks CVV validation
6. ✓ **Invalid Amount** - Tests negative/zero amount rejection
7. ✓ **Null Payment Details** - Validates null payment object handling

### Cash Payment Tests (1 test)
8. ✓ **Valid Cash Payment** - Tests successful cash payment

### Strategy Pattern Core Tests (2 tests)
9. ✓ **Payment Without Strategy** - Validates behavior when no strategy is set
10. ✓ **Switch Strategy at Runtime** - Tests dynamic strategy switching (Credit Card → Cash)

## Expected Output

The test suite will output:
- Individual test results with ✓ (PASSED) or ✗ (FAILED)
- Detailed execution logs for each test
- Final summary showing:
  - Total tests run
  - Passed/Failed count
  - Success rate percentage

## Sample Output
```
========================================
STRATEGY PATTERN TEST SUITE
========================================

Test 1: Credit Card Payment - Valid Case
------------------------------------------
setting payment type
Validating Payment Details
Completed Txn with txn ID : <UUID>
✓ Test 1 PASSED: Credit Card Payment - Valid Case

...

========================================
TEST SUMMARY
========================================
Total Tests: 10
Passed: 10
Failed: 0
Success Rate: 100.00%
========================================
```

## Notes
- All tests use PSVM (public static void main) pattern as requested
- Tests validate both positive and negative scenarios
- Each test is self-contained and independent
- Console output includes detailed execution traces
