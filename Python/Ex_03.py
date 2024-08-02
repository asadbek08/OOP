"""
  Talaba klassiga yana bir, fanlar degan xususiyat qo'shing.
  Bu xususiyat parametr sifatida uzatilmasin va
obyekt yaratilganida bo'sh ro'yxatdan iborat bo'lsin (self.fanlar=[])
  Fan degan yangi klass yarating va bu klassdan turli fanlar uchun alohida obyektlar yarating.
  Talaba klassiga fanga_yozil() degan yangi metod yozing.
  Bu metod parametr sifatida Fan klassiga tegishli obyektlarni qabul qilib olsin
va talabaning fanlar ro'yxatiga qo'shib qo'ysin.
  Talabaning fanlari ro'yxatidan biror fanni o'chirib tashlash uchun remove_fan() metodini yozing.
  Agar bu metodga ro'yxatda yo'q fan uzatilsa "Siz bu fanga yozilmagansiz" xabarini qaytarsin.
  Yuqoridagi Shaxs klassidan boshqa turli voris klasslar yaratib ko'ring 
(masalan Professor, Foydalanuvchi, Sotuvchi, Mijoz va hokazo)
  Har bir klassga o'ziga hoz xususiyatlar va metodlar yuklang.
  Barcha yangi klasslar uchun get_info() metodini qayta yozib chiqing.
  Voris klasslardan yana boshqa voris klass yaratib ko'ring. Misol uchun Foydalanuvchi
klassidan Admin klassini yarating. 
  Admin klassiga foydalanuvchida yo'q yangi metodlar yozing, masalan,
ban_user() metodi konsolga "Foydalanuvchi bloklandi" degan matn chiqarsin.
KODLAR

"""
from uuid import uuid4

class Shaxs:
    __odamlar_soni = 0
    """Shaxslar haqida ma'lumot"""
    def __init__(self,ism,familiya,passport,tyil, salary=0):
        self.ism = ism
        self.familiya = familiya
        self.passport = passport
        self.tyil = tyil
        self.fanlar = []
        self.__id = uuid4()
        self.__salary = salary
        Shaxs.__odamlar_soni+=1
        
    @classmethod
    def get_odamlar_soni(cls):
        return cls.__odamlar_soni
    
    def get_id(self):
        return self.__id

    def get_salary(self):
        return self.__salary
    
    def add_salary(self, salary):
        self.__salary += salary
    
    def get_info(self):
        """Shaxs haqida ma'lumot"""
        info = f"{self.ism} {self.familiya}. "
        info += f"Passport:{self.passport}, {self.tyil}-yilda tug`ilgan"
        return info
        
    def get_age(self,yil):
        """Shaxsning yoshini qaytaruvchi metod"""
        return yil - self.tyil
    
class Fan:
    def __init__(self, nomi, davomiyligi):
        self.nomi = nomi
        self.davomiyligi = davomiyligi
        self.oquvchilar_soni = 0
    
    def get_fan(self):
        fan = f"{self.nomi}".title() + f" fani {self.davomiyligi} yil davom etadi"
        return fan
        
    
class Talaba(Shaxs):
    """Talaba klassi"""
    __talabalar_soni = 0
    def __init__(self,ism,familiya,passport,tyil,idraqam):
        """Talabaning xususiyatlari"""
        super().__init__(ism, familiya, passport, tyil)
        self.idraqam = idraqam
        self.bosqich = 1
        self.fanlar = []
        self.fanlar_soni = 0
        Talaba.__talabalar_soni += 1
        
    @classmethod
    def get_talabalar_soni(cls):
        return cls.__talabalar_soni
    
    def get_id(self):
        """Talabaning ID raqami"""
        return self.idraqam
    
    def get_bosqich(self):
        """Talabaning o'qish bosqichi"""
        return self.bosqich
    
    def get_info(self):
        """Talaba haqida ma'lumot"""
        info = f"{self.ism} {self.familiya}. "
        info += f"{self.get_bosqich()}-bosqich. ID raqami: {self.idraqam} Fanlar: {self.fanlar}"
        return info
    
    def fanga_yozil(self, fan):
        self.fanlar.append(fan)
        self.fanlar_soni +=1
    
    def get_fan(self):
        return [fan.get_fan() for fan in self.fanlar]
    
    def remove_fan(self, fan):
        if fan in self.fanlar:
            self.fanlar.remove(fan)
        else:
            return "Siz bu fanga yozilmagansiz"

class Professor(Shaxs):
    def __init__(self, ism,familiya,passport,tyil, mutaxasisligi, tamomlagan_darajasi):
        super().__init__(ism, familiya, passport, tyil)
        self.mutaxasisligi = mutaxasisligi
        self.tamomlagan_darajasi = tamomlagan_darajasi
    
    def get_info(self):
        info =  f"{self.ism} {self.familiya} "
        info += f"Passport:{self.passport}, {self.tyil}-yilda tug`ilgan "
        info += f"{self.mutaxasisligi} fanidan {self.tamomlagan_darajasi} darjasi bor"
        return info
    
class User(Shaxs):
    def __init__(self,ism,familiya,passport,tyil, soxasi):
        super().__init__(ism,familiya,passport,tyil)
        self.soxasi = soxasi
        
    def get_info(self):
        info =  f"{self.ism} {self.familiya} "
        info += f"Passport:{self.passport}, {self.tyil}-yilda tug`ilgan "
        info += f"Kasbi: {self.soxasi}"
        return info
    
class Admin(User):
    def __init__(self, ism, familiya, passport, tyil, soxasi):
        super().__init__(ism,familiya,passport,tyil, soxasi)
        self.tajribasi = 0
        self.soxasi = "Admin"
    
    def get_info(self):
        info =  f"{self.ism} {self.familiya} "
        info += f"Passport:{self.passport}, {self.tyil}-yilda tug`ilgan "
        info += f"{self.soxasi} bo'lib {self.tajribasi} yildan beri ishlaydi"
        return info
    
    def ban_user(self, foydalanuvchi):
        info = foydalanuvchi.get_info()
        print(f"{info} bloklandi")
    
admin1 = Admin("Asadbek", "Iskandarov", "FA2006264", 1999, "hacker")
user1 = User("Ozodbek", "Nazarbekov", "FA2994949", 1974, "singer")
prof1 = Professor("Giovanni" , "Squillero", "FA2304934", 1982, "Computer Science", "PhD")
inson = Shaxs("Asadbek", "Iskandarov", "FA2909999", 1999, 20000)





# =============================================================================
# fan1 = Fan('matematika', 3 )
# fan2 = Fan('biologiya', 2)
# fan3 = Fan('informatika', 2)
# fan4 = Fan('ona tili', 6)
talaba = Talaba("Valijon","Aliyev","FA112299",2000,"0000012")
# 
# talaba.fanga_yozil(fan1)
# talaba.fanga_yozil(fan2)
# talaba.fanga_yozil(fan3)
# talaba.remove_fan(fan4)
# print(talaba.get_fan())
# =============================================================================
