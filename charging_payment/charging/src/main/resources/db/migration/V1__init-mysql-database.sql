
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    balance DECIMAL(19, 4) NOT NULL CHECK (balance >= 0),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_Authorize BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP,
    version INT NOT NULL DEFAULT 1,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
)engine=InnoDB;

CREATE TABLE charging_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_name VARCHAR(255) NOT NULL UNIQUE,
    rate_per_unit DECIMAL(19, 4) NOT NULL,
    price_per_unit DECIMAL(19, 4) NOT NULL,
    description TEXT,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
)engine=InnoDB;


CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    charging_plan_id BIGINT NOT NULL,
    amount DECIMAL(19, 4) NOT NULL CHECK (amount >= 0),
    is_success BOOLEAN NOT NULL,
    payment_type ENUM('DIRECT', 'GATEWAY') NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (charging_plan_id) REFERENCES charging_plans(id)
)engine=InnoDB;


CREATE TABLE user_charging_plan (
    user_id BIGINT NOT NULL,
    charging_plan_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, charging_plan_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (charging_plan_id) REFERENCES charging_plans(id)
)engine=InnoDB;





