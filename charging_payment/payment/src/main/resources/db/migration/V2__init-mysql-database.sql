
DELIMITER $$
CREATE PROCEDURE update_account_balance(
    IN user_id BIGINT,
    IN p_amount DECIMAL(19, 4)
)
BEGIN
    UPDATE payment_users
    SET account_balance = account_balance - p_amount
    WHERE id = user_id;
END$$

DELIMITER;
