import numpy as np
import cv2
import numpy as np
import math

SIGMA = 20.0

def getAfinity(v1,v2, dim):
    # TODO get texture code from previous lab
    if dim == 3:
        sub = (v1 + (-1*v2))
        sub = np.asmatrix(sub)
        sub = sub.astype(float)
        t = np.transpose(sub)
        mul = sub * t
        mul = mul.item(0)
        intensity = math.e ** (-mul/(2.0*(SIGMA**2)))
        color = math.e ** (- (np.linalg.norm(v1-v2))/(2.0*SIGMA**2))
    else:
        intensity = math.e ** (- ((v1-v2)*(v1-v2))/(2.0*SIGMA**2))
        color = 1  # math.e ** (- (np.linalg.norm(v1-v2))/(2.0*SIGMA**2))
    return color * intensity



def preprocessPlanar(im):
    if len(im.shape) == 2:
        height, width = im.shape
        dim = 1
    else:
        height, width, dim = im.shape
    print height
    print width
    print dim
    vertices = []
    adjacency_list = {}
    for i in range(0, height):
        for j in range(0, width):
            edges = []
            vertices.append((i,j))
            if (0 < i < (height-1)) and (0 < j < (width-1)):
                edges.append([(i - 1, j - 1), getAfinity(im[i][j], im[i - 1][j - 1], dim)])
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i - 1, j + 1), getAfinity(im[i][j], im[i - 1][j + 1], dim)])
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
                edges.append([(i + 1, j - 1), getAfinity(im[i][j], im[i + 1][j - 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
                edges.append([(i + 1, j + 1), getAfinity(im[i][j], im[i + 1][j + 1], dim)])
            elif (0 < i < (height-1)) and j == 0:
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i - 1, j + 1), getAfinity(im[i][j], im[i - 1][j + 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
                edges.append([(i + 1, j + 1), getAfinity(im[i][j], im[i + 1][j + 1], dim)])
            elif (0 < i < (height-1)) and j == (width-1):
                edges.append([(i - 1, j - 1), getAfinity(im[i][j], im[i - 1][j - 1], dim)])
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i + 1, j - 1), getAfinity(im[i][j], im[i + 1][j - 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
            elif i == 0 and (0 < j < (width - 1)):
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
                edges.append([(i + 1, j - 1), getAfinity(im[i][j], im[i + 1][j - 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
                edges.append([(i + 1, j + 1), getAfinity(im[i][j], im[i + 1][j + 1], dim)])
            else:
                edges.append([(i - 1, j - 1), getAfinity(im[i][j], im[i - 1][j - 1], dim)])
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i - 1, j + 1), getAfinity(im[i][j], im[i - 1][j + 1], dim)])
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
            adjacency_list[(i, j)] = edges
    return vertices, adjacency_list, width, height


def preprocessNotPlanar(im):
    if len(im.shape) == 2:
        height, width = im.shape
        dim = 1
    else:
        height, width, dim = im.shape
    print height
    print width
    print dim
    vertices = []
    adjacency_list = {}
    for i in range(0, height):
        for j in range(0, width):
            edges = []
            vertices.append((i,j))
            if (0 < i < (height-1)) and (0 < j < (width-1)):
                edges.append([(i - 1, j - 1), getAfinity(im[i][j], im[i - 1][j - 1], dim)])
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i - 1, j + 1), getAfinity(im[i][j], im[i - 1][j + 1], dim)])
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
                edges.append([(i + 1, j - 1), getAfinity(im[i][j], im[i + 1][j - 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
                edges.append([(i + 1, j + 1), getAfinity(im[i][j], im[i + 1][j + 1], dim)])
            elif (0 < i < (height-1)) and j == 0:
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i - 1, j + 1), getAfinity(im[i][j], im[i - 1][j + 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
                edges.append([(i + 1, j + 1), getAfinity(im[i][j], im[i + 1][j + 1], dim)])
            elif (0 < i < (height-1)) and j == (width-1):
                edges.append([(i - 1, j - 1), getAfinity(im[i][j], im[i - 1][j - 1], dim)])
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i + 1, j - 1), getAfinity(im[i][j], im[i + 1][j - 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
            elif i == 0 and (0 < j < (width - 1)):
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
                edges.append([(i + 1, j - 1), getAfinity(im[i][j], im[i + 1][j - 1], dim)])
                edges.append([(i + 1, j), getAfinity(im[i][j], im[i + 1][j], dim)])
                edges.append([(i + 1, j + 1), getAfinity(im[i][j], im[i + 1][j + 1], dim)])
            else:
                edges.append([(i - 1, j - 1), getAfinity(im[i][j], im[i - 1][j - 1], dim)])
                edges.append([(i - 1, j), getAfinity(im[i][j], im[i - 1][j], dim)])
                edges.append([(i - 1, j + 1), getAfinity(im[i][j], im[i - 1][j + 1], dim)])
                edges.append([(i, j - 1), getAfinity(im[i][j], im[i][j - 1], dim)])
                edges.append([(i, j + 1), getAfinity(im[i][j], im[i][j + 1], dim)])
            adjacency_list[(i, j)] = edges
    return vertices, adjacency_list, width, height