# -*- coding: utf-8 -*-
"""
Created on Thu Sep 23 11:13:21 2021

@author: Asadbek Iskandarov
"""
import json

with open('students.json') as f:
    talaba = json.load(f)
for item in talaba['student']:
    print(f"{item['name']} {item['lastname']}."
          f" Faculty of {item['faculty']} ")
    
with open('wiki.json') as f:
    wiki = json.load(f)

print(wiki['query']['pages']['13801']['title'])
print(wiki['query']['pages']['13801']['extract'])
   