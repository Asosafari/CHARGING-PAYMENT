
CREATE VIEW transaction_view AS
SELECT t.id AS transaction_id, t.user_id, u.username, t.charging_plan_id, cp.plan_name, t.amount, t.is_success, t.created_date
FROM transactions t
JOIN users u ON t.user_id = u.id
JOIN charging_plans cp ON t.charging_plan_id = cp.id;


DELIMITER $$

CREATE PROCEDURE charging_account(
    IN p_user_id BIGINT,
    IN p_rate_per_unit DECIMAL(19, 4),
    OUT p_current_balance DECIMAL(19, 4)
)
BEGIN
    DECLARE v_amount_to_add DECIMAL(19, 4);

    SET v_amount_to_add = p_rate_per_unit * 1000;

    UPDATE users
    SET balance = balance + v_amount_to_add
    WHERE id = p_user_id;

    SELECT balance INTO p_current_balance
    FROM users
    WHERE id = p_user_id;
END$$

DELIMITER ;



