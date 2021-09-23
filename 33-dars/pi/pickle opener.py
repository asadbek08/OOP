import pickle
with open('pi', 'rb') as pitext:
    pi_text = pickle.load(pitext)
print(pi_text)

