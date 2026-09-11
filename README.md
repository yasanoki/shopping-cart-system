# Shopping Cart System

A console-based e-commerce shopping cart system built in Java, demonstrating core object-oriented programming concepts (inheritance, polymorphism, abstraction) and file-based data persistence — no database required.

一个用 Java 编写的控制台电商购物车系统，展示面向对象编程的核心概念（继承、多态、抽象），并使用纯文本文件做数据持久化，不依赖数据库。

---

## Features / 功能

### Customer / 顾客端
- Sign up & login with format-validated username, password, phone number, and email (regex-based)
  注册与登录，用户名、密码、电话、邮箱均有格式验证（正则表达式）
- Browse products: view all, or search by name / brand / model / price range
  浏览商品：查看全部，或按名称、品牌、型号、价格区间搜索
- Shopping cart: add / remove items, update quantities, view cart with live totals
  购物车：加入/移除商品、修改数量、查看购物车实时总额
- Checkout with multiple payment methods: Cash, Credit Card, Bank Transfer
  结账支持多种付款方式：现金、信用卡、银行转账
- Order history: view past orders and full order details
  订单历史：查看过往订单及订单详情

### Product Staff / 员工端
- Inventory management: add products, list all, search, add stock, update stock
  库存管理：新增商品、查看全部、搜索、补货、更新库存
- Supports multiple product categories (Keyboard, Mouse, Monitor, Controller) via a shared `Product` superclass
  支持多种商品类别（键盘、鼠标、显示器、手柄），通过共同的 `Product` 父类实现

---

## Tech Stack / 技术栈

- **Java** — OOP design (inheritance, polymorphism, abstraction)
  Java — 面向对象设计（继承、多态、抽象）
- **File I/O** — data persistence via plain text files (no database)
  文件读写 — 用纯文本文件做数据持久化（不使用数据库）
- **Regex** — input validation for sign-up fields
  正则表达式 — 用于注册表单的输入格式验证
- **Singleton pattern** — `InventoryManager` ensures a single shared source of product data
  单例模式 — `InventoryManager` 保证商品数据只有一份共享来源

---

## Project Structure / 项目结构

| Class | Responsibility |
|---|---|
| `MainMenu` | Application entry point and top-level menu |
| `User`, `Customer`, `ProductStaff` | User hierarchy (base class + roles) |
| `Product`, `Keyboard`, `Mouse`, `Monitor`, `Controller` | Product hierarchy |
| `InventoryManager` | Singleton managing the product catalog and file I/O |
| `ShoppingCart`, `CartItem` | Cart logic and cart line items |
| `CustomerProductBrowser` | Product browsing and search |
| `Order`, `OrderItem` | Order records and line items |
| `CheckoutService` | Coordinates checkout, payment, and order creation |
| `Payment`, `PaymentMethod`, `CashPayment`, `CardPayment`, `BankTransfer` | Payment method hierarchy |

---

## How to Run / 如何运行

1. Clone or download this repository.
   克隆或下载此仓库。
2. Open the folder in NetBeans (or any Java IDE), or compile directly:
   在 NetBeans（或任意 Java IDE）中打开该文件夹，或直接编译：
   ```
   javac *.java
   java MainMenu
   ```
3. Run `MainMenu.java` as the entry point.
   以 `MainMenu.java` 作为程序入口运行。

Data files (`customers.txt`, `products.txt`, `order_history.txt`) are created automatically in the working directory on first run if they don't already exist.

数据文件（`customers.txt`、`products.txt`、`order_history.txt`）如果不存在，首次运行时会自动在工作目录中创建。

---

## Author / 作者

- Chuah Yi Xuan
- Thong Hui Ting
- Ng Shang Ru
- Ng Ee Xun

Final-year B.Sc. (Hons) Interactive Software Technology students, TAR UMT.
计算机科学（互动软件技术）荣誉学士，终末年学生，拉曼理工大学。
