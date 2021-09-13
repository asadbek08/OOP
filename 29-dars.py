class Avto:
    def __init__(self, model, rang, karobka, narh, kilometer= 0):
        self.model= model
        self.rang = rang
        self.karobka = karobka 
        self.narh = narh
        self.kilometer= kilometer
        
    def get_info(self):
        return f"Model: {self.model}\nRangi: {self.rang}\nKarobkasi: {self.karobka}\nNarxi: {self.narh}\nMasofa: {self.kilometer} "
    
    def update_km(self, yangi_masofa):
        self.kilometer += yangi_masofa
        return self.kilometer

class Avtosalon:
    def __init__(self, nomi, manzili):
        self.nomi= nomi
        self.manzili= manzili 
        self.avtolar = []
        self.avtolar_soni= 0
    
    def get_name(self):
        return self.nomi
    
    def get_avto(self):
        return [x.get_info().replace('\n', ' ')  for x in self.avtolar]
        
    def add_avto(self, avto):
        self.avtolar.append(avto)
        self.avtolar_soni +=1
        
# =============================================================================
# avto1 = Avto("Tesla", "Qora", "Automatic", "$40000" )
# avto2 = Avto("BMW", "Oq", "Oddiy", "$50000")
# avto3 = Avto("Mercedes", "Kulrang", "Avtomat", "$200000", 5000)
# salon= Avtosalon("Hybrid logos", "Toshkent, Olmazor")
# salon.add_avto(avto1)
# salon.add_avto(avto2)
# salon.add_avto(avto3)
# print(salon.get_avto())
# # avto1.update_km(10000)
# # avto1.update_km(10000)
# # print(avto1.get_info())
# =============================================================================
