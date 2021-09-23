import pickle

filename = "pi.txt"
with open(filename) as line:
	pi = line.read()
pi = pi.rstrip()
pi = pi.replace('\n', '')
pi = pi.replace(' ','')
	
pi = float(pi)

with open('pi', 'wb') as pitext:
    pickle.dump(pi, pitext)
