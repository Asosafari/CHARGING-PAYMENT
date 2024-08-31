alter table charging_plans
add column charging_plan_type ENUM('PERIMUM','BASIC','DEFAULT') NOT NULL,