# Retrofit Source Code Learning

> Retrofit - A type-safe HTTP client for Android and Java

## 📊 Overview

| Item | Content |
|------|---------|
| **Official Site** | https://square.github.io/retrofit/ |
| **Source Code** | https://github.com/square/retrofit |
| **Version** | 2.9.0 |
| **Learning Period** | 2025-01-15 ~ 2025-01-20 |
| **Difficulty** | ⭐⭐⭐☆☆ |
| **Lines of Code** | ~3000 (core) |

## 🎯 Learning Objectives

- [x] Understand Retrofit's overall architecture
- [x] Master dynamic proxy implementation
- [x] Understand annotation parsing mechanism
- [x] Learn CallAdapter and Converter extension mechanisms
- [x] Study excellent design pattern applications

## 📚 Directory Structure
```
retrofit/
├── README.md                 # This file
├── demo/                     # Runnable demo project
│   └── (Android project files)
├── notes/                    # Learning notes (in order)
│   ├── 01-overview.md
│   ├── 02-dynamic-proxy.md           ⭐⭐⭐⭐⭐
│   ├── 03-annotation-parsing.md      ⭐⭐⭐⭐
│   ├── 04-call-adapter.md            ⭐⭐⭐
│   ├── 05-converter.md               ⭐⭐⭐
│   ├── 06-thread-model.md            ⭐⭐⭐⭐
│   └── 07-best-practices.md
├── diagrams/                 # Flow & architecture diagrams
│   ├── architecture.png
│   ├── create-flow.png
│   └── request-flow.png
├── code-analysis/            # Annotated source code
│   ├── Retrofit.java.md
│   ├── ServiceMethod.java.md
│   └── HttpServiceMethod.java.md
└── questions.md              # Q&A
```

## 🚀 Quick Start

### 1. Run Demo Project
```bash
cd demo
# Open in Android Studio
```

### 2. Read Notes in Order
```bash
cd notes
# Recommended order: 01 → 02 → 03 → 04 → 05 → 06 → 07
```

### 3. Debug Key Flows

Set breakpoints at:
- `retrofit.create()` - understand proxy creation
- `gitHubService.listRepos()` - understand method interception
- `call.enqueue()` - understand async execution

## 💡 Core Knowledge Points

### 1. Dynamic Proxy Mechanism ⭐⭐⭐⭐⭐

**Core Code**:
```java
public <T> T create(final Class<T> service) {
    return (T) Proxy.newProxyInstance(
        service.getClassLoader(),
        new Class<?>[] {service},
        new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                return loadServiceMethod(method).invoke(args);
            }
        }
    );
}
```

**Key Understanding**:
- Generate interface implementation at runtime
- InvocationHandler intercepts all method calls
- Lazy parsing + caching for performance

**Detailed Analysis**: [02-dynamic-proxy.md](./notes/02-dynamic-proxy.md)

## 🏆 Learning Outcomes

### Design Patterns Learned
- ✅ Dynamic Proxy Pattern
- ✅ Builder Pattern  
- ✅ Factory Pattern
- ✅ Adapter Pattern
- ✅ Singleton Pattern

### Programming Techniques
- ✅ Double-Check Locking (DCL)
- ✅ Generic Design
- ✅ Annotation-Driven
- ✅ Lazy Loading
- ✅ Extension Mechanisms

---

**Start Date**: 2025-01-15  
**Complete Date**: 2025-01-20  
**Total Time**: ~15 hours

**Back to**: [AndroidDeepDive Main Page](../)
