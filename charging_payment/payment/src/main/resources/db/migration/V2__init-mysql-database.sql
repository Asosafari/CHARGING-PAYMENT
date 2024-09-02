DELIMITER $$

CREATE PROCEDURE update_account_balance(
    IN p_charging_user_id BIGINT,
    IN p_amount DECIMAL(19, 4)
)
BEGIN
    UPDATE payment_users
    SET account_balance = account_balance + p_amount
    WHERE id = p_charging_user_id;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User not found or update failed';
    END IF;
END$$

DELIMITER ;
