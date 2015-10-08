SELECT SUM(ProduktiShitur.Sasia) as Sassia, Produktet.Emri  as emri
FROM ProduktiShitur 
LEFT JOIN Produktet
ON ProduktiShitur.ID_Prod = Produktet.ID_Prod
GROUP BY emri ORDER BY Sassia DESC;