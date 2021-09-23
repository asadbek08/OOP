# -*- coding: utf-8 -*-
"""
Created on Thu Sep 23 09:52:54 2021

@author: Asadbek Iskandarov
"""

def sorovnoma():
    ism = input("Ismingiz nima? ")
    familiya = input("Familiyangiz nima? ")
    kasb = input("Kasbingiz nima? ")
    
    with open('sorovnoma.txt', 'a') as new:
        new.write(ism+'\n')
        new.write(familiya+'\n')
        new.write(kasb+'\n')
        while True:
            yangi = input("Yangi ma'lumat kiritishni xohlaysizmi? (1|0)")
            if yangi== 1:
                yangi_save = input("Kiriting\n")
                new.write(yangi_save+'\n')
            else:
                break
sorovnoma()