# Understanding `synchronized(DatabaseConnection.class)` in Java

## 🔹 1. What Is a Lock in Java?

Every object in Java has an **intrinsic lock** (also known as a **monitor lock**).  
When a thread enters a `synchronized` block, it must **acquire the lock** associated with the object used in that block.

- Only **one thread** can hold that lock at a time.
- Other threads attempting to enter must **wait** until the lock is released.

---

## 🔹 2. What Does `synchronized (DatabaseConnection.class)` Do?

`DatabaseConnection.class` refers to the **Class object** representing the `DatabaseConnection` class.

```
synchronized (DatabaseConnection.class) {
    // critical section
}
```

This locks the **Class object**, meaning only one thread in the **entire JVM** can execute this block at a time — regardless of how many instances of `DatabaseConnection` exist.

✅ It’s a **class-level lock**.  
❌ It does *not* lock individual instances.

---

## 🔹 3. Why It’s Used in Singleton

In the **Singleton Design Pattern**, we ensure only **one instance** of a class exists.

Before the instance is created, there’s **no object yet**, so we can’t use `synchronized(this)`.  
That’s why we lock on the **Class object** instead:

```
if (instance == null) {
    synchronized (DatabaseConnection.class) {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
    }
}
```

This ensures:
1. Only one thread can enter the synchronized block at a time.
2. The instance is created only once.

---

## 🔹 4. Class-Level Lock vs Object-Level Lock

| Lock Type | Example | Scope | When Used |
|------------|----------|--------|------------|
| **Object-level lock** | `synchronized (this)` | Per instance | Protects instance variables |
| **Class-level lock** | `synchronized (MyClass.class)` | Per class | Protects static data or singleton instance |

---

### Example

```
public class Example {
    public void instanceLock() {
        synchronized (this) {
            System.out.println("Locked by " + Thread.currentThread().getName());
        }
    }

    public void classLock() {
        synchronized (Example.class) {
            System.out.println("Locked class by " + Thread.currentThread().getName());
        }
    }
}
```

- Two threads using **different instances** can both enter `instanceLock()` → *no blocking*.
- Two threads calling `classLock()` → *one waits for the other* (shared class-level lock).

---

## 🔹 5. Behind the Scenes

When executing a synchronized block:

1. The JVM checks if another thread already holds the **monitor lock** on that object.
2. If yes → current thread **waits**.
3. If no → thread **acquires the lock** and executes the block.
4. Upon exit → the thread **releases the lock**.

The JVM implements this using **monitors** (mapped to OS-level mutexes).

---

## 🔹 6. Typical Use Cases

- Thread-safe Singleton initialization
- Synchronizing access to shared static data
- Global logging or configuration managers
- Preventing race conditions in shared resources

---

## 🔹 7. Comparison Table

| Synchronization Form | Lock Object | Scope | Works For |
|----------------------|-------------|--------|------------|
| `synchronized (this)` | Current instance | Object-level | Only one object |
| `synchronized (ClassName.class)` | Class object | Class-level | All instances |
| `public synchronized void method()` | Locks on `this` | Object-level | Per instance |
| `public static synchronized void method()` | Locks on `Class` | Class-level | All instances |

---

## 🔹 8. Analogy

- `synchronized (this)` → Locking **one room**; other rooms (instances) are free.
- `synchronized (ClassName.class)` → Locking the **entire building**; no one can enter any room until it’s unlocked.

---

## ✅ Summary

| Concept | Description |
|----------|--------------|
| What it locks | The `Class` object representing that class |
| Lock type | Class-level |
| Affects | All instances of the class |
| Prevents | Multiple threads executing critical code simultaneously |
| Common use | Thread-safe Singleton initialization or static resource protection |

---

## 🧠 Key Takeaway

> `synchronized (DatabaseConnection.class)` enforces **class-level synchronization**, ensuring that only one thread — across **all instances** of that class — can enter the critical section at a time.
