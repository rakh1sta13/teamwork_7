# Ramen Restaurant Ordering Platform

A complete backend system for an online Ramen Restaurant Ordering Platform built with Spring Boot in Java.

## Features

### 🏪 Branch Management
- Each branch has: id, name, address, phone number, working hours, latitude/longitude
- API to list all branches

### 🍜 Menu & Products
- Product categories: Packages, Hot Food Platters, Fresh Selections, Desserts, Drinks
- Each product has: id, name, description, price, category, image, availability

### 🛒 Cart & Order
- Users can add/remove items, update quantity
- Calculate subtotal, discount, tax, total
- Confirm order with branchId, preferred date & time
- Customer info (name, email, phone) and payment method
- Ordered items list with final price summary

### 📦 Checkout Steps
- Contact details step
- Order summary step
- Confirm order step
- Thank you page

### 📝 Applications (Feedback/Complaints)
- Users can submit feedback or complaints
- Applications have types (FEEDBACK or COMPLAINT)
- Status tracking (PENDING, IN_PROGRESS, RESOLVED, CLOSED)

## Tech Stack

- **Framework**: Spring Boot 3.x
- **Build Tool**: Gradle
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Libraries**: Lombok, MapStruct, Springdoc OpenAPI (Swagger)
- **Security**: Spring Security
- **Testing**: JUnit, Testcontainers

## Architecture

Clean Layered Architecture:
```
/controller - REST API controllers
/service - Business logic services
/repository - Data access layer
/entity - JPA entities
/dto - Data transfer objects
/mapper - MapStruct mappers
/exception - Exception handlers
/config - Configuration classes
/security - Security configuration
```

## Business Logic Rules

### Discounts:
- If 3 or more of same product → 5% discount automatically
- If cart total > 300,000 → apply 10% discount

### Tax:
- VAT = 12%
- Final price formula: total = (subtotal – discount) + tax

## APIs

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category

### Branches
- `GET /api/branches` - Get all branches
- `GET /api/branches/active` - Get active branches

### Cart/Order
- `POST /api/orders` - Create order
- `GET /api/orders/{id}` - Get order by ID

### Checkout
- `POST /api/checkout/contact` - Process contact information
- `POST /api/checkout/summary` - Calculate order summary
- `POST /api/checkout/confirm` - Confirm order
- `GET /api/checkout/{orderId}` - Get confirmed order

### Applications (Feedback/Complaints)
- `GET /api/applications` - Get all applications
- `GET /api/applications/type/{type}` - Get applications by type
- `GET /api/applications/status/{status}` - Get applications by status
- `POST /api/applications` - Create application
- `PUT /api/applications/{id}` - Update application

## Setup Instructions

### Using Docker
```bash
# Start the application with Docker Compose
docker-compose up --build
```

### Manual Setup
1. Make sure you have Java 17+ installed
2. Install PostgreSQL and create a database named `ramen_db`
3. Update the database credentials in `application.yml` if needed
4. Build the project: `./gradlew build`
5. Run the application: `./gradlew bootRun`

## API Documentation
API documentation is available at: `http://localhost:8080/swagger-ui.html`

## Payment Methods Supported
- Payme
- Uzum Bank
- Visa
- Mastercard

## Sample JSON Payloads

### Create Order
```json
{
  "branchId": 1,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "customerPhone": "+998901234567",
  "preferredDateTime": "2023-12-01T18:00:00",
  "paymentMethod": "VISA",
  "deliveryAddress": "123 Main Street",
  "notes": "Please deliver at back door",
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

### Create Application (Feedback/Complaint)
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+998901234567",
  "applicationType": "FEEDBACK",
  "message": "The ramen was absolutely delicious! Best I've ever had.",
  "status": "PENDING"
}
```