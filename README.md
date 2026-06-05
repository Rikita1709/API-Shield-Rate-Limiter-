# 🛡️ API Shield — Rate Limiting & API Analytics System

## 🚀 Overview

API Shield is a full-stack API security system built using **Spring Boot** and **React.js**.  
It provides API Key authentication, rate limiting, request blocking, and a real-time analytics dashboard with charts.

This project simulates a lightweight **API Gateway-style system** used in real-world backend infrastructure.

---

## ✨ Features

- 🔑 API Key Authentication
- 🚦 Fixed Window Rate Limiting
- ❌ Request Blocking when limit exceeded
- 📊 Real-time API analytics dashboard
- 📈 Total / Allowed / Blocked request tracking
- 🏆 Per-API Key usage statistics
- 📜 Request logging system
- 📉 Data visualization using Chart.js

---

## 🧠 Tech Stack

### Backend
- Java
- Spring Boot
- Spring Web
- REST APIs

### Frontend
- React.js
- Chart.js
- JavaScript (ES6)

---

## 🏗️ System Architecture

```
                ┌──────────────────────┐
                │      React UI        │
                │  (Dashboard Client)  │
                └─────────┬────────────┘
                          │
                          ▼
                ┌──────────────────────┐
                │   Spring Boot API    │
                │   (REST Controllers) │
                └─────────┬────────────┘
                          │
       ┌──────────────────┼──────────────────┐
       ▼                  ▼                  ▼
┌──────────────────┐ ┌──────────────────┐ ┌──────────────────┐
│ API Key Service  │ │ Rate Limiter     │ │ Logging Service  │
│ (Validation)     │ │ (Blocking Logic)  │ │ (Request Logs)   │
└──────────────────┘ └──────────────────┘ └──────────────────┘
                          │
                          ▼
                ┌──────────────────────┐
                │ Statistics Service   │
                │ (Analytics Engine)   │
                └──────────────────────┘
```

---

## 🔑 Sample API Keys

```
key123
admin456
```

---

## ▶️ How to Run the Project

### 1️⃣ Clone Repository
```bash
git clone https://github.com/Rikita1709/API-Shield-Rate-Limiter-.git
cd API-Shield-Rate-Limiter-
```

### 2️⃣ Run Backend (Spring Boot)
```bash
cd api-shield-backend/api-shield-backend
mvn spring-boot:run
```

Backend runs at:
```
http://localhost:8080
```

---

### 3️⃣ Run Frontend (React)
```bash
cd api-shield-frontend/api-shield-frontend
npm install
npm run dev
```

Frontend runs at:
```
http://localhost:5173
```

---

## 📡 API Endpoints

### Test API
```
GET /api/test?apiKey=key123
```

### Logs
```
GET /api/logs
```

### System Stats
```
GET /api/stats
```

### API Key Usage Stats
```
GET /api/stats/api-keys
```

---

## 📊 Dashboard Features

- Total Requests Counter
- Allowed vs Blocked Requests
- Doughnut Chart (Traffic Visualization)
- API Key Usage Table
- Request Logs Viewer

---



---

## 🔮 Future Improvements

- Redis-based distributed rate limiting
- Sliding window algorithm
- JWT authentication instead of API keys
- Docker containerization
- PostgreSQL integration
- Spring Cloud Gateway integration

---

## 📌 What This Project Demonstrates

- Backend system design
- API security fundamentals
- Rate limiting algorithms
- Full-stack integration
- Real-time analytics
- Clean REST API architecture

---

## 👨‍💻 Author

**Rikita Maiti**
