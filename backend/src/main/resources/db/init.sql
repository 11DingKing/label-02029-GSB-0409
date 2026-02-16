-- 设置字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bookstore;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(500),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'USER',
    status INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 分类表
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    icon VARCHAR(100),
    sort_order INT DEFAULT 0,
    status INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 书籍表
CREATE TABLE IF NOT EXISTS books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100),
    isbn VARCHAR(20),
    publisher VARCHAR(100),
    description TEXT,
    cover_image VARCHAR(500),
    original_price DECIMAL(10,2),
    price DECIMAL(10,2) NOT NULL,
    quality VARCHAR(20),
    stock INT DEFAULT 1,
    category_id BIGINT,
    seller_id BIGINT,
    status INT DEFAULT 1,
    view_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (seller_id) REFERENCES users(id)
);

-- 购物车表
CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    address VARCHAR(500),
    phone VARCHAR(20),
    receiver VARCHAR(50),
    remark TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 订单项表
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

-- 收藏表
CREATE TABLE IF NOT EXISTS favorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    UNIQUE KEY unique_favorite (user_id, book_id)
);

-- 插入分类数据
INSERT INTO categories (name, icon, sort_order) VALUES 
('文学小说', 'book', 1),
('教材教辅', 'graduation-cap', 2),
('计算机', 'laptop', 3),
('经济管理', 'chart-line', 4),
('人文社科', 'users', 5),
('外语学习', 'globe', 6),
('考试辅导', 'edit', 7),
('生活休闲', 'coffee', 8);

-- 插入示例书籍
INSERT INTO books (title, author, isbn, publisher, description, cover_image, original_price, price, quality, stock, category_id, seller_id, status) VALUES 
('活着', '余华', '9787506365437', '作家出版社', '讲述了农村人福贵悲惨的人生遭遇。福贵本是个阔少爷，可他嗜赌如命，终于赌光了家业，一贫如洗。', 'https://img3.doubanio.com/view/subject/l/public/s29053580.jpg', 45.00, 25.00, '九成新', 3, 1, 2, 1),
('三体', '刘慈欣', '9787536692930', '重庆出版社', '文化大革命如火如荼进行的同时，军方探寻外星文明的绝秘计划"红岸工程"取得了突破性进展。', 'https://img2.doubanio.com/view/subject/l/public/s2768378.jpg', 68.00, 35.00, '八成新', 2, 1, 2, 1),
('Java编程思想', 'Bruce Eckel', '9787111213826', '机械工业出版社', 'Java学习经典之作，从Java的基础语法到最高级特性，适合各层次Java程序员阅读。', 'https://img9.doubanio.com/view/subject/l/public/s27243447.jpg', 108.00, 45.00, '七成新', 5, 3, 2, 1),
('经济学原理', '曼昆', '9787301150894', '北京大学出版社', '世界上最流行的经济学教材，为经济学入门者提供了最好的学习材料。', 'https://img1.doubanio.com/view/subject/l/public/s4148631.jpg', 88.00, 40.00, '九成新', 4, 4, 2, 1),
('人类简史', '尤瓦尔·赫拉利', '9787508647357', '中信出版社', '从十万年前有生命迹象开始到21世纪资本、科技交织的人类发展史。', 'https://img3.doubanio.com/view/subject/l/public/s27814883.jpg', 68.00, 30.00, '八成新', 3, 5, 2, 1);
