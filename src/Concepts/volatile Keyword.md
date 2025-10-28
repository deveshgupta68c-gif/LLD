# 🔹 volatile Keyword in Java (Singleton Context)

## 1. Purpose

The `volatile` keyword in Java ensures **visibility** and **ordering** of variables across threads.

When a variable is declared as `volatile`, it means:

- Its value is **always read from main memory**, not from a thread's local cache.
- **Instruction reordering** related to this variable is **prevented**, ensuring consistent visibility of object initialization across threads.

---

## 2. Example (Thread-Safe Singleton)

    public class DatabaseConnection {

        // volatile prevents reordering and ensures visibility
        private static volatile DatabaseConnection instance;

        private DatabaseConnection() {
            System.out.println("Database connection established...");
        }

        public static DatabaseConnection getInstance() {
            if (instance == null) {  // First check (no lock)
                synchronized (DatabaseConnection.class) {
                    if (instance == null) {  // Second check (with lock)
                        instance = new DatabaseConnection();  // Object creation
                    }
                }
            }
            return instance;
        }
    }

---

## 3. Why volatile is Needed

### Object creation steps internally:
1. Allocate memory for the object
2. Initialize the object (constructor runs)
3. Assign the reference to the variable (`instance`)

### Problem (without volatile)
Due to **instruction reordering**, these steps may happen like:  
1 → 3 → 2  
So another thread may see a **non-null instance** that is **not fully constructed** yet.

### Solution (with volatile)
The `volatile` keyword ensures that:
- Steps **1 → 2 → 3** always happen in the correct order.
- Once a thread assigns the reference to `instance`, it is **fully initialized** and **visible** to all other threads.

---

## 4. Summary Table

| Aspect | Without volatile | With volatile |
|--------|------------------|----------------|
| Visibility | Threads may see stale values | Always up-to-date |
| Instruction reordering | Possible | Prevented |
| Thread safety | Not guaranteed | Guaranteed |
| Safe publication | ❌ | ✅ |

---

## 5. Key Takeaway

> Use `volatile` with Singleton instances (in double-checked locking) to ensure **thread-safe**, **visible**, and **fully initialized** object references.
