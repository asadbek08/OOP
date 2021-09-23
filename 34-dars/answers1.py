# -*- coding: utf-8 -*-
"""
Created on Thu Sep 23 11:05:08 2021

@author: Asadbek Iskandarov

"""
import json
data = {"Model" : "Malibu", "Rang" : "Qora", "Yil":2020, "Narh":40000}
data_json = json.dumps(data)
print(data_json)
talaba_json = """{"ism":"Hasan","familiya":"Husanov","tyil":2000}"""
talaba = json.loads(talaba_json)
print(talaba['ism'], talaba['familiya'])
with open('data.json', 'w') as f1:
    json.dump(data, f1)
with open('talaba.json', 'w') as f2:
    json.dump(talaba, f2)
