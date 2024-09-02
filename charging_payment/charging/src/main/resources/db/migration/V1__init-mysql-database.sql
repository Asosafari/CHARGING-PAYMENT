
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(12) NOT NULL UNIQUE,
    password VARCHAR(12) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    balance DECIMAL(19, 4) NOT NULL CHECK (balance >= 0),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
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
    version INT NOT NULL DEFAULT 1,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
)engine=InnoDB;


CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    charging_plan_id BIGINT NOT NULL,
    amount DECIMAL(19, 4) NOT NULL CHECK (amount >= 0),
    is_success BOOLEAN NOT NULL,
    transaction_type ENUM('DIRECT', 'GATEWAY') NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (charging_plan_id) REFERENCES charging_plans(id)
)engine=InnoDB;


CREATE TABLE user_charging_plan (
    user_id BIGINT NOT NULL,
    charging_plan_id BIGINT NOT NULL,
    version INT NOT NULL DEFAULT 1,
    PRIMARY KEY (user_id, charging_plan_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (charging_plan_id) REFERENCES charging_plans(id)
)engine=InnoDB;


CREATE TABLE authorized_bank_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    bank_user_id BIGINT NOT NULL UNIQUE,
    token_ch VARCHAR(255) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
)engine=InnoDB;


CREATE TABLE successful_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    token_ch VARCHAR(255) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
)engine=InnoDB;

CREATE TABLE logging_event (
  event_id BIGINT NOT NULL AUTO_INCREMENT,
  timestmp BIGINT NOT NULL,
  formatted_message TEXT NOT NULL,
  logger_name VARCHAR(255) NOT NULL,
  level_string VARCHAR(255) NOT NULL,
  thread_name VARCHAR(255),
  reference_flag SMALLINT,
  caller_filename VARCHAR(255) NOT NULL,
  caller_class VARCHAR(255) NOT NULL,
  caller_method VARCHAR(255) NOT NULL,
  caller_line CHAR(4) NOT NULL,
  arg0 VARCHAR(255),
  arg1 VARCHAR(255),
  arg2 VARCHAR(255),
  arg3 VARCHAR(255),
  PRIMARY KEY (event_id)
);

CREATE TABLE logging_event_property (
  event_id BIGINT NOT NULL,
  mapped_key VARCHAR(255) NOT NULL,
  mapped_value TEXT,
  PRIMARY KEY (event_id, mapped_key),
  FOREIGN KEY (event_id) REFERENCES logging_event(event_id)
);

CREATE TABLE logging_event_exception (
  event_id BIGINT NOT NULL,
  i SMALLINT NOT NULL,
  trace_line VARCHAR(255) NOT NULL,
  PRIMARY KEY (event_id, i),
  FOREIGN KEY (event_id) REFERENCES logging_event(event_id)
);




