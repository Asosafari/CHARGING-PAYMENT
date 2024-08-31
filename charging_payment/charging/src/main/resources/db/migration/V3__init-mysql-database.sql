ALTER TABLE charging_plans
ADD COLUMN charging_plan_type ENUM('PREMIUM', 'BASIC', 'DEFAULT') NOT NULL;
