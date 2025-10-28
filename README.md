# AndroidDeepDive 🏊‍♂️

> Deep dive into Android open-source libraries and frameworks - Learning through source code

[![GitHub stars](https://img.shields.io/github/stars/yourusername/AndroidDeepDive?style=social)](https://github.com/yourusername/AndroidDeepDive)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Progress](https://img.shields.io/badge/Progress-10%25-yellow.svg)]()
[![Language](https://img.shields.io/badge/Language-中文/English-green.svg)]()

[English](./README.md) | [中文](./README_CN.md)

## 📖 About This Project

This repository documents my journey of learning Android mainstream open-source libraries and frameworks through source code analysis. Each module includes:

- ✅ Runnable demo projects
- 📝 Detailed learning notes
- 🎨 Clear flow diagrams  
- 💡 Annotated source code
- ❓ Q&A collections

**Learning Approach**: Top-down, starting from usage, reading with questions, hands-on practice, and output-driven learning.

## 🗺️ Learning Roadmap

### Phase 1: Networking Libraries (In Progress)
- [x] [Retrofit](./retrofit) - Type-safe HTTP client ⭐⭐⭐⭐⭐
- [ ] [OkHttp](./okhttp) - High-performance HTTP client
- [ ] [Gson](./gson) - JSON parsing library

### Phase 2: Image Loading
- [ ] [Glide](./glide) - Image loading and caching
- [ ] [Coil](./coil) - Kotlin-first image library

### Phase 3: Reactive Programming  
- [ ] [Kotlin Coroutines](./coroutines) - Coroutines
- [ ] [Flow](./flow) - Reactive streams
- [ ] [RxJava](./rxjava) - Reactive extensions

### Phase 4: Dependency Injection
- [ ] [Hilt](./hilt) - Android DI
- [ ] [Koin](./koin) - Kotlin DI

### Phase 5: Jetpack Components
- [ ] [Lifecycle](./jetpack/lifecycle)
- [ ] [ViewModel](./jetpack/viewmodel)  
- [ ] [Room](./jetpack/room)
- [ ] [Navigation](./jetpack/navigation)
- [ ] [Compose](./jetpack/compose)

### Phase 6: Android Framework
- [ ] Activity Launch Process
- [ ] View Drawing Process
- [ ] Event Dispatch Mechanism
- [ ] Handler Message Mechanism
- [ ] Binder IPC Mechanism

## 📚 Completed Modules

### [Retrofit Source Analysis](./retrofit)

**Learning Period**: 2025-01-15 ~ 2025-01-20  
**Difficulty**: ⭐⭐⭐☆☆  
**Recommendation**: ⭐⭐⭐⭐⭐

**Key Takeaways**:
- ✅ Understanding practical application of dynamic proxy
- ✅ Learning annotation-driven API design
- ✅ Mastering best practices of Builder, Factory, and Adapter patterns
- ✅ Understanding extension mechanism design

**Key Files**:
- [Complete demo project](./retrofit/demo)
- [Dynamic proxy mechanism](./retrofit/notes/02-dynamic-proxy.md)
- [Annotation parsing process](./retrofit/notes/03-annotation-parsing.md)
- [Architecture diagram](./retrofit/diagrams/architecture.png)

---

## 🛠️ How to Use This Repository

### 1. Clone the Project
```bash
git clone https://github.com/yourusername/AndroidDeepDive.git
cd AndroidDeepDive
```

### 2. Run Demo Projects
```bash
# Using Retrofit as example
cd retrofit/demo
# Open this directory in Android Studio
```

### 3. Read Learning Notes
```bash
# Read in numbered order
cd retrofit/notes
cat 01-overview.md
cat 02-dynamic-proxy.md
...
```

### 4. View Diagrams
All diagrams are in the `diagrams/` directory of each module

---

## 📝 Learning Methodology

### My Three-Step Source Reading Method

**Step 1: Quick Overview (30 mins)**
- Review project structure and main classes
- Understand overall architecture
- Mark key classes and methods

**Step 2: Trace Main Flow (2-3 hours)**
- Debug a complete process
- Draw call chain diagrams
- Record core logic

**Step 3: Deep Dive (Ongoing)**
- Study interesting modules
- Analyze design patterns
- Organize best practices

### Reading with Questions

❌ Don't: Read line by line from the beginning  
✅ Do: Start from usage entry points with specific questions

**Example Questions**:
- How does Retrofit.create() work?
- When are annotations parsed?
- How to support multiple data formats?
- How is thread switching implemented?

### Output-Driven Learning

✅ Write learning notes  
✅ Draw flow diagrams  
✅ Write technical blog posts  
✅ Give technical talks  

---

## 🎯 Goals & Plans

### 2025 Goals
- [ ] Complete source code learning for 10 mainstream libraries
- [ ] Deep understanding of 5 Android Framework core modules  
- [ ] Write 20+ source code analysis articles
- [ ] Reach 500+ GitHub Stars

### Current Progress
- ✅ Retrofit source analysis (2025-01-20)
- 🔄 OkHttp source analysis (in progress)
- ⏳ Glide source analysis (planned)

---

## 💬 Communication & Feedback

**If this repository helps you**:
- ⭐ Star it to show support
- 🐛 Report issues
- 💡 Submit PRs for suggestions
- 🤝 Welcome to learn together

**Contact**:
- Email: your.email@example.com
- Blog: https://yourblog.com
- Twitter: @yourhandle

---

## 📚 Learning Resources

### Recommended Books
- *The Art of Android Development*
- *Deep Understanding of Android Kernel Design*
- *Effective Java*

### Recommended Websites
- [Android Developers](https://developer.android.com/)
- [Square Engineering Blog](https://developer.squareup.com/)
- [Android Source Code](https://cs.android.com/)

### Recommended Tools
- Android Studio + Source Code
- PlantUML (for diagrams)
- Obsidian (knowledge management)

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

Thanks to all open-source project contributors for allowing us to learn from excellent code and design ideas.

Special thanks to:
- [Retrofit](https://github.com/square/retrofit) by Square
- [OkHttp](https://github.com/square/okhttp) by Square
- [Glide](https://github.com/bumptech/glide) by Bump Technologies

---

**Last Updated**: 2025-01-20  
**Repository**: https://github.com/yourusername/AndroidDeepDive
