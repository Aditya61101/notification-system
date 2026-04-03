# Notification System Design

## 1. Problem Statement

Design a scalable notification system that supports multiple delivery channels (Email, SMS, Push), processes notifications asynchronously, handles failures using retries with exponential backoff, respects user preferences, and prevents overload using rate limiting.

---

## 2. High-Level Flow

Event → Notification → Queue → Worker → Processing Pipeline → Send
↓
Retry / Dead Letter Queue (DLQ)

---

## 3. Core Components

### Notification
Represents a unit of work.

**Fields:**
- id
- userId
- message
- channel
- type
- retryCount
- nextRetryTime

---

### NotificationQueue
- Stores notifications for processing
- Uses `DelayQueue` for scheduling retries

---

### NotificationWorker
- Continuously consumes notifications
- Executes pipeline
- Handles retry and DLQ logic

---

### DeadLetterQueue (DLQ)
- Stores permanently failed notifications
- Prevents data loss
- Enables debugging and reprocessing

---

### Processing Pipeline (Chain of Responsibility)

Steps:
1. ValidationHandler
2. PreferenceHandler
3. RateLimitHandler
4. SendHandler

Each handler:
- performs one responsibility
- either forwards or stops processing

---

### PreferenceService
- Stores user preferences
- Decides whether notification should be sent

---

### RateLimiterService
- Maintains per-user rate limiters
- Uses `ConcurrentHashMap`
- Ensures isolation between users

---

### RateLimiter (Strategy Pattern)

Interface for rate limiting logic.

**Implementations:**
- TokenBucketRateLimiter
- FixedWindowRateLimiter

---

### RateLimiterFactory
- Creates rate limiter instances
- Decouples creation logic

---

## 4. Design Patterns Used

### Strategy Pattern
Used for:
- Notification sending
- Rate limiting

**Why:**
Allows interchangeable behavior without modifying existing code.

---

### Factory Pattern
Used for:
- Creating rate limiter strategies

**Why:**
Encapsulates object creation and reduces coupling.

---

### Chain of Responsibility
Used for:
- Processing pipeline

**Why:**
Breaks logic into independent steps and avoids God classes.

---

## 5. Failure Handling

### Retry Mechanism
- Each notification has `retryCount`
- Retries up to `MAX_RETRIES`

---

### Exponential Backoff
- Delay increases with each retry:
delay = 2^retryCount * base_time
- Prevents aggressive retries
- Improves system stability

---

### Dead Letter Queue (DLQ)
- Stores notifications after max retries
- Enables debugging and recovery

---

## 6. Concurrency Model

### Worker Threads
- Background threads process notifications

---

### DelayQueue
- Delays retrieval instead of insertion
- Enables time-based scheduling

---

### ConcurrentHashMap
- Used for per-user rate limiters
- Ensures thread safety

---

### computeIfAbsent
- Ensures atomic creation of limiters
- Prevents race conditions

---

## 7. Rate Limiting

- Implemented per user
- Prevents resource exhaustion
- Default: Token Bucket algorithm

---

## 8. Tradeoffs

- DelayQueue is unbounded → potential memory growth
- In-memory storage → no persistence
- Single-node system → not distributed
- No distributed rate limiting

---

## 9. Future Improvements

- Persistence (DB / Kafka)
- Distributed queue system
- Distributed rate limiting (Redis)
- Priority-based notifications
- Batching
- Idempotency

---

## 10. Evolution of System

### v1: Naive Implementation
- Direct synchronous sending
- Tight coupling

---

### v2: Strategy + Factory
- Decoupled sending logic
- Improved extensibility

---

### v3: Async Processing
- Queue + worker threads
- Non-blocking system

---

### v4: Retry + Backoff
- Reliable processing
- Controlled retries

---

### v5: Processing Pipeline
- Modular architecture
- Clean separation of concerns

---

### v6: Rate Limiting
- Controlled throughput
- Fairness across users

---

## 11. Key Takeaways

- Separation of concerns improves scalability
- Async systems require careful failure handling
- Retry without backoff is dangerous
- Rate limiting must match system boundaries
- Good design evolves through iteration, not upfront perfection