CREATE TRIGGER localtimefatura AFTER INSERT ON Fatura
    BEGIN
       UPDATE Fatura SET DataOra = datetime('now', 'localtime')
       WHERE ID_Fatura = new.ID_Fatura;
    END
