def gcd(a, b):
  if b == 0:
    return a;
  return gcd(b, a % b)

def lcm(a, b):
  return b * a / gcd(a, b)

def gcd_loop(a, b):
  while(b != 0):
    old_a = a
    a = b
    b = old_a % b
  return a

