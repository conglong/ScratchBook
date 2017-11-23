from numpy import array

matrix = (range(1,6), range(6,11), range(11,16), range(16,21), range(21,26))

def bottom_left(matrix, top, right):
  bottom = len(matrix) - top
  left = len(matrix[top]) - right
  #print "top:%d, right:%d, bottom:%d, left:%d" % (top, right, bottom, left)
  return (bottom, left)

def ptop(matrix, top, right, bottom, left):
  for i in array(matrix)[top, left:right]:
    print i,

def pright(matrix, top, right, bottom, left):
  for i in array(matrix)[top:bottom, right - 1]:
    print i,

def pbottom(matrix, top, right, bottom, left):
  for i in reversed(array(matrix)[bottom - 1, left:right].tolist()):
    print i,

def pleft(matrix, top, right, bottom, left):
  for i in reversed(array(matrix)[top:bottom - 1, left].tolist()):
    print i,

def pmatrix(matrix, top, right, bottom, left):
  bottom, left = bottom_left(matrix, top, right)
  if bottom > len(matrix) - top or left > right:
    print ""
    print "Done."
    return
  ptop(matrix, top, right, bottom, left)
  top += 1
  pright(matrix, top, right, bottom, left)
  right -= 1
  pbottom(matrix, top, right, bottom, left)
  pleft(matrix, top, right, bottom, left)
  bottom -= 1
  left += 1
  pmatrix(matrix, top, right, bottom, left)

print array(matrix)
pmatrix(matrix, 0, len(matrix[0]), len(matrix), 0)
  
