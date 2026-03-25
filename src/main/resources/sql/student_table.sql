-- 创建学生表
CREATE TABLE IF NOT EXISTS student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    grade VARCHAR(50),
    email VARCHAR(100),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 插入一些测试数据
INSERT INTO student (name, age, grade, email) VALUES
('张三', 18, '高三', 'zhangsan@example.com'),
('李四', 17, '高二', 'lisi@example.com'),
('王五', 16, '高一', 'wangwu@example.com'),
('赵六', 19, '高三', 'zhaoliu@example.com'),
('钱七', 18, '高二', 'qianqi@example.com');