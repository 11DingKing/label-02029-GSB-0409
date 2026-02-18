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

-- 插入管理员和示例用户 (密码都是 123456，使用BCrypt加密)
INSERT INTO users (email, password, nickname, role, status) VALUES 
('admin@bookstore.com', '$2a$10$EqKcp1WFKVQISheBxkV.aOTP1LvVfpWTLEGDfFKH.VKIl3MXhFPi.', '管理员', 'ADMIN', 1),
('user@bookstore.com', '$2a$10$EqKcp1WFKVQISheBxkV.aOTP1LvVfpWTLEGDfFKH.VKIl3MXhFPi.', '测试用户', 'USER', 1),
('seller@bookstore.com', '$2a$10$EqKcp1WFKVQISheBxkV.aOTP1LvVfpWTLEGDfFKH.VKIl3MXhFPi.', '书籍卖家', 'USER', 1);

-- 插入分类数据
INSERT INTO categories (id, name, icon, sort_order, status) VALUES 
(1, '文学小说', 'book', 1, 1),
(2, '教材教辅', 'graduation-cap', 2, 1),
(3, '计算机', 'laptop', 3, 1),
(4, '经济管理', 'chart-line', 4, 1),
(5, '人文社科', 'users', 5, 1),
(6, '外语学习', 'globe', 6, 1),
(7, '考试辅导', 'edit', 7, 1),
(8, '生活休闲', 'coffee', 8, 1);

-- 插入示例书籍 (使用placeholder图片)
INSERT INTO books (title, author, isbn, publisher, description, cover_image, original_price, price, quality, stock, category_id, seller_id, status, view_count) VALUES 
('活着', '余华', '9787506365437', '作家出版社', '讲述了农村人福贵悲惨的人生遭遇。福贵本是个阔少爷，可他嗜赌如命，终于赌光了家业，一贫如洗。穷困之中的福贵因为母亲生病前去求医，没想到半路上被国民党部队抓了壮丁，后被解放军所俘虏，回到家乡他才知道母亲已经去世。', 'https://picsum.photos/seed/book1/200/280', 45.00, 25.00, '九成新', 3, 1, 2, 1, 128),
('三体', '刘慈欣', '9787536692930', '重庆出版社', '文化大革命如火如荼进行的同时，军方探寻外星文明的绝秘计划"红岸工程"取得了突破性进展。但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。', 'https://picsum.photos/seed/book2/200/280', 68.00, 35.00, '八成新', 2, 1, 2, 1, 256),
('Java编程思想', 'Bruce Eckel', '9787111213826', '机械工业出版社', 'Java学习经典之作，从Java的基础语法到最高级特性，适合各层次Java程序员阅读。本书能够帮助你深入理解Java语言和编程思想。', 'https://picsum.photos/seed/book3/200/280', 108.00, 45.00, '七成新', 5, 3, 3, 1, 89),
('经济学原理（第7版）', '曼昆', '9787301150894', '北京大学出版社', '世界上最流行的经济学教材，为经济学入门者提供了最好的学习材料。本书用通俗易懂的语言讲解经济学的基本原理。', 'https://picsum.photos/seed/book4/200/280', 88.00, 40.00, '九成新', 4, 4, 2, 1, 167),
('人类简史', '尤瓦尔·赫拉利', '9787508647357', '中信出版社', '从十万年前有生命迹象开始到21世纪资本、科技交织的人类发展史。这是一部宏大的人类简史，理清了影响人类发展的重大脉络。', 'https://picsum.photos/seed/book5/200/280', 68.00, 30.00, '八成新', 3, 5, 3, 1, 203),
('高等数学（第七版）上册', '同济大学', '9787040396638', '高等教育出版社', '经典高数教材，适合理工科学生使用。内容涵盖函数与极限、导数与微分、微分中值定理与导数的应用等。', 'https://picsum.photos/seed/book6/200/280', 38.00, 18.00, '八成新', 8, 2, 2, 1, 342),
('Python编程从入门到实践', 'Eric Matthes', '9787115428028', '人民邮电出版社', '一本针对所有层次Python读者而作的Python入门书。全书分两部分：基础知识和项目实践。', 'https://picsum.photos/seed/book7/200/280', 89.00, 42.00, '九成新', 6, 3, 3, 1, 178),
('红楼梦', '曹雪芹', '9787020002207', '人民文学出版社', '中国古典四大名著之首，清代作家曹雪芹创作的章回体长篇小说。以贾、史、王、薛四大家族的兴衰为背景。', 'https://picsum.photos/seed/book8/200/280', 59.00, 28.00, '七成新', 2, 1, 2, 1, 95),
('新概念英语1', '亚历山大', '9787560013466', '外语教学与研究出版社', '享誉全球的最为经典地道的英语教材，以其严密的体系性、严谨的科学性、精湛的实用性深受英语学习者的青睐。', 'https://picsum.photos/seed/book9/200/280', 29.00, 12.00, '八成新', 10, 6, 2, 1, 267),
('考研英语词汇', '朱伟', '9787560555218', '西安交通大学出版社', '考研英语必备词汇书，涵盖考研英语大纲要求的全部词汇，配有例句和记忆方法。', 'https://picsum.photos/seed/book10/200/280', 48.00, 22.00, '九成新', 15, 7, 3, 1, 456);

-- 插入示例订单
INSERT INTO orders (order_no, user_id, total_amount, status, address, phone, receiver, remark) VALUES
('ORD20240101001', 2, 60.00, 'COMPLETED', '北京市海淀区中关村大街1号', '13800138001', '张三', '请尽快发货'),
('ORD20240102002', 2, 45.00, 'SHIPPED', '上海市浦东新区陆家嘴环路1000号', '13800138002', '李四', NULL),
('ORD20240103003', 3, 87.00, 'PAID', '广州市天河区天河路385号', '13800138003', '王五', '周末送货');

-- 插入订单项
INSERT INTO order_items (order_id, book_id, quantity, price) VALUES
(1, 1, 1, 25.00),
(1, 2, 1, 35.00),
(2, 3, 1, 45.00),
(3, 4, 1, 40.00),
(3, 6, 1, 18.00),
(3, 9, 1, 12.00);
